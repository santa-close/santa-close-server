# 개발 가이드

## Lint

### setting

```shell
./gradlew addKtlintCheckGitPreCommitHook
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

- Preferences -> Tools -> ktlint

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

- app/src/test/resources/application.yml

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
