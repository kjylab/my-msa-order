package dev.ktcloud.black.order.outbox.inventory.request.application.port.inbound

interface ProcessOrderInventoryRequestOutboxStateCommand {
    fun processAll()
}