package org.example;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * JSON 스토어 위에서 CRUD 연산을 제공하는 영속성 매니저.
 *
 * - 모든 쓰기 연산(create/update/delete)은 즉시 파일에 반영된다.
 * - 읽기 연산(read/readAll)은 매번 파일에서 최신 상태를 불러온다.
 */
public class DataPersistenceManager {

    private final JsonStore store;

    public DataPersistenceManager(String filePath) {
        this.store = new JsonStore(filePath);
    }

    /** 새 키-값 쌍을 저장한다. 이미 존재하는 키이면 예외를 던진다. */
    public void create(String key, Object value) {
        Map<String, Object> data = store.load();
        if (data.containsKey(key)) {
            throw new DataPersistenceException("이미 존재하는 키입니다: " + key);
        }
        data.put(key, value);
        store.save(data);
    }

    /** 키에 해당하는 값을 반환한다. 없으면 Optional.empty(). */
    public Optional<Object> read(String key) {
        return Optional.ofNullable(store.load().get(key));
    }

    /** 전체 데이터를 읽기 전용 Map으로 반환한다. */
    public Map<String, Object> readAll() {
        return Collections.unmodifiableMap(store.load());
    }

    /** 기존 키의 값을 수정한다. 존재하지 않는 키이면 예외를 던진다. */
    public void update(String key, Object value) {
        Map<String, Object> data = store.load();
        if (!data.containsKey(key)) {
            throw new DataPersistenceException("존재하지 않는 키입니다: " + key);
        }
        data.put(key, value);
        store.save(data);
    }

    /** 키-값 쌍을 삭제한다. 존재하지 않는 키이면 예외를 던진다. */
    public void delete(String key) {
        Map<String, Object> data = store.load();
        if (!data.containsKey(key)) {
            throw new DataPersistenceException("존재하지 않는 키입니다: " + key);
        }
        data.remove(key);
        store.save(data);
    }

    /** 키 존재 여부를 반환한다. */
    public boolean exists(String key) {
        return store.load().containsKey(key);
    }

    public String getStorePath() {
        return store.getFilePath();
    }
}
