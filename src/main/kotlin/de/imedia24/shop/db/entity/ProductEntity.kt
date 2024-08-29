package de.imedia24.shop.db.entity

import de.imedia24.shop.domain.product.ProductResponse
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

 @Entity
@Table(name = "products")
class ProductEntity(
    @Id
    @Column(name = "sku", nullable = false)
    var sku: String,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "description")
    var description: String,

    @Column(name = "price", nullable = false)
    var price: BigDecimal,

    @Column(name = "stock", nullable = false)
    var stock: Int = 0
) {
    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()

    constructor() : this("", "", "", BigDecimal.ZERO, 0)
}


fun ProductEntity.toProductResponse(): ProductResponse {
    return ProductResponse(
        sku = this.sku,
        name = this.name,
        description = this.description,
        price = this.price,
        stock = this.stock
    )
}
