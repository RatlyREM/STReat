package io.ssafy.p.j11a307.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String name;
    // 상위 카테고리를 참조하는 parentCategoryId 필드
    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    private ProductCategory parentCategory;

    // 카테고리명 변경 메서드
    public void changeName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        this.name = name;
    }

    // 상위 카테고리를 계속해서 찾는 메서드 (재귀 호출)
    public ProductCategory getRootCategory() {
        if (this.parentCategory == null) {
            return this;
        }
        return this.parentCategory.getRootCategory();
    }

}