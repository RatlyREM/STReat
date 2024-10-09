package io.ssafy.p.j11a307.user.service;

import io.ssafy.p.j11a307.user.dto.DibsStoreStatusResponse;
import io.ssafy.p.j11a307.user.dto.StoreDibsResponse;
import io.ssafy.p.j11a307.user.entity.Subscription;
import io.ssafy.p.j11a307.user.exception.BusinessException;
import io.ssafy.p.j11a307.user.exception.ErrorCode;
import io.ssafy.p.j11a307.user.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DibsService {

    @Value("${streat.internal-request}")
    private String internalRequestKey;

    private final UserService userService;
    private final FcmService fcmService;
    private final StoreService storeService;

    private final SubscriptionRepository subscriptionRepository;

    @Transactional
    public void subscript(String accessToken, Integer storeId) {
        Integer userId = userService.getUserId(accessToken);
        Subscription.SubscriptionId subscriptionId = new Subscription.SubscriptionId(userId, storeId);
        fcmService.subscribeStore(storeId, internalRequestKey, userId);
        subscriptionRepository.save(new Subscription(subscriptionId));
    }

    @Transactional
    public void unsubscribe(String accessToken, Integer storeId) {
        Integer userId = userService.getUserId(accessToken);
        Subscription.SubscriptionId subscriptionId = new Subscription.SubscriptionId(userId, storeId);
        fcmService.unsubscribeStore(storeId, internalRequestKey, userId);
        subscriptionRepository.deleteById(subscriptionId);
    }

    @Transactional
    public void changeStoreAlertStatus(String accessToken, Integer storeId, boolean alertOn) {
        Integer userId = userService.getUserId(accessToken);
        if (!userService.isCustomer(userId)) {
            throw new BusinessException(ErrorCode.CUSTOMER_NOT_FOUND);
        }
        // 찜한 가게가 아니면 알림 켜고 끌 수 없음
        Subscription.SubscriptionId subscriptionId = new Subscription.SubscriptionId(userId, storeId);
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_SUBSCRIBED_STORE));
        subscription.changeAlertStatus(alertOn);
        if (alertOn) {
            fcmService.subscribeStore(storeId, internalRequestKey, userId);
        } else {
            fcmService.unsubscribeStore(storeId, internalRequestKey, userId);
        }
    }

    public List<StoreDibsResponse> getAllDibs(String accessToken) {
        Integer userId = userService.getUserId(accessToken);
        if (!userService.isCustomer(userId)) {
            throw new BusinessException(ErrorCode.CUSTOMER_NOT_FOUND);
        }
        // store이름, 별점 구해오는 api 연결하기
        List<Subscription> subscriptions = subscriptionRepository.findBySubscriptionIdUserId(userId);
        // store Id 추출
        List<Integer> storeIds = subscriptions.stream().map(Subscription::getSubscriptionId).map(Subscription.SubscriptionId::getStoreId).toList();

        // store server에서 store id, store name, status 추출
        List<DibsStoreStatusResponse> dibsStoreStatusResponse = storeService.getStoreStatusByIds(storeIds);

        // store id -> name, status를 저장한 객체를 가리킨 Map 생성
        Map<Integer, DibsStoreStatusResponse> storeIdToDibsStoreStatusResponse =
                dibsStoreStatusResponse.stream().collect(Collectors.toMap(DibsStoreStatusResponse::storeId, store -> store));

        List<StoreDibsResponse> storeDibsResponses = subscriptions.stream().map(subscription ->
                StoreDibsResponse.builder()
                        .storeId(subscription.getSubscriptionId().getStoreId())
                        .storeName(storeIdToDibsStoreStatusResponse.get(subscription.getSubscriptionId().getStoreId()).name())
                        .status(storeIdToDibsStoreStatusResponse.get(subscription.getSubscriptionId().getStoreId()).status())
                        .averageScore(2.5)
                        .alertOn(subscription.getAlertOn()).build()).toList();
        return storeDibsResponses;
    }

    public Boolean calledDibs(String accessToken, Integer storeId) {
        Integer userId = userService.getUserId(accessToken);
        if (!userService.isCustomer(userId)) {
            throw new BusinessException(ErrorCode.CUSTOMER_NOT_FOUND);
        }
        Subscription.SubscriptionId subscriptionId = new Subscription.SubscriptionId(userId, storeId);
        return subscriptionRepository.existsById(subscriptionId);
    }

    public List<Integer> getCalledDibsUserIds(Integer storeId) {
        List<Subscription> subscriptions = subscriptionRepository.findBySubscriptionIdStoreId(storeId);
        return subscriptions.stream().map(Subscription::getSubscriptionId)
                .map(Subscription.SubscriptionId::getUserId)
                .collect(Collectors.toList());
    }
}