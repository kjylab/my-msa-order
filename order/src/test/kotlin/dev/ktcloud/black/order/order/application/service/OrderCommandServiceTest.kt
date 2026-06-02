package dev.ktcloud.black.order.order.application.service

import dev.ktcloud.black.order.order.application.port.inbound.CreateOrderCommand
import dev.ktcloud.black.order.order.application.port.outbound.OrderCommandOutboundPort
import dev.ktcloud.black.order.order.application.port.outbound.OrderQueryOutboundPort
import dev.ktcloud.black.order.order.domain.entity.OrderDomainEntity
import dev.ktcloud.black.order.order.domain.vo.OrderLineItem
import dev.ktcloud.black.order.order.domain.vo.OrderLineItemStatus
import dev.ktcloud.black.order.order.domain.vo.OrderStatus
import dev.ktcloud.black.order.outbox.inventory.request.application.port.inbound.CreateOrderInventoryRequestOutboxCommand
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OrderCommandServiceTest {

    private val orderCommandOutboundPort = mockk<OrderCommandOutboundPort>()
    private val orderQueryOutboundPort = mockk<OrderQueryOutboundPort>()
    private val createOrderInventoryRequestOutboxCommand = mockk<CreateOrderInventoryRequestOutboxCommand>(relaxed = true)

    private val sut = OrderCommandService(
        orderCommandOutboundPort = orderCommandOutboundPort,
        orderQueryOutboundPort = orderQueryOutboundPort,
        createOrderInventoryRequestOutboxCommand = createOrderInventoryRequestOutboxCommand,
    )

    @Test
    fun `주문 생성 시 저장 후 각 라인아이템에 대한 outbox가 생성된다`() {
        val command = listOf(
            CreateOrderCommand.In(inventoryId = 1L, productId = "p1", skuCode = "SKU-001", price = 1000, quantity = 2),
            CreateOrderCommand.In(inventoryId = 2L, productId = "p2", skuCode = "SKU-002", price = 2000, quantity = 1),
        )
        val savedOrder = OrderDomainEntity(
            id = 100L,
            _orderLineItems = listOf(
                OrderLineItem(inventoryId = 1L, productId = "p1", skuCode = "SKU-001", price = 1000, quantity = 2),
                OrderLineItem(inventoryId = 2L, productId = "p2", skuCode = "SKU-002", price = 2000, quantity = 1),
            )
        )
        every { orderCommandOutboundPort.save(any()) } returns savedOrder

        val result = sut.create(command)

        assertThat(result.id).isEqualTo(100L)
        assertThat(result.status).isEqualTo(OrderStatus.PENDING)
        verify(exactly = 2) { createOrderInventoryRequestOutboxCommand.create(any()) }
    }

    @Test
    fun `updateOrderLineItemStatus 호출 시 주문을 조회하고 상태 업데이트 후 저장한다`() {
        val order = OrderDomainEntity(
            id = 1L,
            _orderLineItems = listOf(
                OrderLineItem(inventoryId = 10L, productId = "p1", skuCode = "SKU-001", price = 1000, quantity = 1)
            )
        )
        every { orderQueryOutboundPort.fetchOrder(1L) } returns order
        every { orderCommandOutboundPort.save(any()) } returns order

        sut.updateOrderLineItemStatus(orderId = 1L, inventoryId = 10L, status = OrderLineItemStatus.INVENTORY_RESERVED)

        assertThat(order.status).isEqualTo(OrderStatus.INVENTORY_RESERVED)
        verify { orderCommandOutboundPort.save(order) }
    }
}
