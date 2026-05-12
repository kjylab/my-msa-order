package dev.ktcloud.black.order.common.adapter.infrastructure.kafka

import dev.ktcloud.black.order.common.adapter.infrastructure.kafka.mapper.OrderInventoryPublishEventMapper
import dev.ktcloud.black.order.common.adapter.infrastructure.kafka.model.InventoryReserveRequestMessage
import dev.ktcloud.black.order.order.application.dto.event.outbound.InventoryReserveRequestEvent
import dev.ktcloud.black.order.common.application.port.event.OrderInventoryEventPublishPort
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class OrderInventoryEventKafkaPublisher(
    private val kafkaTemplate: KafkaTemplate<String, InventoryReserveRequestMessage>,
    private val mapper: OrderInventoryPublishEventMapper,
    @Value("\${spring.kafka.topic.inventory-reserve-request}")
    private val topicName: String
): OrderInventoryEventPublishPort {
    override fun publish(
        event: InventoryReserveRequestEvent,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        val message = mapper.toMessage(event)

        kafkaTemplate.send(topicName, message.orderId.toString(), message).whenComplete { _, e ->
            if (e != null) onError.invoke()
            else onSuccess.invoke()
        }
    }
}