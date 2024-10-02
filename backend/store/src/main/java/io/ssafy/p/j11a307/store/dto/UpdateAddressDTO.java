package io.ssafy.p.j11a307.store.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "점포 주소 변경을 위한 DTO")
public record UpdateAddressDTO(
        @Schema(description = "새로운 점포 주소", example = "서울특별시 강남구 삼성동")
        String newAddress
) {}