package dev.ktcloud.black.order.order.adapter.infrastructure.jpa.entity

import dev.ktcloud.black.common.domain.entity.BaseOrmEntity
import dev.ktcloud.black.order.order.domain.vo.OrderLineItem
import dev.ktcloud.black.order.order.domain.vo.OrderStatus
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Table
import org.hibernate.annotations.SQLDelete

@SQLDelete(sql = "UPDATE orders SET deleted_at = NOW() WHERE id = ?")
@Entity
@Table(name = "orders")
class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column
    @Enumerated(EnumType.STRING)
    val status: OrderStatus,

    @ElementCollection
    @CollectionTable(
        name = "order_items",
        joinColumns = [JoinColumn(name = "order_id")]
    )
    val orderLineItems: MutableList<OrderLineItem> = mutableListOf(),
): BaseOrmEntity()
