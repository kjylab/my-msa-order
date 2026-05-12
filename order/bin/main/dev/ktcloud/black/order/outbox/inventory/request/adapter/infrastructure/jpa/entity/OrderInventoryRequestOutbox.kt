package dev.ktcloud.black.order.outbox.inventory.request.adapter.infrastructure.jpa.entity

import dev.ktcloud.black.common.domain.entity.BaseOrmEntity
import dev.ktcloud.black.order.outbox.inventory.request.domain.vo.OrderInventoryRequestOutboxStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.SQLDelete
import java.time.LocalDateTime

@SQLDelete(sql = "UPDATE order_inventory_request_outbox SET deleted_at = NOW() WHERE id = ?")
@Entity
@Table(name = "order_inventory_request_outbox")
data class OrderInventoryRequestOutbox(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column
    @Enumerated(EnumType.STRING)
    val status: OrderInventoryRequestOutboxStatus,

    @Column
    val retry: Int,

    @Column
    val nextStartFrom: LocalDateTime,

    @Column(name = "order_id")
    val orderId: Long,

    @Column(name = "inventory_id")
    val inventoryId: Long,

    @Column
    val amount: Int
): BaseOrmEntity()