package dev.ktcloud.black.order.order.application.dto

data class OrderItemDto(
    val id: Long,
    val skuCode: String,
    val price: Int,
    val quantity: Int,
)
