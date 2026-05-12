package dev.ktcloud.black.order.service.adapter.presentation.web.inbound.grpc

import dev.ktcloud.black.order.service.adapter.presentation.web.inbound.grpc.OrderServiceGrpc.getServiceDescriptor
import io.grpc.CallOptions
import io.grpc.CallOptions.DEFAULT
import io.grpc.Channel
import io.grpc.Metadata
import io.grpc.MethodDescriptor
import io.grpc.ServerServiceDefinition
import io.grpc.ServerServiceDefinition.builder
import io.grpc.ServiceDescriptor
import io.grpc.Status.UNIMPLEMENTED
import io.grpc.StatusException
import io.grpc.kotlin.AbstractCoroutineServerImpl
import io.grpc.kotlin.AbstractCoroutineStub
import io.grpc.kotlin.ClientCalls.unaryRpc
import io.grpc.kotlin.ServerCalls.unaryServerMethodDefinition
import io.grpc.kotlin.StubFor
import kotlin.String
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

/**
 * Holder for Kotlin coroutine-based client and server APIs for order.service.OrderService.
 */
public object OrderServiceGrpcKt {
  public const val SERVICE_NAME: String = OrderServiceGrpc.SERVICE_NAME

  @JvmStatic
  public val serviceDescriptor: ServiceDescriptor
    get() = getServiceDescriptor()

  public val createOrderMethod: MethodDescriptor<CreateOrderRequest, OrderResponseDto>
    @JvmStatic
    get() = OrderServiceGrpc.getCreateOrderMethod()

  public val fetchOrderMethod: MethodDescriptor<FetchOrderRequest, OrderResponseDto>
    @JvmStatic
    get() = OrderServiceGrpc.getFetchOrderMethod()

  public val fetchOrdersMethod: MethodDescriptor<Empty, FetchOrdersResponse>
    @JvmStatic
    get() = OrderServiceGrpc.getFetchOrdersMethod()

  /**
   * A stub for issuing RPCs to a(n) order.service.OrderService service as suspending coroutines.
   */
  @StubFor(OrderServiceGrpc::class)
  public class OrderServiceCoroutineStub @JvmOverloads constructor(
    channel: Channel,
    callOptions: CallOptions = DEFAULT,
  ) : AbstractCoroutineStub<OrderServiceCoroutineStub>(channel, callOptions) {
    override fun build(channel: Channel, callOptions: CallOptions): OrderServiceCoroutineStub =
        OrderServiceCoroutineStub(channel, callOptions)

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun createOrder(request: CreateOrderRequest, headers: Metadata = Metadata()):
        OrderResponseDto = unaryRpc(
      channel,
      OrderServiceGrpc.getCreateOrderMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun fetchOrder(request: FetchOrderRequest, headers: Metadata = Metadata()):
        OrderResponseDto = unaryRpc(
      channel,
      OrderServiceGrpc.getFetchOrderMethod(),
      request,
      callOptions,
      headers
    )

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @param headers Metadata to attach to the request.  Most users will not need this.
     *
     * @return The single response from the server.
     */
    public suspend fun fetchOrders(request: Empty, headers: Metadata = Metadata()):
        FetchOrdersResponse = unaryRpc(
      channel,
      OrderServiceGrpc.getFetchOrdersMethod(),
      request,
      callOptions,
      headers
    )
  }

  /**
   * Skeletal implementation of the order.service.OrderService service based on Kotlin coroutines.
   */
  public abstract class OrderServiceCoroutineImplBase(
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
  ) : AbstractCoroutineServerImpl(coroutineContext) {
    /**
     * Returns the response to an RPC for order.service.OrderService.CreateOrder.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun createOrder(request: CreateOrderRequest): OrderResponseDto = throw
        StatusException(UNIMPLEMENTED.withDescription("Method order.service.OrderService.CreateOrder is unimplemented"))

    /**
     * Returns the response to an RPC for order.service.OrderService.FetchOrder.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun fetchOrder(request: FetchOrderRequest): OrderResponseDto = throw
        StatusException(UNIMPLEMENTED.withDescription("Method order.service.OrderService.FetchOrder is unimplemented"))

    /**
     * Returns the response to an RPC for order.service.OrderService.FetchOrders.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    public open suspend fun fetchOrders(request: Empty): FetchOrdersResponse = throw
        StatusException(UNIMPLEMENTED.withDescription("Method order.service.OrderService.FetchOrders is unimplemented"))

    final override fun bindService(): ServerServiceDefinition = builder(getServiceDescriptor())
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = OrderServiceGrpc.getCreateOrderMethod(),
      implementation = ::createOrder
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = OrderServiceGrpc.getFetchOrderMethod(),
      implementation = ::fetchOrder
    ))
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = OrderServiceGrpc.getFetchOrdersMethod(),
      implementation = ::fetchOrders
    )).build()
  }
}
