package io.ssafy.p.j11a307.product.dto;

import io.ssafy.p.j11a307.product.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "상품 정보를 수정하기 위한 DTO")
public record UpdateProductDTO(
        Integer id,                   // 상품 ID
        Integer storeId,               // 가게 ID
        String name,                   // 상품명
        Integer price,                 // 가격
        String src,                    // 이미지 경로
        List<UpdateProductCategoryDTO> categories, // 수정될 상품 카테고리 리스트
        List<UpdateProductOptionCategoryDTO> optionCategories // 수정될 옵션 카테고리 리스트
) {
        // Product 엔티티를 받아서 record로 변환하는 생성자
        public UpdateProductDTO(Product product) {
                this(
                        product.getId(),
                        product.getStoreId(),
                        product.getName(),
                        product.getPrice(),
                        product.getSrc(),
                        product.getCategories().stream()
                                .map(UpdateProductCategoryDTO::new)
                                .toList(),
                        product.getOptionCategories().stream()
                                .map(UpdateProductOptionCategoryDTO::new)
                                .toList()
                );
        }
}