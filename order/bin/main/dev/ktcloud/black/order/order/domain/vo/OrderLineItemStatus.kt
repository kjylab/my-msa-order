package dev.ktcloud.black.order.order.domain.vo

enum class OrderLineItemStatus {
    PENDING, INVENTORY_RESERVED, FAILED;

    private fun getTransitiveList(): List<OrderLineItemStatus> {
        return when (this) {
            PENDING -> listOf(INVENTORY_RESERVED, FAILED)
            INVENTORY_RESERVED -> listOf(FAILED)
            FAILED -> listOf()
        }
    }

    fun checkTransitive(orderStatus: OrderLineItemStatus): Boolean {
        return getTransitiveList().contains(orderStatus)
    }
}