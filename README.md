# 프로젝트 소개
- 주제
  - 부동산 임장 정보 관리 서비스 개발
- 주제 선정 이유
  - 부동산 임장을 다닐 때 정보를 카톡으로 저장해왔는데 정보가 체계가 없어서 관리 측면에서 단점이 있었음. 그래서 임장 시 웬만하면 확인하면 좋은 정보에 대한 답만 입력하여 관리할 수 있는 서비스를 만들어보기로 함.
# 진행 상황
- [x] 웹 프론트엔드, 백엔드 개발

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
  - 건물 정보 관리
  - 세대 정보 관리
- 회원가입 및 로그인
  - Spring Security 사용 세션 기반 인증
- 사진 첨부
  - AWS S3 사용
- 주소 검색 및 지도에 마커 표시
  - Daum 주소 API
  - 네이버 지도 API
    - 마커 기능
    - Geocoder를 사용하여 주소 => 좌표 변환

# 서비스 배포 방법
1. Gradle 빌드
2. Docker 이미지를 빌드하여 AWS ECR로 Push
3. 업로드한 이미지를 AWS EC2에서 Pull
4. docker compose up -d 명령어를 실행하여 서버 시작

# ERD
![Alt text](image-1.png)
