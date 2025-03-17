package com.sparta.logistics.product_service.service;

import com.sparta.logistics.product_service.dto.request.ProductCreateRequestDto;
import com.sparta.logistics.product_service.dto.request.ProductUpdateRequestDto;
import com.sparta.logistics.product_service.dto.response.*;
import com.sparta.logistics.product_service.entity.Product;
import com.sparta.logistics.product_service.repository.ProductRepository;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductCreateResponseDto createProduct(ProductCreateRequestDto requestProductCreateDto) {
        UUID findCompanyId = requestProductCreateDto.getCompanyId();
        UUID findHubId = requestProductCreateDto.getHubId();

        // 회사 존재 여부 로직
        // 담당 허브 존재 여부 로직

        Product product = Product.builder()
                .companyId(requestProductCreateDto.getCompanyId())
                .hubId(requestProductCreateDto.getHubId())
                .name(requestProductCreateDto.getName())
                .stock(requestProductCreateDto.getStock())
                .build();

        Product savedProduct = productRepository.save(product);

        return ProductCreateResponseDto.builder()
                .id(savedProduct.getId())
                .companyId(savedProduct.getCompanyId())
                .hubId(savedProduct.getHubId())
                .name(savedProduct.getName())
                .stock(savedProduct.getStock())
                .createdAt(savedProduct.getCreatedAt())
                .createdBy(savedProduct.getCreatedBy())
                .updatedAt(savedProduct.getUpdatedAt())
                .updatedBy(savedProduct.getUpdatedBy())
                .build();
    }

    public ProductUpdateResponseDto updateProduct(UUID productId, ProductUpdateRequestDto requestProductUpdateDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다: " + productId));

        if (requestProductUpdateDto.getName() != null) {
            product.setName(requestProductUpdateDto.getName());
        }

        if (requestProductUpdateDto.getStock() != null) {
            product.setStock(requestProductUpdateDto.getStock());
        }

        Product updatedProduct = productRepository.save(product);

        return ProductUpdateResponseDto.builder()
                .id(updatedProduct.getId())
                .companyId(updatedProduct.getCompanyId())
                .hubId(updatedProduct.getHubId())
                .name(updatedProduct.getName())
                .stock(updatedProduct.getStock())
                .updatedAt(updatedProduct.getUpdatedAt())
                .updatedBy(updatedProduct.getUpdatedBy())
                .build();
    }

    public Page<ProductSearchResponseDto> searchProducts(String name, UUID companyId, UUID hubId, int page, int size, String sortBy, String order) {
        if (size != 10 && size != 30 && size != 50) {
            size = 10;
        }

        Sort sort = Sort.by(Sort.Order.by(sortBy));
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> productList = productRepository.searchProducts(name, companyId, hubId, pageable);
        List<Product> products = productList.getContent();

        List<ProductGetResponseDto> productDtos = products.stream()
                .map(product -> ProductGetResponseDto.builder()
                        .id(product.getId())
                        .companyId(product.getCompanyId())
                        .hubId(product.getHubId())
                        .name(product.getName())
                        .stock(product.getStock())
                        .createdAt(product.getCreatedAt())
                        .createdBy(product.getCreatedBy())
                        .updatedAt(product.getUpdatedAt())
                        .updatedBy(product.getUpdatedBy())
                        .build())
                .collect(Collectors.toList());

        ProductSearchResponseDto productSearchResponseDto = ProductSearchResponseDto.builder()
                .products(productDtos)
                .page(productList.getNumber())
                .size(productList.getSize())
                .build();

        return new PageImpl<>(List.of(productSearchResponseDto), pageable, productList.getTotalElements());
    }

    public ProductGetResponseDto getProduct(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다: " + productId));

        return ProductGetResponseDto.builder()
                .id(product.getId())
                .companyId(product.getCompanyId())
                .hubId(product.getHubId())
                .name(product.getName())
                .stock(product.getStock())
                .createdAt(product.getCreatedAt())
                .createdBy(product.getCreatedBy())
                .updatedAt(product.getUpdatedAt())
                .updatedBy(product.getUpdatedBy())
                .build();
    }

    public ResponseEntity<Void> deleteProduct(UUID productId) {
        productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다: " + productId));

        productRepository.deleteById(productId);

        return ResponseEntity.ok().build();
    }

    public ProductIdResponseDto getProductIdByName(String name) {
        Product product = productRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("해당 상품 없음"));
        return ProductIdResponseDto.builder()
                .productId(product.getId())
                .build();
    }

    public ProductStockResponseDto getStockByProductId(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("해당 상품 없음"));
        return ProductStockResponseDto.builder()
                .productId(product.getId())
                .stock(product.getStock())
                .build();
    }
}
