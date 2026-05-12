package dev.ktcloud.black.order.common.adapter.infrastructure.kafka


import dev.ktcloud.black.order.order.application.dto.event.inbound.InventoryReservedResultEvent
import dev.ktcloud.black.order.common.application.port.event.OrderInventoryEventListenerPort
import dev.ktcloud.black.order.order.application.service.OrderCommandService
import dev.ktcloud.black.order.order.domain.vo.InventoryReserveResultState
import dev.ktcloud.black.order.order.domain.vo.OrderLineItemStatus
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class OrderInventoryEventKafkaListener(
    private val orderCommandService: OrderCommandService
): OrderInventoryEventListenerPort {
    @KafkaListener(
        topics = ["\${spring.kafka.topic.inventory-reserved-result}"],
        groupId = "inventory-service-group",
        containerFactory = "inventoryReservedResponseContainerFactory"
    )
    override fun onResultPublished(event: InventoryReservedResultEvent) {
        if (event.resultState == InventoryReserveResultState.SUCCESS)
            orderCommandService.updateOrderLineItemStatus(
                orderId = event.orderId,
                inventoryId = event.inventoryId,
                status = OrderLineItemStatus.INVENTORY_RESERVED
            )

        if (event.resultState == InventoryReserveResultState.FAILED)
            orderCommandService.updateOrderLineItemStatus(
                orderId = event.orderId,
                inventoryId = event.inventoryId,
                status = OrderLineItemStatus.FAILED
            )
    }
}