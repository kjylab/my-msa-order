package dev.ktcloud.black.order.order.application.port.outbound

import dev.ktcloud.black.order.order.domain.entity.OrderDomainEntity

interface OrderCommandOutboundPort {
    fun save(orderDomainEntity: OrderDomainEntity): OrderDomainEntity
}