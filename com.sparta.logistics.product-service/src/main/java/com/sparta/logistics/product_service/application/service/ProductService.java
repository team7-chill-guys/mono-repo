package com.sparta.logistics.product_service.application.service;

import com.sparta.logistics.product_service.application.dto.request.ProductCreateRequestDto;
import com.sparta.logistics.product_service.application.dto.request.ProductUpdateRequestDto;
import com.sparta.logistics.product_service.application.dto.response.*;
import com.sparta.logistics.product_service.client.CompanyClient;
import com.sparta.logistics.product_service.client.HubClient;
import com.sparta.logistics.product_service.client.dto.CompanyResponseDto;
import com.sparta.logistics.product_service.client.dto.HubResponseDto;
import com.sparta.logistics.product_service.domain.entity.Product;
import com.sparta.logistics.product_service.domain.repository.ProductRepository;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CompanyClient companyClient;
    private final HubClient hubClient;

    public ProductService(ProductRepository productRepository, CompanyClient companyClient, HubClient hubClient) {
        this.productRepository = productRepository;
        this.companyClient = companyClient;
        this.hubClient = hubClient;
    }

    public ProductCreateResponseDto createProduct(ProductCreateRequestDto requestProductCreateDto) {
        UUID findCompanyId = requestProductCreateDto.getCompanyId();
        UUID findHubId = requestProductCreateDto.getHubId();

        CompanyResponseDto companyResponse = companyClient.getCompany(findCompanyId);
        if (companyResponse == null) {
            throw new IllegalArgumentException("회사를 찾을 수 없습니다: " + findCompanyId);
        }

        HubResponseDto hubResponse = hubClient.getHub(findHubId);
        if (hubResponse == null) {
            throw new IllegalArgumentException("허브를 찾을 수 없습니다: " + findHubId);
        }

        Product product = Product.builder()
                .companyId(findCompanyId)
                .hubId(findHubId)
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
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다: " + productId));

        product.setDeletedAt(new Timestamp(System.currentTimeMillis()));

        productRepository.save(product);

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
                .availableStock(product.getStock())
                .build();
    }

    public void reduceStock(UUID productId, long quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("해당 상품 없음"));

        product.decreaseStock(quantity);
        product.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        productRepository.save(product);
    }

    public void addStock(UUID productId, long quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("해당 상품 없음"));

        product.increaseStock(quantity);
        product.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        productRepository.save(product);
    }

    public ProductHubIdResponseDto getHubIdByProductId(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("해당 상품 없음"));
        return ProductHubIdResponseDto.builder()
                .hubId(product.getHubId())
                .build();
    }
}
