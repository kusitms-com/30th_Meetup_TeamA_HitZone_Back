## ⚾️ HitZone Backend

### 사용 스택 & 선정 이유

🛠 **Stacks**

- `Spring Boot 3.3.4`
- `JDK 17`
- `MySQL`
- `NCP Object Storage` : 정적 리소스 파일을 관리하기 위해 사용하였습니다.
- `NCP Clova Studio` : 챗봇 답변을 도출하기 위한 AI 기능으로 활용하였습니다.
- `Redis` : Refresh Token 관리 & 분산 락에 활용하였습니다.
- `Jwt Token & Cookie` : 유저를 인증하고, 토큰을 안전하게 보관 및 전달하기 위해 사용하였습니다.
- `Nginx` : 웹 서버, 리버스 프록시 등을 사용해 `블루 ↔ 그린 무중단 배포`에 활용하였습니다.
- `Docker` : 프로젝트를 빌드하고 NCP 인스턴스 내부에서 서버 컨테이너를 실행하는데 활용하였습니다.
- `Docker Compose` : 여러 컨테이너가 동일한 환경 & 네트워크에서 실행되도록 하며, 간편하게 컨테이너들을 관리하기 위해 사용하였습니다.
- `NCP Container Registry` : Docker Image를 관리하는데 사용하였습니다.
- `Github Actions` : CICD 작업을 수행하는데 활용하였습니다.

### 시스템 아키텍처

<img width="755" alt="히트존_아키텍처" src="https://github.com/user-attachments/assets/ea164722-083b-4f62-bcf3-a3a9f0c98172">

### ERD

<img width="1162" alt="히트존_ERD" src="https://github.com/user-attachments/assets/68a9d4bb-efc1-4504-9b0a-fc21bd015150">

### **Issue & PR**

- `Code Review & Approve` 룰 설정
    - 상대방이 `Approve` 올린 사람이 `Merge`

### Branch

- 생성한 이슈에 따라서 브랜치 생성 `Ex) feature/#4/login`
- `main branch` : 개발 최종 완료 시 merge
- `develop branch` : 개발 진행
- `feature branch` : 각 새로운 기능 개발
- `hotfix branch` : 배포 이후 긴급 수정
- `refactor branch` : 리팩토링 진행

### **Commit Message**

- 이슈 번호 붙여서 커밋 `Ex) #4 [feat] : 로그인 기능을 추가한다`
- Body는 추가 설명 필요하면 사용

| ***작업태그*** | ***내용*** |
| --- | --- |
| **feat** | 새로운 기능 추가 / 일부 코드 추가 / 일부 코드 수정 (리팩토링과 구분) / 디자인 요소 수정 |
| **fix** | 버그 수정 |
| **refactor** | 코드 리팩토링 |
| **style** | 코드 의미에 영향을 주지 않는 변경사항 (코드 포맷팅, 오타 수정, 변수명 변경, 에셋 추가) |
| **chore** | 빌드 부분 혹은 패키지 매니저 수정 사항 / 파일 이름 변경 및 위치 변경 / 파일 삭제 |
| **docs** | 문서 추가 및 수정 |
| **rename** | 패키지 혹은 폴더명, 클래스명 수정 (단독으로 시행하였을 시) |
| **remove** | 패키지 혹은 폴더, 클래스를 삭제하였을 때 (단독으로 시행하였을 시) |

### Naming

- **패키지명** : 한 단어 소문자 사용 `Ex) service`
- **클래스명** : 파스칼 케이스 사용 `Ex) JwtUtil`
- **메서드명** : 카멜 케이스 사용, 동사로 시작  `Ex) getUserScraps`
- **변수명** : 카멜 케이스 사용 `Ex) jwtToken`
- **상수명** : 대문자 사용 `Ex) EXPIRATION_TIME`
- **컬럼명** : 스네이크 케이스 사용 `Ex) user_id`

### Package

> DDD 방식을 사용하였습니다.

- global
- result
    - application
        - dto
            - request
            - response
        - service
    - domain
        - enums
        - model
        - repository
        - service
        - util
    - presentation
    - status
    - infra
        - jpa
            - entity
            - repository
            - repositoryImpl
        - mapper
- stadium
- user

### API Documentation

[REST Docs + Swagger 📑](https://git.hitzone.store/swagger-ui/index.html#/Chatbot/chatbot%2Fguide_1)

### API Response

```json
{
  "isSuccess": true,
  "code": "200",
  "message": "가이드 챗봇 답변을 가져오는 데 성공했습니다.",
  "payload": {
    "answers": [
      "야구장 입장 가능 시간은 경기 시작 시간을 기준으로 평일 90분 전, 주말 120분 전입니다.",
      "또한, 경기 시작 이후에도 언제든지 입장이 가능하고 경기가 모두 끝나지 않은 이후라도 언제든지 원하는 시간에 퇴장도 가능해요!"
    ],
    "imgUrl": null
  }
}
```

- `isSuccess` : 성공 여부
- `code` : 성공 코드, HTTP 상태 코드와 동일함
- `message` : 성공 메세지, 커스텀 가능
- `payload` : 데이터가 들어가는 곳

### 1차 리팩토링 진행

[[A팀] 백엔드 파트 코드리뷰용 PR by bbbang105 · Pull Request #44 · KUSITMS-30th-TEAM-A/backend](https://github.com/KUSITMS-30th-TEAM-A/backend/pull/44)

- 심사위원분께서 코드리뷰 해주신 코멘트를 기반으로 `refactor-v1` 브랜치에서 1차 리팩토링을 진행중입니다.

<img width="866" alt="1" src="https://github.com/user-attachments/assets/5dfcda02-3f32-4886-8a91-3ce3ba348329">|<img width="866" alt="2" src="https://github.com/user-attachments/assets/078639ad-4868-4ac3-80e9-c4d9c052b81b">
---|---|
