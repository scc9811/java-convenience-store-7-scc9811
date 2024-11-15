# java-convenience-store-precourse


<br>


## 프로젝트 개요 - 편의점 프로그램
사용자 입력으로 받은 (상품 종류, 가격 등) 을 기반으로, <br>
할인 혜택과 재고 상황을 고려해 최종 결제 금액을 계산하고 안내하는 결제 시스템입니다.

### 동작 순서
1. .md 파일에서 현재 재고를 읽어온 뒤 출력합니다.
2. 구매할 상품과 수량을 입력 받습니다.
3. 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량만큼 가져오지 않았을 경우, 혜택에 대한 안내 메시지를 출력합니다.
4. 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제할지 여부에 대한 안내 메시지를 출력합니다.
5. 멤버십 할인 적용 여부를 확인하기 위해 안내 메시지를 출력합니다.
6. 구매 상품 내역, 증정 상품 내역, 금액 정보를 출력합니다.
7. 추가 구매 여부를 확인하기 위한 안내 메시지를 출력합니다.
8. 만약, 재구매 의사가 있다면, 2~7을 반복합니다.


### 프로그램 실행 예시

```plaintext
안녕하세요. W편의점입니다.
현재 보유하고 있는 상품입니다.

- 콜라 1,000원 10개 탄산2+1
- 콜라 1,000원 10개
- 사이다 1,000원 8개 탄산2+1
- 사이다 1,000원 7개
- 오렌지주스 1,800원 9개 MD추천상품
- 오렌지주스 1,800원 재고 없음
- 탄산수 1,200원 5개 탄산2+1
- 탄산수 1,200원 재고 없음
- 물 500원 10개
- 비타민워터 1,500원 6개
- 감자칩 1,500원 5개 반짝할인
- 감자칩 1,500원 5개
- 초코바 1,200원 5개 MD추천상품
- 초코바 1,200원 5개
- 에너지바 2,000원 5개
- 정식도시락 6,400원 8개
- 컵라면 1,700원 1개 MD추천상품
- 컵라면 1,700원 10개

구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])
[콜라-3],[에너지바-5]

멤버십 할인을 받으시겠습니까? (Y/N)
Y 

==============W 편의점================
상품명		수량	금액
콜라		3 	3,000
에너지바 		5 	10,000
=============증	정===============
콜라		1
====================================
총구매액		8	13,000
행사할인			-1,000
멤버십할인			-3,000
내실돈			 9,000

감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)
Y

안녕하세요. W편의점입니다.
현재 보유하고 있는 상품입니다.

- 콜라 1,000원 7개 탄산2+1
- 콜라 1,000원 10개
- 사이다 1,000원 8개 탄산2+1
- 사이다 1,000원 7개
- 오렌지주스 1,800원 9개 MD추천상품
- 오렌지주스 1,800원 재고 없음
- 탄산수 1,200원 5개 탄산2+1
- 탄산수 1,200원 재고 없음
- 물 500원 10개
- 비타민워터 1,500원 6개
- 감자칩 1,500원 5개 반짝할인
- 감자칩 1,500원 5개
- 초코바 1,200원 5개 MD추천상품
- 초코바 1,200원 5개
- 에너지바 2,000원 재고 없음
- 정식도시락 6,400원 8개
- 컵라면 1,700원 1개 MD추천상품
- 컵라면 1,700원 10개

구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])
[콜라-10]

현재 콜라 4개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)
Y

멤버십 할인을 받으시겠습니까? (Y/N)
N

==============W 편의점================
상품명		수량	금액
콜라		10 	10,000
=============증	정===============
콜라		2
====================================
총구매액		10	10,000
행사할인			-2,000
멤버십할인			-0
내실돈			 8,000

감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)
Y

안녕하세요. W편의점입니다.
현재 보유하고 있는 상품입니다.

- 콜라 1,000원 재고 없음 탄산2+1
- 콜라 1,000원 7개
- 사이다 1,000원 8개 탄산2+1
- 사이다 1,000원 7개
- 오렌지주스 1,800원 9개 MD추천상품
- 오렌지주스 1,800원 재고 없음
- 탄산수 1,200원 5개 탄산2+1
- 탄산수 1,200원 재고 없음
- 물 500원 10개
- 비타민워터 1,500원 6개
- 감자칩 1,500원 5개 반짝할인
- 감자칩 1,500원 5개
- 초코바 1,200원 5개 MD추천상품
- 초코바 1,200원 5개
- 에너지바 2,000원 재고 없음
- 정식도시락 6,400원 8개
- 컵라면 1,700원 1개 MD추천상품
- 컵라면 1,700원 10개

구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])
[오렌지주스-1]

현재 오렌지주스은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)
Y

멤버십 할인을 받으시겠습니까? (Y/N)
Y

==============W 편의점================
상품명		수량	금액
오렌지주스		2 	3,600
=============증	정===============
오렌지주스		1
====================================
총구매액		2	3,600
행사할인			-1,800
멤버십할인			-0
내실돈			 1,800

감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)
N

```

### 클래스 소개
1. StoreController
* 프로그램의 전체적인 실행 흐름을 관리합니다.
* StoreHandler를 직접적으로 호출합니다.

2. StoreHandler
* StoreController 와 StoreService 클래스의 중간다리 역할을 합니다.
* StoreController의 역할을 분산하기 위해 정의한 클래스입니다.

3. StoreService
* 핵심 비지니스 로직을 처리하는 클래스입니다.
* 상품 가격 계산, 프로모션/멤버십 할인 계산 등의 로직이 포함되어 있습니다.

4. View
   1. InputView : 사용자의 입력을 담당합니다.
   2. OutputView : 프로그램의 출력을 담당합니다.


5. Validator
   1. ErrorMessage : 에러 메시지를 저장하는 Enum입니다.
   2. BooleanTypeValidator : Y 혹은 N의 입력을 대상으로 검증하는 클래스입니다.
   3. ParseValidator : 사용자의 상품-수량 입력의 형태를 검증하는 클래스입니다.
   4. ProductValidator : 상품의 존재 유무, 수량 부족을 검증하는 클래스입니다.


6. Util
   1. ParseUtil : 사용자 입력의 파싱을 위한 유틸리티 클래스입니다.


7. Entity
   1. FileReader : .md 파일을 읽어오기 위한 객체입니다.
   2. PresentedProduct : 프로모션으로 받은 상품 정보를 저장하기 위한 객체입니다.
   3. Product : 상품 정보를 저장하기 위한 객체입니다.
   4. Receipt : 영수증 정보를 저장하기 위한 객체입니다.
   5. RequestItem : 사용자의 요청 정보를 저장하기 위한 객체입니다.


## 구현 기능 목록


### 입력
- [X] 구매할 상품과 수량 입력
- [X] 프로모션 적용 상품 추가 여부 입력
- [X] 재고 부족 상품 프로모션 정가 결제 여부 입력
- [X] 멤버십 할인 적용 여부 입력
- [X] 추가 구매 여부 입력
- [X] 비정상적인 입력 예외 처리
  - [X] 구매 수량이 재고 수량을 초과한 경우
  - [X] 존재하지 않는 상품을 입력한 경우
  - [X] 구매할 상품과 수량 형식이 올바르지 않은 경우
  - [X] 기타 잘못된 입력의 경우


### 출력
- [X] 환영 인사 출력
- [X] 상품 정보 리스트 출력
- [X] 프로모션 적용 상품 안내 출력
- [X] 재고 부족 상품 프로모션 정가 결제 안내 출력
- [X] 멤버십 할인 적용 안내 출력
- [X] 구매 상품 내역, 증정 상품 내역, 금액 정보 출력
- [X] 추가 구매 안내 출력



### 로직 구현 사항
- [X] .md 파일 읽어오기
- [X] 각 상품 종류별 인스턴스 객체 생성
  - [X] 프로모션 상품이 존재하지만 일반 상품이 없는 경우 생성
- [X] 각 프로모션 종류별 인스턴스 객체 생성
- [X] 프로모션 상품 존재 유무 확인
- [X] 프로모션 기간 만족 여부 확인
- [X] 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우 제외
- [X] 가격 계산
  - [X] 프로모션 상품이 충분한 경우 프로모션 상품으로만 계산
  - [X] 프로모션 상품이 충분하지 않은 경우 프로모션 + 일반 상품으로 계산
  - [X] 멤버십 할인 금액 계산

