package dev.ktcloud.black.order.common.adapter.infrastructure.kafka.model

import dev.ktcloud.black.order.order.domain.vo.InventoryReserveResultState

data class InventoryReservedResultMessage(
    val orderId: Long,
    val inventoryId: Long,
    val amount: Int,
    val resultState: InventoryReserveResultState
)