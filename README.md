# DataPersistence

JSON 파일을 이용한 데이터 영속성(Data Persistence) 구현 프로젝트입니다.  
애플리케이션을 재시작해도 데이터가 유지되며, Key-Value 구조의 CRUD 연산을 지원합니다.

## 개요

**데이터 영속성**이란 애플리케이션이 종료되거나 재시작되어도 데이터가 사라지지 않고 유지되는 성질입니다.  
이 프로젝트는 JSON 파일을 저장소로 사용하여 해당 개념을 구현합니다.

## 아키텍처

```
DataPersistenceManager   ← CRUD 연산 레이어
        ↓
    JsonStore            ← JSON 파일 I/O 레이어
        ↓
  data/store.json        ← 실제 데이터 저장 파일
```

| 클래스 | 역할 |
|---|---|
| `JsonStore` | JSON 파일을 `Map<String, Object>`로 읽고 쓰는 저수준 I/O |
| `DataPersistenceManager` | `create` / `read` / `readAll` / `update` / `delete` / `exists` |
| `DataPersistenceException` | 중복 키, 없는 키 등 도메인 예외 |
| `Main` | 인터랙티브 CRUD CLI |

## 실행 방법

IDE(IntelliJ 등)에서 `Main.java`를 직접 실행합니다.

```
=== 데이터 영속성 매니저 ===
저장 경로: .../data/store.json

1. Create  2. Read  3. Read All  4. Update  5. Delete  6. Exit
>
```

## 데이터 저장 위치

```
프로젝트루트/data/store.json
```

첫 실행 시 디렉토리와 파일이 자동으로 생성됩니다.

## 빌드 및 테스트

```bash
# 전체 빌드
./gradlew build

# 테스트 실행
./gradlew test

# 단일 테스트 클래스 실행
./gradlew test --tests "org.example.DataPersistenceManagerTest"
```

## 기술 스택

- Java 17
- Gradle 9 (Kotlin DSL)
- Jackson Databind 2.18.3
- JUnit Jupiter 6 (JUnit 5)
