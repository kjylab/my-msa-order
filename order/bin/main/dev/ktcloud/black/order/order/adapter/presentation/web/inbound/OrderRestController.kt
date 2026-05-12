package dev.ktcloud.black.order.order.adapter.presentation.web.inbound

import dev.ktcloud.black.order.order.adapter.presentation.web.inbound.request.OrderRequest

interface OrderRestController {
    fun placeOrder(request: OrderRequest)
}