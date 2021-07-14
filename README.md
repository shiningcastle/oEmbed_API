---
###   oEmbed 정보 제공 서비스
---

**👨🏻‍💻 개발자 : 권희성**

---

## 📽 oEmbed 서비스 개발 시연 영상

---

<br>

[![link](https://t1.daumcdn.net/cfile/tistory/246D443E53575CCF11)](https://www.youtube.com/watch?v=PDkA2Cp53JU)

<br>

---

#### 1️⃣ 사용 스택 및 세팅

---

- Java 8 (1.8) <br>

- Spring Boot (Ver 2.5.2) <br>

- Vue.js (Vue Cli, ESLint & Prettier, Buefy) (vue cli, buefy, axios 설치필요)

- <a href ="https://java119.tistory.com/5">이클립스 UTF-8 세팅</a> <br>

<br>

---

#### 2️⃣ 설명

---

1. **Spring Boot** <br><br>

- 브라우저에서 URL 주소를 보내면 1차적으로 입력값 유효성 검증

- 입력받은 주소에서 고유한 Host 값과 reflection을 이용해 동적으로 해당 Host 처리 메서드를 찾아 실행

- oEmbed 요청 결과를 Json 객체 형태로 변환해서 반환

- 프런트에서도 어떤 Provider의 정보인지 알 수 있도록 Header에 기록해서 같이 반환

- 캐싱을 사용해서 같은 URL 요청은 빠르게 결과 반환

- 에러 발생시 사용자는 간단한 에러 메세지만 수신하도록 errMsg를 만들어 반환

- 간단한 테스팅(JUnit5) 및 로깅

<br>

2. **Vue.js** <br><br>

- 1차적으로 Input type으로 URL 형태 입력 검증

- axios로 http get요청으로 서버와 비동기 통신

- Provider마다 Json 데이터의 변수가 다르므로 v-for를 사용해서 동적으로 테이블 생성

- 서버에서 보낸 header 정보로 해당하는 Provider의 표만 생성

- 인스타그램은 access token 문제로 가능한 부분까지만 구현
