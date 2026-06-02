package dev.ktcloud.black.order.order.domain.entity

import dev.ktcloud.black.order.order.domain.exception.OrderException
import dev.ktcloud.black.order.order.domain.vo.OrderLineItem
import dev.ktcloud.black.order.order.domain.vo.OrderLineItemStatus
import dev.ktcloud.black.order.order.domain.vo.OrderStatus
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class OrderDomainEntityTest {

    private fun lineItem(inventoryId: Long, status: OrderLineItemStatus = OrderLineItemStatus.PENDING) =
        OrderLineItem(
            inventoryId = inventoryId,
            productId = "product-$inventoryId",
            skuCode = "SKU-00$inventoryId",
            price = 1000,
            quantity = 1,
            status = status,
        )

    @Test
    fun `단일 라인아이템이 INVENTORY_RESERVED로 업데이트되면 주문 상태도 INVENTORY_RESERVED가 된다`() {
        val order = OrderDomainEntity(_orderLineItems = listOf(lineItem(1L)))

        order.updateOrderLineItem(1L, OrderLineItemStatus.INVENTORY_RESERVED)

        assertThat(order.status).isEqualTo(OrderStatus.INVENTORY_RESERVED)
        assertThat(order.orderLineItems[0].status).isEqualTo(OrderLineItemStatus.INVENTORY_RESERVED)
    }

    @Test
    fun `일부 라인아이템만 INVENTORY_RESERVED이면 주문 상태는 PENDING을 유지한다`() {
        val order = OrderDomainEntity(_orderLineItems = listOf(lineItem(1L), lineItem(2L)))

        order.updateOrderLineItem(1L, OrderLineItemStatus.INVENTORY_RESERVED)

        assertThat(order.status).isEqualTo(OrderStatus.PENDING)
    }

    @Test
    fun `모든 라인아이템이 INVENTORY_RESERVED이면 주문 상태가 INVENTORY_RESERVED가 된다`() {
        val order = OrderDomainEntity(_orderLineItems = listOf(lineItem(1L), lineItem(2L)))

        order.updateOrderLineItem(1L, OrderLineItemStatus.INVENTORY_RESERVED)
        order.updateOrderLineItem(2L, OrderLineItemStatus.INVENTORY_RESERVED)

        assertThat(order.status).isEqualTo(OrderStatus.INVENTORY_RESERVED)
    }

    @Test
    fun `라인아이템 중 하나라도 FAILED이면 주문 상태가 FAILED가 된다`() {
        val order = OrderDomainEntity(_orderLineItems = listOf(lineItem(1L), lineItem(2L)))

        order.updateOrderLineItem(1L, OrderLineItemStatus.FAILED)

        assertThat(order.status).isEqualTo(OrderStatus.FAILED)
    }

    @Test
    fun `PENDING이 아닌 주문에 updateOrderLineItem 호출 시 예외가 발생한다`() {
        val order = OrderDomainEntity(_orderLineItems = listOf(lineItem(1L)))
        order.updateOrderLineItem(1L, OrderLineItemStatus.FAILED)

        assertThatThrownBy { order.updateOrderLineItem(1L, OrderLineItemStatus.INVENTORY_RESERVED) }
            .isInstanceOf(OrderException.OrderStatusUpdateImpossible::class.java)
    }
}
