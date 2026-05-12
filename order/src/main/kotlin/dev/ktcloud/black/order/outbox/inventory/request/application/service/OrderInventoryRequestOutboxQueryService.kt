package dev.ktcloud.black.order.outbox.inventory.request.application.service

import dev.ktcloud.black.order.outbox.inventory.request.application.port.inbound.FetchUnprocessedOrderInventoryRequestOutboxesQuery
import dev.ktcloud.black.order.outbox.inventory.request.application.port.outbound.OrderInventoryRequestQueryOutboundPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderInventoryRequestOutboxQueryService(
    private val orderInventoryRequestQueryOutboundPort: OrderInventoryRequestQueryOutboundPort
): FetchUnprocessedOrderInventoryRequestOutboxesQuery {
    @Transactional(readOnly = true)
    override fun fetchAllUnprocessed(): List<FetchUnprocessedOrderInventoryRequestOutboxesQuery.Out> {
        val domainEntities = orderInventoryRequestQueryOutboundPort.fetchUnprocessed()

        return domainEntities.map(FetchUnprocessedOrderInventoryRequestOutboxesQuery.Out::from)
    }
}