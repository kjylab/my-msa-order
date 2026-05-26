# my-msa-order

Troica Market MSA의 **주문(Order) 서비스**. 주문 생성 및 상태 관리를 담당하며, Kafka로 inventory-service와 재고 예약 이벤트를 비동기 교환한다.

## 아키텍처

### 모듈 구성

```
order/                ← 순수 도메인 + 유스케이스 + Kafka 어댑터
  domain/             ← OrderDomainEntity, OrderLineItem, OrderException
  application/        ← UseCase 인터페이스 + 구현 서비스
  adapter/
    infrastructure/
      jpa/            ← PostgreSQL (주문 영구 저장)
      kafka/          ← 재고 이벤트 발행/수신

order-service/        ← 실행 진입점 (Spring Boot + gRPC 서버)
  OrderGrpcController
  GrpcExceptionHandler
```

### 주문 처리 흐름

```
user-api-gateway → gRPC CreateOrder
  → OrderCommandService.createOrder()
  → 주문 저장 (PostgreSQL, status=PENDING)
  → Kafka publish: inventory-reserve-request-topic
       {orderId, inventoryId, amount}
  ↓
  (비동기)
  ↓
Kafka consume: inventory-reserved-result-topic
  → 성공: OrderLineItem status → INVENTORY_RESERVED
  → 실패: OrderLineItem status → FAILED
```

## gRPC API

```protobuf
service OrderService {
  rpc CreateOrder  (CreateOrderRequest)  returns (OrderResponseDto);
  rpc FetchOrder   (FetchOrderRequest)   returns (OrderResponseDto);
  rpc FetchOrders  (Empty)              returns (FetchOrdersResponse);
}
```

> gRPC 포트: **9090** (HTTP: 8080)

## Kafka 토픽

| 토픽 | 방향 | 설명 |
|------|------|------|
| `inventory-reserve-request-topic` | 발행 (producer) | 재고 예약 요청 → inventory-service |
| `inventory-reserved-result-topic` | 수신 (consumer) | 재고 예약 결과 ← inventory-service |

### Kafka 설정 특이사항
- Publisher: 전송 성공/실패 콜백(`onSuccess`, `onError`) 처리
- Consumer Group: `order-service-group`
- 메시지 형식: JSON

### 메시지 모델 (양쪽 서비스 동일 필드)

| 클래스 | 필드 |
|--------|------|
| `InventoryReserveRequestMessage` | orderId, inventoryId, amount |
| `InventoryReservedResultMessage` | orderId, inventoryId, amount, resultState(SUCCESS/FAILED) |

## 실행 포트

| 포트 | 용도 |
|------|------|
| 8080 | HTTP (actuator: /healthz, /prometheus) |
| 9090 | gRPC (내부 서비스 통신) |

## 의존 인프라

| 인프라 | 용도 |
|--------|------|
| PostgreSQL (`order_db`) | 주문 영구 저장 |
| Kafka | inventory-service와 재고 이벤트 비동기 처리 |

## 관측성 (Observability)

### 메트릭 (Prometheus)
- `/prometheus` 엔드포인트로 메트릭 노출
- HTTP 요청별 latency histogram bucket 활성화
- ServiceMonitor로 Prometheus 자동 스크레이프

### Kafka Consumer Lag 모니터링
- `order-service-group` consumer lag 메트릭을 kafka-exporter가 수집
- Grafana `kafka-lag` 대시보드에서 확인 가능

### 분산 트레이싱 (Tempo)
- `micrometer-tracing-bridge-otel` + `opentelemetry-exporter-otlp` 사용
- OTLP HTTP로 Tempo(`tempo.monitoring.svc.cluster.local:4318`)로 전송
- sampling probability: 1.0

### 로그-트레이스 연동
- 로그에 `[traceId-spanId]` 포함 → Grafana Loki에서 Tempo 링크로 바로 이동 가능

## CI/CD 흐름

```
GitHub push
  → JAR 빌드
  → Docker 이미지 빌드 + Docker Hub push (jyupk/my-msa-order-service)
  → my-msa-manifest-values/order-service/values-release.yaml 의 tag를 커밋 SHA로 업데이트
  → ArgoCD 감지 → 클러스터 롤링 업데이트
```

## 로컬 Docker 빌드

```bash
docker build --no-cache -t ktcloud-msa-order-service:latest -f Containerfile .
```

## 관련 레포

| 레포 | 역할 |
|------|------|
| [my-msa-common](https://github.com/kjylab/my-msa-common) | 공통 라이브러리 |
| [my-msa-inventory](https://github.com/kjylab/my-msa-inventory) | 재고 서비스 (Kafka 파트너) |
| [my-msa-manifest-values](https://github.com/kjylab/my-msa-manifest-values) | Helm values (이미지 태그 관리) |
| [my-market-msa-manifest](https://github.com/kjylab/my-market-msa-manifest) | 공통 Helm 차트 |
