package dev.ktcloud.black.order.outbox.inventory.request.application.service

import dev.ktcloud.black.order.common.application.port.event.OrderInventoryEventPublishPort
import dev.ktcloud.black.order.order.application.dto.event.outbound.InventoryReserveRequestEvent
import dev.ktcloud.black.order.outbox.inventory.request.application.port.inbound.CreateOrderInventoryRequestOutboxCommand
import dev.ktcloud.black.order.outbox.inventory.request.application.port.inbound.ProcessOrderInventoryRequestOutboxStateCommand
import dev.ktcloud.black.order.outbox.inventory.request.application.port.outbound.OrderInventoryRequestCommandOutboundPort
import dev.ktcloud.black.order.outbox.inventory.request.application.port.outbound.OrderInventoryRequestQueryOutboundPort
import dev.ktcloud.black.order.outbox.inventory.request.domain.entity.OrderInventoryRequestOutboxDomainEntity
import dev.ktcloud.black.order.outbox.inventory.request.domain.exception.OrderInventoryRequestOutboxException
import dev.ktcloud.black.order.outbox.inventory.request.domain.vo.OrderInventoryRequestOutboxStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderInventoryRequestOutboxCommandService(
    private val orderInventoryRequestCommandOutboundPort: OrderInventoryRequestCommandOutboundPort,
    private val orderInventoryRequestQueryOutboundPort: OrderInventoryRequestQueryOutboundPort,
    private val orderInventoryEventPublishPort: OrderInventoryEventPublishPort
): CreateOrderInventoryRequestOutboxCommand, ProcessOrderInventoryRequestOutboxStateCommand {
    @Transactional
    override fun create(command: CreateOrderInventoryRequestOutboxCommand.In) {
        val domainEntity = OrderInventoryRequestOutboxDomainEntity(
            orderId = command.orderId,
            inventoryId = command.inventoryId,
            amount = command.amount,
        )

        orderInventoryRequestCommandOutboundPort.save(domainEntity)
    }

    private fun processOrderInventoryRequestOutbox(
        domainEntity: OrderInventoryRequestOutboxDomainEntity
    ): OrderInventoryRequestOutboxDomainEntity {
        try {
            domainEntity.increaseRetry()
        } catch (_: OrderInventoryRequestOutboxException.OutboxFailedOverMaximum) {
            domainEntity.updateStatus(OrderInventoryRequestOutboxStatus.FAILED)
            return domainEntity
        }

        orderInventoryEventPublishPort.publish(
            event = InventoryReserveRequestEvent(
                orderId = domainEntity.orderId,
                inventoryId = domainEntity.inventoryId,
                amount = domainEntity.amount,
            ),
            onSuccess = { domainEntity.updateStatus(OrderInventoryRequestOutboxStatus.PUBLISHED) },
        )

        return domainEntity
    }

    @Transactional
    override fun processAll() {
        val unProcessedList = orderInventoryRequestQueryOutboundPort.fetchUnprocessed()

        runBlocking {
            unProcessedList.map {
                async(Dispatchers.IO) {
                    processOrderInventoryRequestOutbox(it)
                }
            }.awaitAll()
        }
    }
}