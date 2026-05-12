package dev.ktcloud.black.order.common.adapter.infrastructure.kafka.mapper

import dev.ktcloud.black.common.adapter.infrastructure.kafka.EventMapper
import dev.ktcloud.black.order.common.adapter.infrastructure.kafka.model.InventoryReservedResultMessage
import dev.ktcloud.black.order.order.application.dto.event.inbound.InventoryReservedResultEvent
import org.springframework.stereotype.Component

@Component
class OrderInventoryListenEventMapper: EventMapper<InventoryReservedResultMessage, InventoryReservedResultEvent> {
    override fun toMessage(event: InventoryReservedResultEvent): InventoryReservedResultMessage {
        return InventoryReservedResultMessage(
            event.orderId,
            event.inventoryId,
            event.amount,
            event.resultState
        )
    }

    override fun toEvent(message: InventoryReservedResultMessage): InventoryReservedResultEvent {
        return InventoryReservedResultEvent(
            message.orderId,
            message.inventoryId,
            message.amount,
            message.resultState
        )
    }
}