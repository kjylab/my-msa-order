package dev.ktcloud.black.order.outbox.inventory.request.domain.vo

enum class OrderInventoryRequestOutboxStatus {
    INIT, PUBLISHED, FAILED;

    private fun getTransitiveList(): List<OrderInventoryRequestOutboxStatus> {
        return when (this) {
            INIT -> listOf(PUBLISHED, FAILED)
            PUBLISHED -> listOf()
            FAILED -> listOf()
        }
    }

    fun checkTransitive(orderStatus: OrderInventoryRequestOutboxStatus): Boolean {
        return getTransitiveList().contains(orderStatus)
    }
}