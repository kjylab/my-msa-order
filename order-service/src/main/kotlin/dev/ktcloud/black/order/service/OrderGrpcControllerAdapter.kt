package dev.ktcloud.black.order.service

import dev.ktcloud.black.order.order.application.dto.OrderLineItemDto
import dev.ktcloud.black.order.order.application.port.inbound.CreateOrderCommand
import dev.ktcloud.black.order.order.application.port.inbound.FetchOrderQuery
import dev.ktcloud.black.order.order.application.port.inbound.FetchOrdersQuery
import dev.ktcloud.black.order.order.domain.vo.OrderStatus
import dev.ktcloud.black.order.service.adapter.presentation.web.inbound.grpc.CreateOrderRequest
import dev.ktcloud.black.order.service.adapter.presentation.web.inbound.grpc.Empty
import dev.ktcloud.black.order.service.adapter.presentation.web.inbound.grpc.FetchOrderRequest
import dev.ktcloud.black.order.service.adapter.presentation.web.inbound.grpc.FetchOrdersResponse
import dev.ktcloud.black.order.service.adapter.presentation.web.inbound.grpc.OrderListItemResponseDto
import dev.ktcloud.black.order.service.adapter.presentation.web.inbound.grpc.OrderResponseDto
import dev.ktcloud.black.order.service.adapter.web.inbound.OrderGrpcController
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class OrderGrpcControllerAdapter(
    private val createOrderCommand: CreateOrderCommand,
    private val fetchOrderQuery: FetchOrderQuery,
    private val fetchOrdersQuery: FetchOrdersQuery
): OrderGrpcController() {
    private fun buildOrderLineItemResponseDto(orderLineItemDto: OrderLineItemDto): OrderListItemResponseDto {
        return OrderListItemResponseDto.newBuilder()
            .setInventoryId(orderLineItemDto.inventoryId)
            .setProductId(orderLineItemDto.productId)
            .setSkuCode(orderLineItemDto.skuCode)
            .setPrice(orderLineItemDto.price)
            .setQuantity(orderLineItemDto.quantity)
            .setStatus(orderLineItemDto.status.name)
            .build()
    }

    private fun buildOrderResponseDto(
        id: Long,
        status: OrderStatus,
        orderLineItemDtos: List<OrderLineItemDto>
    ): OrderResponseDto {
        return OrderResponseDto.newBuilder()
            .setId(id)
            .setStatus(status.name)
            .addAllOrderLineItems(orderLineItemDtos.map(::buildOrderLineItemResponseDto))
            .build()
    }

    override suspend fun createOrder(request: CreateOrderRequest): OrderResponseDto {
        val created = createOrderCommand.create(
                request.itemsList.map {
                    CreateOrderCommand.In(
                        inventoryId = it.inventoryId,
                        productId = it.productId,
                        skuCode = it.skuCode,
                        price = it.price,
                        quantity = it.quantity,
                    )
                },
            )

        return buildOrderResponseDto(
            id = created.id,
            status = created.status,
            orderLineItemDtos = created.orderLineItems
        )
    }

    override suspend fun fetchOrder(request: FetchOrderRequest): OrderResponseDto {
        val order = fetchOrderQuery.fetchOrder(
            FetchOrderQuery.In(request.id)
        )

        return buildOrderResponseDto(
            id = order.id,
            status = order.status,
            orderLineItemDtos = order.orderLineItems
        )
    }

    override suspend fun fetchOrders(request: Empty): FetchOrdersResponse {
        val orders = fetchOrdersQuery.fetchOrders()

        val orderResponseDtos = orders.map {
            buildOrderResponseDto(
                id = it.id,
                status = it.status,
                orderLineItemDtos = it.orderLineItems
            )
        }

        return FetchOrdersResponse.newBuilder()
            .addAllOrders(orderResponseDtos)
            .build()
    }
}