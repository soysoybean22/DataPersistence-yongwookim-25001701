# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# 빌드
./gradlew build

# 전체 테스트 실행
./gradlew test

# 단일 테스트 클래스 실행
./gradlew test --tests "com.example.SomeTest"

# 단일 테스트 메서드 실행
./gradlew test --tests "com.example.SomeTest.methodName"

# 테스트 결과 리포트 확인 (실행 후)
# build/reports/tests/test/index.html

# 빌드 캐시 정리 후 재빌드
./gradlew clean build
```

Windows에서는 `./gradlew` 대신 `gradlew.bat`을 사용한다.

## 프로젝트 구조

- **언어**: Java
- **빌드 도구**: Gradle (Kotlin DSL, `build.gradle.kts`)
- **테스트 프레임워크**: JUnit Jupiter (JUnit 5, `junit-bom:6.0.0`)
- **소스 루트**: `src/main/java/`
- **테스트 루트**: `src/test/java/`
- **그룹 ID**: `org.example`

## 테스트 작성 규칙

JUnit 5 (`@Test`, `@BeforeEach`, `@AfterEach` 등) 어노테이션을 사용한다. JUnit 4 스타일(`org.junit.Test`)은 이 프로젝트에서 지원되지 않는다.
