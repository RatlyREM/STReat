package io.ssafy.p.j11a307.store.controller;

import io.ssafy.p.j11a307.store.dto.CreateStoreLocationPhotoDTO;
import io.ssafy.p.j11a307.store.dto.ReadStoreLocationPhotoDTO;
import io.ssafy.p.j11a307.store.dto.UpdateStoreLocationPhotoDTO;
import io.ssafy.p.j11a307.store.global.DataResponse;
import io.ssafy.p.j11a307.store.global.MessageResponse;
import io.ssafy.p.j11a307.store.service.StoreLocationPhotoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/store-location-photos")
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class StoreLocationPhotoController {

    private final StoreLocationPhotoService storeLocationPhotoService;

    /**
     * StoreLocationPhoto 생성
     */
    @PostMapping
    @Operation(summary = "StoreLocationPhoto 생성")
    public ResponseEntity<MessageResponse> createStoreLocationPhoto(@RequestBody CreateStoreLocationPhotoDTO createDTO) {
        storeLocationPhotoService.createStoreLocationPhoto(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MessageResponse.of("StoreLocationPhoto 생성 성공"));
    }

    /**
     * StoreLocationPhoto 조회 (단건)
     */
    @GetMapping("/{id}")
    @Operation(summary = "StoreLocationPhoto 단일 조회")
    public ResponseEntity<DataResponse<ReadStoreLocationPhotoDTO>> getStoreLocationPhotoById(@PathVariable Integer id) {
        ReadStoreLocationPhotoDTO storeLocationPhoto = storeLocationPhotoService.getStoreLocationPhotoById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("StoreLocationPhoto 조회 성공", storeLocationPhoto));
    }

    /**
     * StoreLocationPhoto 전체 조회
     */
    @GetMapping
    @Operation(summary = "StoreLocationPhoto 전체 조회")
    public ResponseEntity<DataResponse<List<ReadStoreLocationPhotoDTO>>> getAllStoreLocationPhotos() {
        List<ReadStoreLocationPhotoDTO> storeLocationPhotos = storeLocationPhotoService.getAllStoreLocationPhotos();
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("StoreLocationPhoto 전체 조회 성공", storeLocationPhotos));
    }

    /**
     * StoreLocationPhoto 수정
     */
    @PutMapping("/{id}")
    @Operation(summary = "StoreLocationPhoto 수정")
    public ResponseEntity<MessageResponse> updateStoreLocationPhoto(@PathVariable Integer id, @RequestBody UpdateStoreLocationPhotoDTO updateDTO) {
        storeLocationPhotoService.updateStoreLocationPhoto(id, updateDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("StoreLocationPhoto 수정 성공"));
    }

    /**
     * StoreLocationPhoto 삭제
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "StoreLocationPhoto 삭제")
    public ResponseEntity<MessageResponse> deleteStoreLocationPhoto(@PathVariable Integer id) {
        storeLocationPhotoService.deleteStoreLocationPhoto(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("StoreLocationPhoto 삭제 성공"));
    }
}