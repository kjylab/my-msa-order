package dev.ktcloud.black.order.outbox.inventory.request.domain.entity

import dev.ktcloud.black.common.util.backoff.getNextRetryDateTime
import dev.ktcloud.black.common.util.time.now
import dev.ktcloud.black.order.outbox.inventory.request.domain.exception.OrderInventoryRequestOutboxException
import dev.ktcloud.black.order.outbox.inventory.request.domain.vo.OrderInventoryRequestOutboxStatus
import java.time.LocalDateTime

data class OrderInventoryRequestOutboxDomainEntity(
    val id: Long = 0L,
    private var _status: OrderInventoryRequestOutboxStatus = OrderInventoryRequestOutboxStatus.INIT,
    private var _retry: Int = 0,
    private var _nextStartFrom: LocalDateTime = now(),
    val orderId: Long,
    val inventoryId: Long,
    val amount: Int
) {
    val status: OrderInventoryRequestOutboxStatus
        get() = _status

    val retry: Int
        get() = _retry

    val nextStartFrom: LocalDateTime
        get() = _nextStartFrom

    fun updateStatus(newStatus: OrderInventoryRequestOutboxStatus) {
        if (!_status.checkTransitive(newStatus))
            throw OrderInventoryRequestOutboxException.IllegalStatusTransitive()

        _status = newStatus
    }

    fun increaseRetry() {
        if (_retry >= 5) throw OrderInventoryRequestOutboxException.OutboxFailedOverMaximum()

        _nextStartFrom = getNextRetryDateTime(_retry)

        _retry += 1
    }
}
