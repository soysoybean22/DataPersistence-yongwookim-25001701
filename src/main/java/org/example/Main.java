package org.example;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
        DataPersistenceManager manager = new DataPersistenceManager("data/store.json");
        System.out.println("저장 경로: " + manager.getStorePath());

        // CREATE
        System.out.println("\n--- CREATE ---");
        manager.create("username", "yongwoo");
        manager.create("score", 42);
        manager.create("active", true);
        System.out.println("3개 항목 저장 완료");

        // READ
        System.out.println("\n--- READ ---");
        manager.read("username").ifPresent(v -> System.out.println("username: " + v));
        manager.read("score").ifPresent(v -> System.out.println("score: " + v));
        manager.read("notExist").ifPresentOrElse(
                v -> System.out.println("notExist: " + v),
                () -> System.out.println("notExist: 없음")
        );

        // READ ALL
        System.out.println("\n--- READ ALL ---");
        Map<String, Object> all = manager.readAll();
        all.forEach((k, v) -> System.out.println("  " + k + " = " + v));

        // UPDATE
        System.out.println("\n--- UPDATE ---");
        manager.update("score", 100);
        manager.read("score").ifPresent(v -> System.out.println("score 업데이트 후: " + v));

        // DELETE
        System.out.println("\n--- DELETE ---");
        manager.delete("active");
        System.out.println("active 삭제 후 존재 여부: " + manager.exists("active"));

        // 재실행해도 데이터가 유지되는지 확인
        System.out.println("\n--- 최종 상태 (재실행 후에도 유지됨) ---");
        manager.readAll().forEach((k, v) -> System.out.println("  " + k + " = " + v));
    }
}
