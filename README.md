# 프로젝트 소개
- 부동산 임장을 다니면서 카카오톡으로 임장 내용을 기록하다가, 어느 집의 내용인지 모르거나 어딘지 알아도 목록으로 모아보기 어려운 문제 등을 직접 앱을 만들어 해결해보고자 시작한 프로젝트
- 프로젝트를 진행하면서 Java, Spring도 공부하고 싶었기 때문에 Java, SpringBoot로 개발하기로 결정
- React Native로 앱도 개발할 예정이며 해당 프로젝트는 백엔드에 해당함

# 사용 기술
- 언어: Java
- 프레임워크: Spring Boot
- DB: Postgresql
- 의존성(Dependencies): Spring Data Jpa, Spring Security, Spring Validation 등

# 프로젝트를 진행하며 생각한 것들
- 클린아키텍처
  - 오래전부터 "클린 아키텍처"라는 말이 많이 들려서 클린아키텍처가 무엇인지 몇번 찾아봤지만 막상 실무에는 적용할 수 없었다. 이 개인 프로젝트를 진행하면서 생각한 또 하나의 목표는 클린아키텍처가 무엇인지 프로젝트에 적용해보면서 공부하는 것이다. 아직 100% 이해한 것 같지는 않지만 나름 공부하고 이해한 바로는 결국 유지보수하기 좋고 확장성이 뛰어난 소프트웨어를 개발하기위한 여러 고민들이 합쳐지면서 탄생한 프로젝트 구조라는 생각이 들었다. 그리고 핵심은 직접 의존하던 구현 클래스를 인터페이스와 구현 클래스로 분리하고 인터페이스를 의존하게 함으로써 구현 클래스 간의 결합도를 낮추고 유연성을 향상시키며 테스트를 용이하게 하는 것이라고 이해했다.
  - 좀더 자세하게 얘기하면 클라이언트(사용하는 쪽)는 인터페이스에 의존하도록 하면...
    - 인터페이스를 사용하여 테스트 코드를 쉽게 작성할 수 있다.
    - 필요에 따라 다른 구현 클래스를 주입하여 동작을 변경할 수 있다.
    - 구현 클래스의 내용이 변경되어도 인터페이스, 클라이언트에는 아무 영향을 주지 않는다.
    - 즉, 구현 클래스 (Controller, Service, Repository 등) 간의 결합도를 낮추고 유연성을 향상시키며 테스트를 용이하게함.
- Spring Security, JWT를 사용한 인증
  - 다른 프로젝트에서는 로그인 시 JWT를 발급 받고 이후 프론트에서 HTTP 요청 헤더에 Authorization 값으로 담아서 요청을 보내면 미들웨어에서 유효성을 확인하는 식으로 인증/인가 처리를 하였는데, 이것을 Spring Security로 구현 중
