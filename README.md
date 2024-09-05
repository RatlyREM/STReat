## A307 - 김유경🍀

안녕하세요. 특화 프로젝트 A307팀입니다.

## 2024.08.26

### 아이디어 회의

"P2P 대출 플랫폼" 프로젝트

#### **프로젝트 개요:**

이 P2P (Peer-to-Peer) 대출 플랫폼은 **소규모 대출**을 필요로 하는 사람들과 투자자들을 직접 연결하는 서비스입니다. 은행이나 전통적인 금융 기관을 통하지 않고도, 대출을 원하는 개인과 소규모 사업자들이 필요한 자금을 쉽게 확보할 수 있도록 돕는 것이 목표입니다.

앱의 주요 기능은 다음과 같습니다:

- **대출 요청 및 매칭**: 대출 신청자가 자신의 필요와 조건을 입력하면, 플랫폼은 투자자와 매칭해줍니다.
- **신용 평가**: 사용자의 신용도를 AI와 빅데이터를 통해 평가하여, 신용 점수가 낮은 사람도 합리적인 조건으로 대출을 받을 수 있도록 돕습니다.
- **자동화된 상환 관리**: 대출 상환이 자동으로 관리되며, 사용자는 상환 일정과 금액을 투명하게 볼 수 있습니다.

## 2024.08.27

### 2차 아이디어 회의

"티오더 모바일 버전" 프로젝트

#### **프로젝트 개요:**

- **메인 기능**
  - 개인화된 메뉴판
  - 태블릿 메뉴판 → 모바일 … 에서 전부 다
  - 직원 호출 + 메시지 (임시 채팅방 개념)
- **차별점**
  - 다인원일 경우 편함
  - (사장 입장) 돈 아낌
  - (유저 입장) 편하게 볼 수 있음

## 2024.08.28

### 아이디어 선정 후 구체화

💗**선정된 아이디어 - "모바일 오더" 프로젝트**

- **기획 의도**
  - 귀찮은 주문/결제 프로세스를 EZ하게 모바일로
- **메인 기능**
  - 태블릿 주문이 아닌, 각자의 휴대폰으로 주문
  - 결제 방식 다양화 / 단체, 개인별(메뉴별), 더치페이 가능
  - 매장 관리(매출, 메뉴 관리)
  - 추가 요청 사항 or 문의사항 채팅 기능(임시 채팅방)
  - 통합 포인트
- **세부 사항**
  - 태블릿 주문이 아닌, 각자의 휴대폰으로 주문
    - 테이블에 있는 QR 인식 → 메뉴 선택 및 주문 → 개인 or 테이블 주문 선택 → 최종 결제 시 개인이 내야 할 금액 자동 계산
    - 고객 주문 시 가게 POS(역할 하는 다른 기기) 자동 연동
  - 결제 방식 다양화 / 단체, 개인별(메뉴별), 더치페이 가능
    - 각자 주문 후 개인이 주문한 메뉴 계산 → 차별화
    - 단체 계산
    - 총 금액 1 / N 계산
    - 금액 지정 계산
    - 가상계좌 / 카드 결제
  - 매장 관리(매출, 메뉴)
    - 일, 주, 월, 연간 매출 관리
    - 상품별 매출 관리(어떤 상품이 잘 팔리는지)
    - 재고 관리
    - 메뉴 추가, 수정, 삭제 등
  - 추가 요청 사항 or 문의사항 채팅 기능(임시 채팅방)
    - 기존에 정해져있는 요청사항 외 추가적인 요청사항이 있을 경우, 직원 호출 없이 채팅만으로 요청 가능
    - 손님이 퇴장하고나면 채팅방은 사라짐
    - 음성인식으로 추가 요청사항을 텍스트로 요청하기 → 차별 포인트
  - 통합 포인트
    - 해당 서비스를 이용하는 매장간 사용할 수 있는 통합 포인트 기능
    - 같이 간사람들의 포인트를 모아서 사용할 수도 있음

## 2024.08.29

### 메인 기능 구체화

"티오더 모바일 버전" 프로젝트

#### **메인 기능:**

유저 기능

1. NFC 접근 방법을 사용한 각자 주문

- 본인 메뉴 계산
- 1/N 계산
- 지정 금액 계산

2. 결제 기능

- 가상 계좌를 이용한 송금 방식
- 카드 결제 방식
- 환불 → 후순위

3. 장바구니 기능

- 메뉴 조회, 추가, 삭제

관리자 기능

1. 테이블 합치기

- 주문한 메뉴 리스트 통합해서 보여주고, 합쳐진 테이블 번호 리스트 출력

2. 주문 정보 확인

3. 좌석 배치도 설정

- 초기 UI 기본적으로 제공
- 이후에 드래그로 요소 이동 → 후순위

4. 가게 매출 관리

- AI를 이용한 매출 관리 요약 → 받아온 데이터를 바탕으로 그래프 출력

5. 단골 관리

공통 기능

1. 채팅 기능 → 양방향

## 2024.08.30

### 다시 시작된 아이디어 회의

"티오더 모바일 버전" 프로젝트 -> 후보로 바뀜

#### **프로젝트 회의:**

- **일상 주제 소스**

1. 자전거
2. 스프링쿨러 -> 전기차
3. 주차 -> 주차영수증 공유 서비스
4. 흡연구역 -> 많이 봄
5. 간판이 불편
6. 전세 사기
7. 지구온난화
8. 의류수거함 -> 다 팜
9. 동물병원
10. 싱크홀
11. 전동 스쿠터 음주운전

# 2주차

## 2024.09.02

### 아이디어 확정

"푸드트럭" 서비스

#### **프로젝트 회의:**

##### 핵심 기능

- NFC를 이용한 간편 계좌 송금 (푸드트럭)

- 매출 관리 (AI를 활용한 매출 리포트)

- 주문 예약 시스템

1. 사이렌오더 같은 시스템 (= 테이블링)
2. NFC를 이용한 주문 예약 (줄이 긴 푸드트럭/도깨비 야시장)
3. 카드 결제/계좌 이체 선택 (선불 시스템)

- 개인별 메뉴 확인 및 리뷰 → 리뷰는 후순위

1. 알레르기 요소 있는 메뉴 정보 확인

- GPS를 통한 위치 업데이트

##### 부가 기능

- NFC를 이용한 간편 계좌 송금 (푸드트럭)
  → 인증 필요

- 매출 관리 (AI를 활용한 매출 리포트)
  → 사장님이 회원가입을 했을 경우 제공되는 기능
  → 대출 기능

- 영업 시작 버튼 클릭 시 해당 푸드트럭의 위치 표시 (GPS)
  → 사업자 등록 번호가 있는 푸드트럭만 가입 가능

- 외국인을 위한 환율 시스템..? (메뉴 가격이 해외돈으로 얼마인지 함께 표시)

- 앞에 몇팀이 있는지 정도를 표시 (주문 전/주문 후 모두 표시)

- NFC 태그를 이용해 본인의 음식 수령
  → NFC 태그 인식(스마트폰) → 쿠키, 세션스토리지 등등 주문정보 전송 → 정보에 맞는 음식 손님에게 전달 → 포장된 음식 태깅 처리 → 시스템에서 수령 완료 처리

## 2024.09.03

아이디어 확정 후 컨설턴트님 미팅

#### **미팅 내용 정리:**

1. NFC 가 가능한 휴대폰 기종은 언제부터?
   → 아이폰은 2014년, 삼성은 2011년부터 입니다.
2. NFC를 태그할 때 결제가 바로 되느냐?
   → 주문 URL 을 제공할 뿐 결제가 바로 되지는 않는다.
3. NFC로 물건을 주문하는 것이 보안적으로 안전한가? 예를 들면 주파수 조작..
4. GPS 기능과 사이렌오더 기능은 좋다고 생각
5. GPS를 반영한 매출관리 제안하심 (ex: 카카오 모빌리티)
6. 주변 메뉴별 검색 기능 있으면 좋겠다
7. 이미 주문한 가게를 GPS 상에서 근처에 있을 때 알람이 온다, 내가 주문한 위치에 해당 가게가 또 왔을 때 앱 알람이 오면 좋을 것 같다.
8. 주문이 발생했을 때 효과음이 났으면 좋겠다.
9. AI를 활용한 매출관리를 디벨롭했으면 좋겠다.
10. 사장입장에서 본인이 팔고 있는 메뉴가 많이 밀집한 곳은 피하도록 추천해주기

## 2024.09.04

1. 기능명세서 작성
2. 기능명세서를 바탕으로 와이어프레임 작업 시작

## 2024.09.05

1. 와이어프레임 러프하게 작성0. 사장입장에서 본인이 팔고 있는 메뉴가 많이 밀집한 곳은 피하도록 추천해주기

## 2024.09.04

1. 기능명세서 작성
2. 기능명세서를 바탕으로 와이어프레임 작업 시작

## 2024.09.05

1. 와이어프레임 러프하게 작성
