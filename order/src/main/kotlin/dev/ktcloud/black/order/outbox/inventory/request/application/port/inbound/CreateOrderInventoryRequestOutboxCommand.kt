package dev.ktcloud.black.order.outbox.inventory.request.application.port.inbound

interface CreateOrderInventoryRequestOutboxCommand {
    fun create(command: In)

    data class In(
        val orderId: Long,
        val inventoryId: Long,
        val amount: Int
    )
}