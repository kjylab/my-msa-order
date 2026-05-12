package dev.ktcloud.black.order.service.adapter.web.inbound

import dev.ktcloud.black.order.service.adapter.presentation.web.inbound.grpc.CreateOrderRequest
import dev.ktcloud.black.order.service.adapter.presentation.web.inbound.grpc.Empty
import dev.ktcloud.black.order.service.adapter.presentation.web.inbound.grpc.FetchOrderRequest
import dev.ktcloud.black.order.service.adapter.presentation.web.inbound.grpc.FetchOrdersResponse
import dev.ktcloud.black.order.service.adapter.presentation.web.inbound.grpc.OrderResponseDto
import dev.ktcloud.black.order.service.adapter.presentation.web.inbound.grpc.OrderServiceGrpcKt

abstract class OrderGrpcController: OrderServiceGrpcKt.OrderServiceCoroutineImplBase() {
    abstract override suspend fun createOrder(request: CreateOrderRequest): OrderResponseDto

    abstract override suspend fun fetchOrder(request: FetchOrderRequest): OrderResponseDto

    abstract override suspend fun fetchOrders(request: Empty): FetchOrdersResponse
}