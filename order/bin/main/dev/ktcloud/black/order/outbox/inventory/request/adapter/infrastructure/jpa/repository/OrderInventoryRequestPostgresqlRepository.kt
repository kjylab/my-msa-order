package dev.ktcloud.black.order.outbox.inventory.request.adapter.infrastructure.jpa.repository

import dev.ktcloud.black.order.outbox.inventory.request.adapter.infrastructure.jpa.entity.OrderInventoryRequestOutbox
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderInventoryRequestPostgresqlRepository: CrudRepository<OrderInventoryRequestOutbox, Long> {
}