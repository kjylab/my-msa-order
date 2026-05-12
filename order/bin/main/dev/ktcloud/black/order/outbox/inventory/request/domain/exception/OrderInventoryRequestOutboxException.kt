package dev.ktcloud.black.order.outbox.inventory.request.domain.exception

import dev.ktcloud.black.common.exception.CustomException
import org.springframework.http.HttpStatus

sealed class OrderInventoryRequestOutboxException {
    class IllegalStatusTransitive(message: String? = null, e: Throwable? = null): CustomException(
        "001",
        message ?: "間違えたステータス転移です",
        HttpStatus.BAD_REQUEST,
        e
    )

    class OutboxFailedOverMaximum(message: String? = null, e: Throwable? = null): CustomException(
        "002",
        message ?: "制限を超えた失敗数です",
        HttpStatus.BAD_REQUEST,
        e
    )
}