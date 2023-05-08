# SOLPONGE 프로젝트 JPA 버전업
팀 프로젝트로 진행한 solponge( https://github.com/Yongtapark/solponge.team.git )를 버전업한 개인프로젝트입니다.
## 개발 동기
기존의 팀프로젝트를 마치고, 이후에 공부를 진행하면서 좀더 코드를 직관적이고 객체지향적으로 만들수 있겠다 라는 생각이 들어 진행하게 되었습니다.
### 웹 실행 전 사전준비 (데이터 세팅) !! 현재 '사람인_공고별_데이터_03.csv' 가 깃에 올라가지 않아 작업중 !!
1. `scarpData` 디렉토리로 이동합니다.
cd scarpData
2. H2 DB에 `TableQuery`를 사용하여 테이블을 생성합니다.
3. `bookDataForH2.py`를 실행합니다.
python bookDataForH2.py
4. `JobDataForH2.py`를 실행합니다.
python JobDataForH2.py
5. 이제 준비가 끝났습니다.

### 사전 요구 사항
Java 11
SpringBoot
JPA
QueryDsl
H2 DB
### 테이블 관계도
![테이블](https://user-images.githubusercontent.com/91367204/236715659-0e14527c-9358-4f9b-9452-fae760194e85.PNG)

### 변경점
##### 1. 디렉토리 구조 및 명칭 변경
![제목 없음](https://user-images.githubusercontent.com/91367204/231708363-cea85c7b-f97d-440d-89bc-705868a334bb.png)
##### 2. 도메인
![제목 없음2](https://user-images.githubusercontent.com/91367204/231709970-4c1b6b95-ef58-431d-a074-37a133ee6f7b.png)
##### 3.로직 단순화(예시 : 장바구니의 상품을 Order로 넘기는 값을 기본 자료형 -> 객체로 받을수 있게 변경)
![1](https://user-images.githubusercontent.com/91367204/231712186-10ea61e7-d266-46ed-9e5d-8d61d33e7aee.PNG)
## 작성자
박용타

Yong-ta Park

bjjtachan@gmail.com
