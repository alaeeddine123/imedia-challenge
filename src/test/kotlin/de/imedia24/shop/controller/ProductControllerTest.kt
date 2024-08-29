package de.imedia24.shop.controller

import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.service.ProductService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.http.HttpStatus
import java.math.BigDecimal



class ProductControllerTest {

     @Mock
    private lateinit var productService: ProductService

    @InjectMocks
    private lateinit var productController: ProductController

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

       @Test
    fun `findProductsBySkus should return products when they exist`() {
        // Given
        val skus = listOf("SKU1", "SKU2")
        val products = listOf(
            ProductResponse("SKU1", "Product 1", "Description 1", BigDecimal("10.00"),100),
            ProductResponse("SKU2", "Product 2", "Description 2", BigDecimal("20.00"),200)
        )
        `when`(productService.findProductBySkus(skus)).thenReturn(products)

        // When
        val result = productController.findProductsBySkus(skus)

        // Then
        assert(result.statusCode == HttpStatus.OK)
        assert(result.body == products)
    }

     @Test
    fun `findProductsBySkus should return not found when no products exist`() {
        // Given
        val skus = listOf("SKU1", "SKU2")
        `when`(productService.findProductBySkus(skus)).thenReturn(emptyList())

        // When
        val result = productController.findProductsBySkus(skus)

        // Then
        assert(result.statusCode == HttpStatus.NOT_FOUND)
        assert(result.body == null)
    }
}