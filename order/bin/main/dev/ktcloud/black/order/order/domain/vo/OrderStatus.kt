package dev.ktcloud.black.order.order.domain.vo

enum class OrderStatus {
    PENDING, INVENTORY_RESERVED, FAILED;

    private fun getTransitiveList(): List<OrderStatus> {
        return when (this) {
            PENDING -> listOf(INVENTORY_RESERVED, FAILED)
            INVENTORY_RESERVED -> listOf(FAILED)
            FAILED -> listOf()
        }
    }

    fun checkTransitive(orderStatus: OrderStatus): Boolean {
        return getTransitiveList().contains(orderStatus)
    }
}