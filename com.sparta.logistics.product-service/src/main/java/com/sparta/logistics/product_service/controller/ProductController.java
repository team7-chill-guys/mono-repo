package com.sparta.logistics.product_service.controller;

import com.sparta.logistics.product_service.dto.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @PostMapping
    public ResponseProductCreateDto createProduct(@RequestBody RequestProductCreateDto requestProductCreateDto) {
        UUID productId = UUID.randomUUID();

        ResponseProductCreateDto responseDto = ResponseProductCreateDto.builder()
                .id(productId)
                .companyId(requestProductCreateDto.getCompanyId())
                .hubId(requestProductCreateDto.getHubId())
                .name(requestProductCreateDto.getName())
                .stock(requestProductCreateDto.getStock())
                .build();

        return responseDto;
    }

    @PutMapping("/{productId}")
    public ResponseProductUpdateDto updateProduct(@PathVariable("productId") UUID productId,
                                                  @RequestBody RequestProductUpdateDto requestProductUpdateDto) {
        UUID companyId = UUID.randomUUID();
        UUID hubId = UUID.randomUUID();

        ResponseProductUpdateDto responseDto = ResponseProductUpdateDto.builder()
                .id(productId)
                .companyId(companyId)
                .hubId(hubId)
                .name(requestProductUpdateDto.getName())
                .stock(requestProductUpdateDto.getStock())
                .build();

        return responseDto;
    }

    @GetMapping("/search")
    public ResponseProductSearchDto searchProducts(@RequestParam(required = false) String name,
                                                   @RequestParam(required = false) UUID companyId,
                                                   @RequestParam(required = false) UUID hubId,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {

        List<ResponseProductGetDto> productDtoList = new ArrayList<>();

        for (int i = 1; i <= 20; i++) {
            if ((name == null || ("상품 " + i).contains(name)) &&
                    (companyId == null || companyId.equals(UUID.randomUUID())) &&
                    (hubId == null || hubId.equals(UUID.randomUUID()))) {

                ResponseProductGetDto productDto = ResponseProductGetDto.builder()
                        .id(UUID.randomUUID())
                        .companyId(UUID.randomUUID())
                        .hubId(UUID.randomUUID())
                        .name("상품 " + i)
                        .stock(100L * i)
                        .build();

                productDtoList.add(productDto);
            }
        }

        int start = page * size;
        int end = Math.min(start + size, productDtoList.size());
        List<ResponseProductGetDto> paginatedList = productDtoList.subList(start, end);

        return ResponseProductSearchDto.builder()
                .products(paginatedList)
                .page(page)
                .size(size)
                .build();
    }


    @GetMapping("/{productId}")
    public ResponseProductGetDto getProduct(@PathVariable("productId") UUID productId) {
        ResponseProductGetDto responseDto = ResponseProductGetDto.builder()
                .id(productId)
                .companyId(UUID.randomUUID())
                .hubId(UUID.randomUUID())
                .name("여행용 가방")
                .stock(100L)
                .build();

        return responseDto;
    }

    @DeleteMapping("/{productId}")
    public ResponseProductDeleteDto deleteProduct(@PathVariable("productId") UUID productId) {
        ResponseProductDeleteDto responseDto = ResponseProductDeleteDto.builder()
                .id(productId)
                .companyId(UUID.randomUUID())
                .hubId(UUID.randomUUID())
                .name("여행용 가방")
                .stock(100L)
                .build();

        return responseDto;
    }
}
