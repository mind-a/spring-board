# Spring Board Project

## 📋 프로젝트 설명
Spring Boot를 사용하여 게시판 기능을 구현한 프로젝트입니다.  
회원가입, 로그인, 게시물 CRUD 등의 기본적인 웹 애플리케이션 기능을 포함하고 있습니다.

---

## ⚙️ 기술 스택
- **Java**: 17
- **Spring Boot**: 3.4.0
- **Gradle**: 프로젝트 빌드 및 의존성 관리
- **Database**: PostgreSQL
- **Template Engine**: Thymeleaf (HTML 관리)

---

## 🛠️ 주요 기능
1. 회원가입 / 로그인
2. 게시물 목록 조회, 등록, 수정, 삭제
3. 회원 탈퇴 시 작성한 게시물 삭제
4. 예외 처리 및 유효성 검사
---

## 📂 프로젝트 구조
```plaintext
src
├── main
│   ├── java
│   │   └── com.mediopia.demo.board_project
│   │       ├── SecurityConfig    # Spring Security 설정 (인증, 인가, 보안 정책)
│   │       ├── controller    # REST API 컨트롤러
│   │       ├── service       # 비즈니스 로직 처리
│   │       ├── repository    # 데이터베이스 연동
│   │       ├── entity        # JPA 엔티티 클래스
│   │       └── exception     # 예외 처리
│   └── resources
│       ├── application.properties    # 설정 파일
│       ├── templates                 # Thymeleaf HTML 파일
│       │   ├── posts.html            # 게시물 목록 페이지
│       │   ├── ...
│       │   └── nav.html              # 상단바
└──     └── static                    # CSS 파일

```
---

### 사용자 관리
- **회원가입**: 유효성 검사를 통해 아이디와 비밀번호를 검증하며 회원가입.
- **로그인/로그아웃**: Spring Security를 활용한 보안 인증.
- **접근 제어**:
  - 일반 사용자는 자신의 게시물만 수정/삭제 가능.
  - 관리자는 모든 게시물에 대한 접근 권한 보유.

### 게시물 관리
- **게시물 작성**: 사용자가 새로운 게시물을 작성 가능.
- **게시물 목록 보기**: 페이징 처리된 게시물 목록 제공.
- **게시물 상세 보기**: 특정 게시물의 상세 내용을 확인.
- **게시물 수정**: 자신의 게시물만 수정 가능(관리자는 모든 게시물 수정 가능).
- **게시물 삭제**: 자신의 게시물만 삭제 가능(관리자는 모든 게시물 삭제 가능).

### 페이징 및 정렬
- 게시물은 페이징 표시.
- 게시물은 글 ID 기준으로 내림차순 정렬.

### 예외 처리
- GlobalExceptionHandler를 통해 전역적인 예외 처리.
- 기타 유효성 검사, 접근 권한 부족, 기타 오류 상황에 대해 메시지 제공.

---
## 📄 사용 라이브러리

- **Spring Boot**: 애플리케이션의 기본 프레임워크.
- **Spring Security**: 인증 및 권한 관리.
- **Spring Data JPA**: 데이터베이스와의 상호작용.
- **Thymeleaf**: 뷰 렌더링을 위한 템플릿 엔진.
- **PostgreSQL**: 데이터베이스.
- **Lombok**: 반복 코드를 줄이기 위한 도구.
- **Bootstrap**: 스타일링 및 반응형 디자인.
---

## 📄 API

| API             | 메서드 | 설명                           |
|---------------------|------|------------------------------|
| `/`                 | GET  | 게시물 목록으로 리다이렉트            |
| `/posts`            | GET  | 게시물 목록 표시         |
| `/posts/{id}`       | GET  | 특정 게시물의 상세 내용 표시          |
| `/posts/new`        | GET  | 게시물 작성 폼 표시                |
| `/posts/new`        | POST | 새 게시물 작성 처리                 |
| `/posts/edit/{id}`  | GET  | 게시물 수정 폼 표시                |
| `/posts/edit/{id}`  | POST | 게시물 수정 처리                   |
| `/posts/delete/{id}`| POST | 특정 게시물 삭제                   |
| `/login`                        | GET     | 로그인 페이지 표시                  |
| `/register`                     | GET     | 회원가입 폼 표시                   |
| `/register`                     | POST    | 회원가입 처리                      |
| `/members/delete`               | GET     | 회원 탈퇴 확인 페이지 표시             |
| `/members/delete`               | POST    | 회원 탈퇴 처리                      |
| `/admin/members/delete/{id}`    | POST    | 관리자가 특정 회원 삭제                |



