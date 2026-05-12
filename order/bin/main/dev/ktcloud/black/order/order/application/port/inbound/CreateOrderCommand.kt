package dev.ktcloud.black.order.order.application.port.inbound

import dev.ktcloud.black.order.order.domain.vo.OrderLineItem

interface CreateOrderCommand {
    fun create(command: In)

    data class In(
        val orderLineItems: List<OrderLineItem>,
    )
}