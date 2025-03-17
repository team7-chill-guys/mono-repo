package com.sparta.logistics.product_service.controller;

import com.sparta.logistics.product_service.dto.request.ProductCreateRequestDto;
import com.sparta.logistics.product_service.dto.request.ProductUpdateRequestDto;
import com.sparta.logistics.product_service.dto.response.*;
import com.sparta.logistics.product_service.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ProductCreateResponseDto createProduct(@RequestBody ProductCreateRequestDto requestProductCreateDto) {
        return productService.createProduct(requestProductCreateDto);
    }

    @PutMapping("/{productId}")
    public ProductUpdateResponseDto updateProduct(@PathVariable("productId") UUID productId,
                                                  @RequestBody ProductUpdateRequestDto requestProductUpdateDto) {
        return productService.updateProduct(productId, requestProductUpdateDto);
    }

    @GetMapping("/search")
    public Page<ProductSearchResponseDto> searchProducts(@RequestParam(required = false) String name,
                                                   @RequestParam(required = false) UUID companyId,
                                                   @RequestParam(required = false) UUID hubId,
                                                   @RequestParam(required = false, defaultValue = "1") int page,
                                                   @RequestParam(required = false, defaultValue = "10") int size,
                                                   @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
                                                   @RequestParam(required = false, defaultValue = "asc") String order) {
        return productService.searchProducts(name, companyId, hubId, page-1, size, sortBy, order);
    }

    @GetMapping("/{productId}")
    public ProductGetResponseDto getProduct(@PathVariable("productId") UUID productId) {
        return productService.getProduct(productId);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") UUID productId) {
        return productService.deleteProduct(productId);
    }

    @GetMapping("/id")
    public ResponseEntity<ProductIdResponseDto> getProductIdByName(@RequestParam String name) {
        ProductIdResponseDto productId = productService.getProductIdByName(name);
        return ResponseEntity.ok(productId);
    }

    @GetMapping("/{productId}/stock")
    public ResponseEntity<ProductStockResponseDto> getProductStock(@PathVariable UUID productId) {
        ProductStockResponseDto productStock = productService.getStockByProductId(productId);
        return ResponseEntity.ok(productStock);
    }
}
