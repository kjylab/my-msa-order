package dev.ktcloud.black.order.outbox.inventory.request.adapter.scheduler

import dev.ktcloud.black.order.outbox.inventory.request.application.port.inbound.ProcessOrderInventoryRequestOutboxStateCommand
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class OrderInventoryRequestOutboxScheduler(
    private val processOrderInventoryRequestOutboxStateCommand: ProcessOrderInventoryRequestOutboxStateCommand
) {
    @Scheduled(fixedDelay = 5000)
    fun process() {
        processOrderInventoryRequestOutboxStateCommand.processAll()
    }
}
