package dev.ktcloud.black.order.order.domain.entity

import dev.ktcloud.black.order.order.domain.exception.OrderException
import dev.ktcloud.black.order.order.domain.vo.OrderLineItem
import dev.ktcloud.black.order.order.domain.vo.OrderLineItemStatus
import dev.ktcloud.black.order.order.domain.vo.OrderStatus

data class OrderDomainEntity(
    val id: Long = 0L,
    private var _status: OrderStatus = OrderStatus.PENDING,
    private var _orderLineItems: List<OrderLineItem>,
) {
    val status: OrderStatus
        get() = _status

    val orderLineItems: List<OrderLineItem>
        get() = _orderLineItems

    fun updateOrderLineItem(inventoryId: Long, newStatus: OrderLineItemStatus) {
        if (_status != OrderStatus.PENDING) throw OrderException.OrderStatusUpdateImpossible()

        _orderLineItems = _orderLineItems.map {
            if (it.inventoryId == inventoryId) it.copy(newStatus = newStatus)
            else it
        }

        val failedInventory = _orderLineItems.find { it.status == OrderLineItemStatus.FAILED }

        if(failedInventory != null) _status = OrderStatus.FAILED

        if (_orderLineItems.all { it.status == OrderLineItemStatus.INVENTORY_RESERVED }) _status = OrderStatus.INVENTORY_RESERVED
    }
}