package io.ssafy.p.j11a307.store.service;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import io.ssafy.p.j11a307.store.dto.StoreSimpleLocationDTO;
import io.ssafy.p.j11a307.store.entity.Store;
import io.ssafy.p.j11a307.store.entity.StoreLocationPhoto;
import io.ssafy.p.j11a307.store.entity.StoreSimpleLocation;
import io.ssafy.p.j11a307.store.exception.BusinessException;
import io.ssafy.p.j11a307.store.exception.ErrorCode;
import io.ssafy.p.j11a307.store.repository.StoreLocationPhotoRepository;
import io.ssafy.p.j11a307.store.repository.StoreRepository;
import io.ssafy.p.j11a307.store.repository.StoreSimpleLocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreSimpleLocationService {

    private final StoreSimpleLocationRepository simpleLocationRepository;
    private final StoreLocationPhotoRepository storeLocationPhotoRepository;
    private final StoreRepository storeRepository;
    private final AmazonS3 amazonS3;

    private final OwnerClient ownerClient;
    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    @Value("${streat.internal-request}")
    private String internalRequestKey;

    // token을 사용해 userId로 storeId 조회
    private Integer getStoreIdByToken(String token) {
        Integer userId = ownerClient.getUserId(token, internalRequestKey);
        if (userId == null) {
            throw new BusinessException(ErrorCode.OWNER_NOT_FOUND);
        }

        Store store = storeRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));
        return store.getId();
    }

    /**
     * 간편 위치 생성 및 Store 정보 업데이트
     */
    @Transactional
    public void createSimpleLocation(String token, StoreSimpleLocationDTO dto,  List<MultipartFile> images) {
        Integer storeId = getStoreIdByToken(token);
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        // StoreSimpleLocation 생성
        StoreSimpleLocation simpleLocation = StoreSimpleLocation.builder()
                .store(store)
                .address(dto.address())
                .latitude(dto.latitude())
                .longitude(dto.longitude())
                .nickname(dto.nickname())
                .createdAt(LocalDateTime.now())
                .build();

        // StoreSimpleLocation 저장
        simpleLocationRepository.save(simpleLocation);

        for (MultipartFile image : images) {
            validateImageFile(image);  // 이미지 파일 검증
            String imageUrl = uploadImageToS3(image);  // 이미지 S3에 업로드 후 URL 반환

            // StoreLocationPhoto 엔티티 생성 및 저장
            StoreLocationPhoto locationPhoto = StoreLocationPhoto.builder()
                    .storeSimpleLocation(simpleLocation)  // 연관된 StoreSimpleLocation 설정
                    .store(store)  // 연관된 Store 설정
                    .latitude(dto.latitude())  // DTO에서 가져온 위도 설정
                    .longitude(dto.longitude())  // DTO에서 가져온 경도 설정
                    .src(imageUrl)  // 업로드된 이미지 URL 설정
                    .createdAt(LocalDateTime.now())  // 현재 시간 설정
                    .build();

            storeLocationPhotoRepository.save(locationPhoto);
        }

        // Store 정보 업데이트 (address, latitude, longitude, status)
        store.updateLocationAndStatus(dto.address(), dto.latitude(), dto.longitude(), "OPEN");
        storeRepository.save(store);
    }



    /**
     * 간편 위치 정보만 생성
     * 가게 정보는 수정하지 않고 위치 정보만 생성하는 메소드
     */
    @Transactional
    public void createSimpleLocationOnly(String token, StoreSimpleLocationDTO dto, List<MultipartFile> images) {
        Integer storeId = getStoreIdByToken(token);
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        // StoreSimpleLocation 생성 및 저장 (가게 정보는 변경하지 않음)
        StoreSimpleLocation simpleLocation = StoreSimpleLocation.builder()
                .store(store)
                .address(dto.address())
                .latitude(dto.latitude())
                .longitude(dto.longitude())
                .nickname(dto.nickname())  // 닉네임도 추가
                .createdAt(LocalDateTime.now())
                .build();

        simpleLocationRepository.save(simpleLocation);


        // 이미지가 있을 경우 처리
        for (MultipartFile image : images) {
            validateImageFile(image);  // 이미지 파일 검증
            String imageUrl = uploadImageToS3(image);  // 이미지 S3에 업로드 후 URL 반환

            // StoreLocationPhoto 엔티티 생성 및 저장
            StoreLocationPhoto locationPhoto = StoreLocationPhoto.builder()
                    .storeSimpleLocation(simpleLocation)  // 연관된 StoreSimpleLocation 설정
                    .store(store)  // 연관된 Store 설정
                    .latitude(dto.latitude())  // DTO에서 가져온 위도 설정
                    .longitude(dto.longitude())  // DTO에서 가져온 경도 설정
                    .src(imageUrl)  // 업로드된 이미지 URL 설정
                    .createdAt(LocalDateTime.now())  // 현재 시간 설정
                    .build();

            storeLocationPhotoRepository.save(locationPhoto);
        }
    }

    /**
     * 특정 가게의 간편 위치 목록 조회
     */
    @Transactional(readOnly = true)
    public List<StoreSimpleLocation> getSimpleLocation(String token) {
        Integer storeId = getStoreIdByToken(token);
        return simpleLocationRepository.findByStoreId(storeId);
    }

    /**
     * 간편 위치 수정
     */
    @Transactional
    public void updateSimpleLocation(String token, Integer locationId, StoreSimpleLocationDTO dto, List<MultipartFile> images) {
        Integer storeId = getStoreIdByToken(token);
        StoreSimpleLocation simpleLocation = simpleLocationRepository.findById(locationId)
                .orElseThrow(() -> new BusinessException(ErrorCode.LOCATION_NOT_FOUND));

        if (!simpleLocation.getStore().getId().equals(storeId)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED_USER);
        }

        // 간편 위치 정보 수정
        simpleLocation.updateLocation(dto.address(), dto.latitude(), dto.longitude(), dto.nickname(), LocalDateTime.now());

        System.out.println("simpleLocation은!!!"+simpleLocation);
        // 기존 이미지 삭제 및 새 이미지 추가
        storeLocationPhotoRepository.deleteByStoreSimpleLocation(simpleLocation);  // 기존 이미지를 모두 삭제

        if (images != null && !images.isEmpty()) {
            for (MultipartFile image : images) {
                validateImageFile(image);  // 이미지 파일 검증
                String imageUrl = uploadImageToS3(image);  // 이미지 S3에 업로드 후 URL 반환

                // StoreLocationPhoto 엔티티 생성 및 저장
                StoreLocationPhoto locationPhoto = StoreLocationPhoto.builder()
                        .storeSimpleLocation(simpleLocation)  // 연관된 StoreSimpleLocation 설정
                        .store(simpleLocation.getStore())  // 연관된 Store 설정
                        .latitude(dto.latitude())  // DTO에서 가져온 위도 설정
                        .longitude(dto.longitude())  // DTO에서 가져온 경도 설정
                        .src(imageUrl)  // 업로드된 이미지 URL 설정
                        .createdAt(LocalDateTime.now())  // 현재 시간 설정
                        .build();

                storeLocationPhotoRepository.save(locationPhoto);
            }
        }

        // 간편 위치 정보 저장 (수정된 정보 반영)
        simpleLocationRepository.save(simpleLocation);
    }
    /**
     * 간편 위치 삭제
     */
    @Transactional
    public void deleteSimpleLocation(String token, Integer locationId) {
        Integer storeId = getStoreIdByToken(token);
        StoreSimpleLocation simpleLocation = simpleLocationRepository.findById(locationId)
                .orElseThrow(() -> new BusinessException(ErrorCode.LOCATION_NOT_FOUND));

        if (!simpleLocation.getStore().getId().equals(storeId)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED_USER);
        }

        simpleLocationRepository.delete(simpleLocation);
    }

    /**
     * S3에 이미지 업로드
     */
    private String uploadImageToS3(MultipartFile imageFile) {
        try {
            validateImageFile(imageFile);
            String originalFilename = imageFile.getOriginalFilename();
            assert originalFilename != null;
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String s3Filename = UUID.randomUUID().toString().substring(0, 10) + originalFilename;

            try (InputStream is = imageFile.getInputStream()) {
                byte[] bytes = is.readAllBytes();
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentType("image/" + extension);
                metadata.setContentLength(bytes.length);

                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

                PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, s3Filename, byteArrayInputStream, metadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead);

                amazonS3.putObject(putObjectRequest);
                return amazonS3.getUrl(bucketName, s3Filename).toString();
            }
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.FILE_UPLOAD_FAIL);
        }
    }


    /**
     * 이미지 파일 검증
     */
    private void validateImageFile(MultipartFile imageFile) {
        if (imageFile == null || imageFile.isEmpty() || imageFile.getOriginalFilename() == null) {
            throw new BusinessException(ErrorCode.STORE_SIMPLE_PHOTO_NULL);
        }

        int lastDotIndex = imageFile.getOriginalFilename().lastIndexOf(".");
        if (lastDotIndex == -1) {
            throw new BusinessException(ErrorCode.INVALID_FILE_EXTENSION);
        }

        String extension = imageFile.getOriginalFilename().substring(lastDotIndex + 1).toLowerCase();
        List<String> allowedExtensions = List.of("jpg", "jpeg", "png", "gif");

        if (!allowedExtensions.contains(extension)) {
            throw new BusinessException(ErrorCode.INVALID_FILE_EXTENSION);
        }
    }
}