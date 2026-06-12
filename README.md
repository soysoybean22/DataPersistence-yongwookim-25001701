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

### CLI 사용 예시

**Create** — 새 상품 추가
```
> 1
키: product:31
값: {"name":"새 상품","price":15000,"stock":50,"category":"전자기기"}
[완료] 'product:31' 저장됨
```

**Read** — 특정 키 조회
```
> 2
키: product:1
[결과] product:1 = {name=무선 블루투스 이어폰, price=45000, stock=120, category=전자기기}
```

**Read All** — 전체 목록 조회
```
> 3
[결과] 전체 30건
  product:1 = {name=무선 블루투스 이어폰, ...}
  product:2 = {name=USB-C 충전 케이블, ...}
  ...
```

**Update** — 재고 수정
```
> 4
키: product:5
새 값: {"name":"27인치 모니터","price":320000,"stock":5,"category":"전자기기"}
[완료] 'product:5' 업데이트됨
```

**Delete** — 상품 삭제
```
> 5
키: product:30
[완료] 'product:30' 삭제됨
```

## 더미 데이터

`data/store.json`에 상품 재고 데이터 30건이 포함되어 있습니다.  
각 항목은 `product:{번호}` 키 형태이며 다음 필드를 가집니다.

| 필드 | 타입 | 설명 |
|---|---|---|
| `name` | String | 상품명 |
| `price` | Number | 가격 (원) |
| `stock` | Number | 재고 수량 |
| `category` | String | 카테고리 |

포함된 카테고리: 전자기기, 주변기기, 저장장치, 음향기기, 스마트홈, 생활가전, 생활용품, 헬스케어, 사무용품, 가구/인테리어, 가방/케이스

## 데이터 저장 위치

```
프로젝트루트/data/store.json
```

첫 실행 시 디렉토리와 파일이 자동으로 생성됩니다. 데이터 변경 후 `git push`하면 저장 상태도 GitHub에 반영됩니다.

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
