package org.example;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DataPersistenceManagerTest {

    @TempDir
    Path tempDir;

    private DataPersistenceManager manager;

    @BeforeEach
    void setUp() {
        manager = new DataPersistenceManager(tempDir.resolve("test-store.json").toString());
    }

    @Test
    void create_저장후_읽기_성공() {
        manager.create("name", "alice");
        assertEquals("alice", manager.read("name").orElseThrow());
    }

    @Test
    void create_중복_키_예외() {
        manager.create("key", "value1");
        assertThrows(DataPersistenceException.class, () -> manager.create("key", "value2"));
    }

    @Test
    void read_없는_키는_empty() {
        assertTrue(manager.read("ghost").isEmpty());
    }

    @Test
    void readAll_전체_데이터_반환() {
        manager.create("a", 1);
        manager.create("b", 2);
        Map<String, Object> all = manager.readAll();
        assertEquals(2, all.size());
        assertEquals(1, all.get("a"));
        assertEquals(2, all.get("b"));
    }

    @Test
    void update_값_변경_성공() {
        manager.create("score", 10);
        manager.update("score", 99);
        assertEquals(99, manager.read("score").orElseThrow());
    }

    @Test
    void update_없는_키_예외() {
        assertThrows(DataPersistenceException.class, () -> manager.update("ghost", "x"));
    }

    @Test
    void delete_키_제거_성공() {
        manager.create("temp", "bye");
        manager.delete("temp");
        assertFalse(manager.exists("temp"));
    }

    @Test
    void delete_없는_키_예외() {
        assertThrows(DataPersistenceException.class, () -> manager.delete("ghost"));
    }

    @Test
    void 영속성_재로드시_데이터_유지() {
        manager.create("persist", "yes");

        // 같은 파일을 가리키는 새 인스턴스로 재로드 시뮬레이션
        DataPersistenceManager reloaded = new DataPersistenceManager(
                tempDir.resolve("test-store.json").toString()
        );
        assertEquals("yes", reloaded.read("persist").orElseThrow());
    }

    @Test
    void 다양한_타입_저장_및_조회() {
        manager.create("integer", 42);
        manager.create("bool", true);
        manager.create("text", "hello");

        assertEquals(42, manager.read("integer").orElseThrow());
        assertEquals(true, manager.read("bool").orElseThrow());
        assertEquals("hello", manager.read("text").orElseThrow());
    }
}
