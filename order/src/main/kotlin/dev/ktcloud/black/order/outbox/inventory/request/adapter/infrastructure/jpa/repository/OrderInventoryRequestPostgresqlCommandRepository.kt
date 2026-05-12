package dev.ktcloud.black.order.outbox.inventory.request.adapter.infrastructure.jpa.repository

import dev.ktcloud.black.order.outbox.inventory.request.adapter.infrastructure.jpa.OrderInventoryRequestOutboxMapper
import dev.ktcloud.black.order.outbox.inventory.request.application.port.outbound.OrderInventoryRequestCommandOutboundPort
import dev.ktcloud.black.order.outbox.inventory.request.domain.entity.OrderInventoryRequestOutboxDomainEntity
import org.springframework.stereotype.Component

@Component
class OrderInventoryRequestPostgresqlCommandRepository(
    private val mapper: OrderInventoryRequestOutboxMapper,
    private val repository: OrderInventoryRequestPostgresqlRepository
): OrderInventoryRequestCommandOutboundPort {
    override fun save(orderInventoryRequest: OrderInventoryRequestOutboxDomainEntity) {
        val ormEntity = mapper.toOrmEntity(orderInventoryRequest)

        repository.save(ormEntity)
    }

    override fun saveAll(orderInventoryRequests: List<OrderInventoryRequestOutboxDomainEntity>) {
        val ormEntityList = mapper.toOrmEntity(orderInventoryRequests)

        repository.saveAll(ormEntityList)
    }
}