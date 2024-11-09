# java-convenience-store-precourse


## 구현 기능 목록


### 입력
- [X] 구매할 상품과 수량 입력
- [X] 프로모션 적용 상품 추가 여부 입력
- [ ] 재고 부족 상품 프로모션 정가 결제 여부 입력
- [ ] 멤버십 할인 적용 여부 입력
- [ ] 추가 구매 여부 입력
- [ ] 비정상적인 입력 예외 처리
  - [X] 구매 수량이 재고 수량을 초과한 경우
  - [ ] 구매할 상품과 수량 형식이 올바르지 않은 경우
  - [ ] 존재하지 않는 상품을 입력한 경우
  - [ ] 기타 잘못된 입력의 경우
    - [ ] 구매 수량이 1 이상 정수가 아닌 경우
    - [ ]


### 출력
- [X] 환영 인사 출력
- [X] 상품 정보 리스트 출력
- [X] 프로모션 적용 상품 안내 출력
- [ ] 재고 부족 상품 프로모션 정가 결제 안내 출력
- [ ] 멤버십 할인 적용 안내 출력
- [ ] 구매 상품 내역, 증정 상품 내역, 금액 정보 출력
- [ ] 추가 구매 안내 출력



### 로직 구현 사항
- [X] .md 파일 읽어오기
- [X] 각 상품 종류별 인스턴스 객체 생성
- [X] 각 프로모션 종류별 인스턴스 객체 생성
- [X] 프로모션 상품 존재 유무 확인
- [X] 프로모션 기간 만족 여부 확인
- [ ] 가격 계산
  - [ ] 프로모션 상품이 충분한 경우 프로모션 상품으로만 계산
  - [ ] 프로모션 상품이 충분하지 않은 경우 프로모션 + 일반 상품으로 계산
  - [ ] 멤버십 할인 금액 계산

