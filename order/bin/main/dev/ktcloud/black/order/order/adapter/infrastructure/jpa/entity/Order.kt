package dev.ktcloud.black.order.order.adapter.infrastructure.jpa.entity

import dev.ktcloud.black.common.domain.entity.BaseOrmEntity
import dev.ktcloud.black.order.order.domain.vo.OrderLineItem
import dev.ktcloud.black.order.order.domain.vo.OrderStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.annotations.SQLDelete
import org.hibernate.type.SqlTypes

@SQLDelete(sql = "UPDATE orders SET deleted_at = NOW() WHERE id = ?")
@Entity
@Table(name = "orders")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column
    @Enumerated(EnumType.STRING)
    val status: OrderStatus,

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    val orderLineItems: List<OrderLineItem>,
): BaseOrmEntity()
