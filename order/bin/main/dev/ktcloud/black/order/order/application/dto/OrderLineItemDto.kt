package dev.ktcloud.black.order.order.application.dto

import dev.ktcloud.black.order.order.domain.vo.OrderLineItem
import dev.ktcloud.black.order.order.domain.vo.OrderLineItemStatus

data class OrderLineItemDto(
    val inventoryId: Long,
    val productId: String,
    val skuCode: String,
    val price: Int,
    val quantity: Int,
    val status: OrderLineItemStatus,
) {
    companion object {
        fun from(orderLineItem: OrderLineItem): OrderLineItemDto = OrderLineItemDto(
            inventoryId = orderLineItem.inventoryId,
            productId = orderLineItem.productId,
            skuCode = orderLineItem.skuCode,
            price = orderLineItem.price,
            quantity = orderLineItem.quantity,
            status = orderLineItem.status,
        )
    }
}
