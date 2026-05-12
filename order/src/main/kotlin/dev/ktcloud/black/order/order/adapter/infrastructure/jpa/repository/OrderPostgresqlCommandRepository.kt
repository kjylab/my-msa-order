package dev.ktcloud.black.order.order.adapter.infrastructure.jpa.repository

import dev.ktcloud.black.order.order.adapter.infrastructure.jpa.OrderMapper
import dev.ktcloud.black.order.order.application.port.outbound.OrderCommandOutboundPort
import dev.ktcloud.black.order.order.domain.entity.OrderDomainEntity
import org.springframework.stereotype.Component

@Component
class OrderPostgresqlCommandRepository(
    private val repository: OrderPostgresqlRepository,
    private val orderMapper: OrderMapper
): OrderCommandOutboundPort {
    override fun save(orderDomainEntity: OrderDomainEntity): OrderDomainEntity {
        val orderOrmEntity = orderMapper.toOrmEntity(orderDomainEntity)

        return orderMapper.toDomainEntity(repository.save(orderOrmEntity))
    }
}