package dev.ktcloud.black.order.order.adapter.infrastructure.jpa.repository

import dev.ktcloud.black.order.order.adapter.infrastructure.jpa.OrderMapper
import dev.ktcloud.black.order.order.application.port.outbound.OrderQueryOutboundPort
import dev.ktcloud.black.order.order.domain.entity.OrderDomainEntity
import dev.ktcloud.black.order.order.domain.exception.OrderException
import org.springframework.stereotype.Component

@Component
class OrderPostgresqlQueryRepository(
    private val repository: OrderPostgresqlRepository,
    private val orderMapper: OrderMapper
): OrderQueryOutboundPort {
    override fun fetchOrder(orderId: Long): OrderDomainEntity {
        val orderOrmEntity = repository.findById(orderId)

        if (!orderOrmEntity.isPresent) throw OrderException.NoSuchOrder()

        return orderMapper.toDomainEntity(orderOrmEntity.get())
    }

    override fun fetchAll(): List<OrderDomainEntity> {
        val ormEntities = repository.findAll()

        return orderMapper.toDomainEntity(ormEntities)
    }
}