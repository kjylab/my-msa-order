package dev.ktcloud.black.order.order.application.port.outbound

import dev.ktcloud.black.order.order.domain.entity.OrderDomainEntity

interface OrderQueryOutboundPort {
    fun fetchOrder(orderId: Long): OrderDomainEntity
    fun fetchAll(): List<OrderDomainEntity>
}