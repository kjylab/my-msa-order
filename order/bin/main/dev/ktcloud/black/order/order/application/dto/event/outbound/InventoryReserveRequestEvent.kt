package dev.ktcloud.black.order.order.application.dto.event.outbound

data class InventoryReserveRequestEvent(
    val orderId: Long,
    val inventoryId: Long,
    val amount: Int
)
