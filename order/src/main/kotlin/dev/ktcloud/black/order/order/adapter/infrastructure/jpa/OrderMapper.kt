package dev.ktcloud.black.order.order.adapter.infrastructure.jpa

import dev.ktcloud.black.common.adapter.infrastructure.jpa.EntityMapper
import dev.ktcloud.black.order.order.adapter.infrastructure.jpa.entity.Order
import dev.ktcloud.black.order.order.domain.entity.OrderDomainEntity
import org.springframework.stereotype.Component

@Component
class OrderMapper: EntityMapper<Order, OrderDomainEntity> {
    override fun toOrmEntity(domainEntity: OrderDomainEntity): Order {
        return Order(
            id = domainEntity.id,
            status = domainEntity.status,
            orderLineItems = domainEntity.orderLineItems.toMutableList(),
        )
    }

    override fun toDomainEntity(entity: Order): OrderDomainEntity {
        return OrderDomainEntity(
            id = entity.id,
            _status = entity.status,
            _orderLineItems = entity.orderLineItems,
        )
    }
}