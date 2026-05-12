package dev.ktcloud.black.order.outbox.inventory.request.application.port.outbound

import dev.ktcloud.black.order.outbox.inventory.request.domain.entity.OrderInventoryRequestOutboxDomainEntity

interface OrderInventoryRequestCommandOutboundPort {
    fun save(orderInventoryRequest: OrderInventoryRequestOutboxDomainEntity)
    fun saveAll(orderInventoryRequests: List<OrderInventoryRequestOutboxDomainEntity>)
}