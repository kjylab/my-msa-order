package dev.ktcloud.black.order.outbox.inventory.request.adapter.infrastructure.jpa

import dev.ktcloud.black.common.adapter.infrastructure.jpa.EntityMapper
import dev.ktcloud.black.order.outbox.inventory.request.adapter.infrastructure.jpa.entity.OrderInventoryRequestOutbox
import dev.ktcloud.black.order.outbox.inventory.request.domain.entity.OrderInventoryRequestOutboxDomainEntity
import org.springframework.stereotype.Component

@Component
class OrderInventoryRequestOutboxMapper: EntityMapper<OrderInventoryRequestOutbox, OrderInventoryRequestOutboxDomainEntity> {
    override fun toOrmEntity(domainEntity: OrderInventoryRequestOutboxDomainEntity): OrderInventoryRequestOutbox {
        return OrderInventoryRequestOutbox(
            id = domainEntity.id,
            status = domainEntity.status,
            retry = domainEntity.retry,
            nextStartFrom = domainEntity.nextStartFrom,
            orderId = domainEntity.orderId,
            inventoryId = domainEntity.inventoryId,
            amount = domainEntity.amount,
        )
    }

    override fun toDomainEntity(entity: OrderInventoryRequestOutbox): OrderInventoryRequestOutboxDomainEntity {
        return OrderInventoryRequestOutboxDomainEntity(
            id = entity.id,
            _status = entity.status,
            _retry = entity.retry,
            _nextStartFrom = entity.nextStartFrom,
            orderId = entity.orderId,
            inventoryId = entity.inventoryId,
            amount = entity.amount,
        )
    }
}