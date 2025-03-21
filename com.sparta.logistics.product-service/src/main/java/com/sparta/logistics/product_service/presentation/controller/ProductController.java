//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.sparta.logistics.product_service.presentation.controller;

import com.sparta.logistics.product_service.application.dto.request.ProductCreateRequestDto;
import com.sparta.logistics.product_service.application.dto.request.ProductStockRequestDto;
import com.sparta.logistics.product_service.application.dto.request.ProductUpdateRequestDto;
import com.sparta.logistics.product_service.application.dto.response.ProductCreateResponseDto;
import com.sparta.logistics.product_service.application.dto.response.ProductGetResponseDto;
import com.sparta.logistics.product_service.application.dto.response.ProductSearchResponseDto;
import com.sparta.logistics.product_service.application.dto.response.ProductUpdateResponseDto;
import com.sparta.logistics.product_service.application.dto.response.StockUpdateResponseDto;
import com.sparta.logistics.product_service.application.service.ProductService;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/products"})
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ProductCreateResponseDto createProduct(@RequestBody ProductCreateRequestDto requestProductCreateDto) {
        return this.productService.createProduct(requestProductCreateDto);
    }

    @PutMapping({"/{productId}"})
    public ProductUpdateResponseDto updateProduct(@PathVariable("productId") UUID productId, @RequestBody ProductUpdateRequestDto requestProductUpdateDto) {
        return this.productService.updateProduct(productId, requestProductUpdateDto);
    }

    @GetMapping({"/search"})
    public Page<ProductSearchResponseDto> searchProducts(@RequestParam(required = false) String name, @RequestParam(required = false) UUID companyId, @RequestParam(required = false) UUID hubId, @RequestParam(required = false,defaultValue = "1") int page, @RequestParam(required = false,defaultValue = "10") int size, @RequestParam(required = false,defaultValue = "createdAt") String sortBy, @RequestParam(required = false,defaultValue = "asc") String order) {
        return this.productService.searchProducts(name, companyId, hubId, page - 1, size, sortBy, order);
    }

    @GetMapping({"/{productId}"})
    public ProductGetResponseDto getProduct(@PathVariable("productId") UUID productId) {
        return this.productService.getProduct(productId);
    }

    @DeleteMapping({"/{productId}"})
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") UUID productId) {
        return this.productService.deleteProduct(productId);
    }

    @PostMapping({"/{productId}/decrease-stock"})
    public ResponseEntity<StockUpdateResponseDto> decreaseStock(@PathVariable UUID productId, @RequestBody ProductStockRequestDto requestDto) {
        StockUpdateResponseDto response = this.productService.reduceStock(productId, requestDto.getQuantity());
        return ResponseEntity.ok(response);
    }

    @PostMapping({"/{productId}/increase-stock"})
    public ResponseEntity<StockUpdateResponseDto> increaseStock(@PathVariable UUID productId, @RequestParam Long quantity) {
        StockUpdateResponseDto response = this.productService.addStock(productId, quantity);
        return ResponseEntity.ok(response);
    }

    @GetMapping({"/{productId}/hub"})
    public ResponseEntity<UUID> getHubIdByProductId(@PathVariable("productId") UUID productId) {
        UUID hubId = this.productService.getHubIdByProductId(productId);
        return ResponseEntity.ok(hubId);
    }
}
