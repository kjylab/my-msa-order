package dev.ktcloud.black.order.outbox.inventory.request.application.dto

import dev.ktcloud.black.order.outbox.inventory.request.domain.vo.OrderInventoryRequestOutboxStatus

data class OrderInventoryRequestOutboxDto(
    val id: Long,
    val status: OrderInventoryRequestOutboxStatus,
    val orderId: Long,
    val inventoryId: Long,
    val amount: Int,
)