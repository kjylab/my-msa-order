package dev.ktcloud.black.order.order.adapter.infrastructure.jpa.repository

import dev.ktcloud.black.order.order.adapter.infrastructure.jpa.entity.Order
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderPostgresqlRepository: CrudRepository<Order, Long> {
}