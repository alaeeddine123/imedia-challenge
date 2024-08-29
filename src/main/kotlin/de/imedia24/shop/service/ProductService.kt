package de.imedia24.shop.service

import de.imedia24.shop.db.entity.ProductEntity
import de.imedia24.shop.db.entity.toProductResponse
import de.imedia24.shop.db.repository.ProductRepository
import de.imedia24.shop.domain.product.ProductRequest
import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.domain.product.ProductUpdateRequest
import org.springframework.stereotype.Service

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun findProductBySku(sku: String): ProductResponse? {
        val productEntity = productRepository.findBySku(sku)
        return productEntity?.toProductResponse()
    }

    fun findProductBySkus(skus: List<String>): List<ProductResponse> {
        val productEntities = productRepository.findBySkuIn(skus)
        return productEntities.map {
            it.toProductResponse()
        }

    }

    fun addProduct(productRequest: ProductRequest): ProductResponse? {
        val existingProduct = productRepository.findBySku(productRequest.sku)
        if (existingProduct != null) {
            return null
        }

        val productEntity = ProductEntity(
            sku = productRequest.sku,
            name = productRequest.name,
            description = productRequest.description,
            price = productRequest.price,
            stock = productRequest.stock
        )

        val savedProduct = productRepository.save(productEntity)

        return ProductResponse(
            sku = savedProduct.sku,
            name = savedProduct.name,
            description = savedProduct.description,
            price = savedProduct.price,
            stock = savedProduct.stock
        )
    }

    fun updateProduct(sku: String, updateRequest: ProductUpdateRequest): ProductResponse? {
        val existingProduct = productRepository.findBySku(sku) ?: return null

        updateRequest.name?.let { existingProduct.name = it}
        updateRequest.description?.let { existingProduct.description = it }
        updateRequest.price?.let { existingProduct.price = it }

        val updatedProduct = productRepository.save(existingProduct)
        return ProductResponse.fromEntity(updatedProduct)
    }
}
