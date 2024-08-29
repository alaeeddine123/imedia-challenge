package de.imedia24.shop.controller

import de.imedia24.shop.domain.product.ProductRequest
import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.domain.product.ProductUpdateRequest
import de.imedia24.shop.service.ProductService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@Api(tags = ["Product Management"])
class ProductController(private val productService: ProductService) {

    private val logger = LoggerFactory.getLogger(ProductController::class.java)!!

    @GetMapping("/products/{sku}", produces = ["application/json;charset=utf-8"])
    @ApiOperation("Find a product by SKU")
    fun findProductsBySku(
        @PathVariable("sku") sku: String
    ): ResponseEntity<ProductResponse> {
        logger.info("Request for product $sku")

        val product = productService.findProductBySku(sku)
        return if(product == null) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(product)
        }
    }

     @GetMapping("/products",produces = ["application/json;charset=utf-8"])
     @ApiOperation("Find products by multiple SKUs")
    fun findProductsBySkus(@RequestParam("skus") skus: List<String>): ResponseEntity<List<ProductResponse>> {
         logger.info("Request for products with SKUs: $skus")
         val products = productService.findProductBySkus(skus)
         return if (products.isEmpty()){
             ResponseEntity.notFound().build()
         } else {
             ResponseEntity.ok(products)
         }
     }

     @PostMapping(consumes = ["application/json"], produces = ["application/json;charset=utf-8"])
     @ApiOperation("Add a new product")
     fun addProduct(@RequestBody productRequest: ProductRequest): ResponseEntity<ProductResponse>{
         val product = productService.addProduct(productRequest)
         return if(product != null){
             ResponseEntity.ok(product)
         }else{
             ResponseEntity.badRequest().build()
         }
     }


  @PatchMapping("/products/{sku}", consumes = ["application/json"], produces = ["application/json;charset=utf-8"])
  @ApiOperation("Partially update a product")
    fun updateProduct(
        @PathVariable("sku") sku: String,
        @RequestBody productUpdateRequest: ProductUpdateRequest
    ): ResponseEntity<ProductResponse> {
        logger.info("Request to update product $sku")
        val updatedProduct = productService.updateProduct(sku, productUpdateRequest)
        return if (updatedProduct != null) {
            ResponseEntity.ok(updatedProduct)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
