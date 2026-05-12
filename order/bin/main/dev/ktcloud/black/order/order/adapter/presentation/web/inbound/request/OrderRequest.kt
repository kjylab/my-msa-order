package dev.ktcloud.black.order.order.adapter.presentation.web.inbound.request

import dev.ktcloud.black.order.order.application.dto.OrderItemDto

data class OrderRequest(
    val items: List<OrderItemDto>
)