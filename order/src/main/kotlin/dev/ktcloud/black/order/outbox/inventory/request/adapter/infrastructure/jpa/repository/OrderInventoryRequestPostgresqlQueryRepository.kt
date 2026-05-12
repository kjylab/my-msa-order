package dev.ktcloud.black.order.outbox.inventory.request.adapter.infrastructure.jpa.repository


import com.querydsl.jpa.impl.JPAQueryFactory
import dev.ktcloud.black.common.util.time.now
import dev.ktcloud.black.order.outbox.inventory.request.adapter.infrastructure.jpa.OrderInventoryRequestOutboxMapper
import dev.ktcloud.black.order.outbox.inventory.request.adapter.infrastructure.jpa.entity.QOrderInventoryRequestOutbox.orderInventoryRequestOutbox
import dev.ktcloud.black.order.outbox.inventory.request.application.port.outbound.OrderInventoryRequestQueryOutboundPort
import dev.ktcloud.black.order.outbox.inventory.request.domain.entity.OrderInventoryRequestOutboxDomainEntity
import dev.ktcloud.black.order.outbox.inventory.request.domain.vo.OrderInventoryRequestOutboxStatus
import org.springframework.stereotype.Component

@Component
class OrderInventoryRequestPostgresqlQueryRepository(
    private val mapper: OrderInventoryRequestOutboxMapper,
    private val queryFactory: JPAQueryFactory,
    private val repository: OrderInventoryRequestPostgresqlRepository
): OrderInventoryRequestQueryOutboundPort {
    override fun findAll(by: List<Long>): List<OrderInventoryRequestOutboxDomainEntity> {
        val ormEntities = repository.findAllById(by)

        return mapper.toDomainEntity(ormEntities)
    }

    override fun fetchUnprocessed(): List<OrderInventoryRequestOutboxDomainEntity> {
        val qb = queryFactory.selectFrom(orderInventoryRequestOutbox)
            .where(
                orderInventoryRequestOutbox.status.eq(
                    OrderInventoryRequestOutboxStatus.INIT
                ),
                orderInventoryRequestOutbox.nextStartFrom.before(now())
            )
            .orderBy(orderInventoryRequestOutbox.id.asc())

        return mapper.toDomainEntity(qb.fetch())
    }
}