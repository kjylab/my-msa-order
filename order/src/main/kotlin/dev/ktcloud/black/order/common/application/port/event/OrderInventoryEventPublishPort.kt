package dev.ktcloud.black.order.common.application.port.event

import dev.ktcloud.black.order.order.application.dto.event.outbound.InventoryReserveRequestEvent

interface OrderInventoryEventPublishPort {
    fun publish(event: InventoryReserveRequestEvent, onSuccess: () -> Unit = {}, onError: () -> Unit = {})
}