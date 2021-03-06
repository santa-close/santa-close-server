# 개발 가이드

## Lint

### setting

> pre-commit 과 pre-push hook 이 동작하기 위해 사전에 `yarn` 으로 husky 를 설치해야 한다.

```shell
yarn
```

### check

```shell
./gradlew ktlintCheck
```

### format

```shell
./gradlew ktlintFormat
```

### IntelliJ IDEA 설정

파일 저장 시 자동 포맷팅 하려면 `ktlint` 플러그인 설치 후 아래처럼 설정

- Preferences -> Tools -> ktlint Settings

![ktlint](./image/ktlint.png)

- Preferences -> Tools -> Actions on Save

![actions-on-save](./image/actions-on-save.png)

## Database

### 로컬 DB 실행

요구사항: docker, docker-compose

```shell
docker-compose up
```

### 로깅 설정

테스트 코드 실행 시 발생한 sql 을 보고싶은 경우

- server-app/src/test/resources/application.yml

```yaml
spring:
  jpa:
    properties:
      hibernate:
        check_nullability: true
        use_sql_comments: true
        format_sql: true
        show_sql: true # true 로 변경

logging:
  level:
    org:
      hibernate:
        type: trace # trace 로 변경
```

### 서버 실행

```shell
./gradlew :app:bootRun
```

### 주의 사항

#### category 정보 업데이트

> git hook 이 제대로 설정된 상태라면 푸시하기 전에 아래 category 테스트가 자동으로 실행된다.

[CategoryAppList.kt](server-app/src/main/kotlin/com/santaclose/app/category/controller/dto/CategoryAppList.kt)
파일이 수정된 경우 category 응답 데이터를 업데이트 해야한다.
[CategoryAppControllerTest](server-app/src/test/kotlin/com/santaclose/app/category/controller/CategoryAppControllerTest.kt)
테스트를 실행하면 된다.

### 개발 환경 이슈

#### 테스트 실행 또는 서버 실행시, `unresolved reference: lib` 에러 발생

`./gradlew clean` 으로 다시 설치한다. 
