package dev.ktcloud.black.order.common.application.port.event

import dev.ktcloud.black.order.order.application.dto.event.inbound.InventoryReservedResultEvent

interface OrderInventoryEventListenerPort {
    fun onResultPublished(event: InventoryReservedResultEvent)
}