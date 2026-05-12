package dev.ktcloud.black.order.order.domain.vo

import jakarta.persistence.Embeddable

@Embeddable
data class OrderLineItem(
    val inventoryId: Long,
    val productId: String,
    val skuCode: String,
    val price: Int,
    val quantity: Int,
    val status: OrderLineItemStatus,
) {
    fun copy(newStatus: OrderLineItemStatus): OrderLineItem {
        return OrderLineItem(
            inventoryId = inventoryId,
            productId = productId,
            skuCode = skuCode,
            price = price,
            quantity = quantity,
            status = newStatus
        )
    }
}