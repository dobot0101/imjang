# 프로젝트 소개

- 주제
    - 부동산 임장 정보 관리 서비스 개발
- 주제 선정 이유
    - 부동산 임장을 다닐 때 정보를 카톡으로 저장해왔는데 정보가 체계가 없어서 관리 측면에서 단점이 있었음. 그래서 임장 시 웬만하면 확인하면 좋은 정보에 대한 답만 입력하여 관리할 수 있는 서비스를
      만들어보기로 함.

# 진행 상황

- [x] 프론트엔드 개발
- [x] 백엔드 개발
- [x] 배포 (현재는 서비스 중단 상태)

# 사용 기술

- 언어 및 프레임워크
    - Java 17, Spring Boot 3.1.2, Spring Security, Spring Validation
- DB 및 ORM
    - PostgreSQL, QueryDSL, Spring Data JPA
- 클라우드 및 인프라
    - AWS(S3, EC2, ECR, RDS), Docker, Docker Compose
- 프론트엔드
    - Spring Thymeleaf, Javascript, JQuery, HTML, CSS
- 외부 API
    - 네이버 지도, 카카오 주소 찾기
- 버전관리
    - Github, Github Action

# 주요 기능

- 부동산 임장 정보 관리
    - 건물 정보 읽기/생성/수정/삭제
    - 세대 정보 읽기/생성/수정/삭제
- 회원가입 및 로그인
    - Spring Security 기반 인증
- 사진 첨부
    - AWS S3에 파일 업로드
- 주소 검색 및 지도에 마커 표시
    - Daum 주소 API
    - 네이버 지도 API
        - 주소 검색 및 Geocoder를 사용하여 좌표 변환
        - 좌표로 지도에 마커 표시

# 서비스 배포 방법

1. JAR 파일 빌드
2. Docker 이미지를 빌드하여 AWS ECR로 업로드
3. AWS EC2에서 ECR에 업로드한 Docker 이미지 다운로드
4. docker compose up -d 명령어를 실행하여 애플리케이션 실행

# ERD

![Alt text](image-1.png)
