# 개발

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
