package dev.ktcloud.black.order.order.application.service

import dev.ktcloud.black.order.order.application.port.inbound.FetchOrderQuery
import dev.ktcloud.black.order.order.application.port.inbound.FetchOrdersQuery
import dev.ktcloud.black.order.order.application.port.outbound.OrderQueryOutboundPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderQueryService(
    private val orderQueryOutboundPort: OrderQueryOutboundPort
): FetchOrdersQuery, FetchOrderQuery {
    @Transactional(readOnly = true)
    override fun fetchOrder(query: FetchOrderQuery.In): FetchOrderQuery.Out {
        val order = orderQueryOutboundPort.fetchOrder(query.id)

        return FetchOrderQuery.Out.from(order)
    }

    @Transactional(readOnly = true)
    override fun fetchOrders(): List<FetchOrdersQuery.Out> {
        val orders = orderQueryOutboundPort.fetchAll()

        return orders.map(FetchOrdersQuery.Out::from)
    }
}