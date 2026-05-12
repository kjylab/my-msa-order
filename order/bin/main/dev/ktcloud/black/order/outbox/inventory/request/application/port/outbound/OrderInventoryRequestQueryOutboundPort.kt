package dev.ktcloud.black.order.outbox.inventory.request.application.port.outbound

import dev.ktcloud.black.order.outbox.inventory.request.domain.entity.OrderInventoryRequestOutboxDomainEntity

interface OrderInventoryRequestQueryOutboundPort {
    fun findAll(by: List<Long>): List<OrderInventoryRequestOutboxDomainEntity>
    fun fetchUnprocessed(): List<OrderInventoryRequestOutboxDomainEntity>
}