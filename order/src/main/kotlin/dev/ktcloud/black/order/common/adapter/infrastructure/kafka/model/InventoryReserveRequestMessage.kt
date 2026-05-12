package dev.ktcloud.black.order.common.adapter.infrastructure.kafka.model

data class InventoryReserveRequestMessage(
    val orderId: Long,
    val inventoryId: Long,
    val amount: Int
)
