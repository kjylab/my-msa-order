package dev.ktcloud.black.order.order.application.dto.event.inbound

import dev.ktcloud.black.order.order.domain.vo.InventoryReserveResultState

data class InventoryReservedResultEvent(
    val orderId: Long,
    val inventoryId: Long,
    val amount: Int,
    val resultState: InventoryReserveResultState
)