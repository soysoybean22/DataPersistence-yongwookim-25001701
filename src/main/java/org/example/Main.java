package org.example;

import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final String STORE_PATH = "data/store.json";
    private static final DataPersistenceManager manager = new DataPersistenceManager(STORE_PATH);
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== 데이터 영속성 매니저 ===");
        System.out.println("저장 경로: " + manager.getStorePath());

        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> handleCreate();
                case "2" -> handleRead();
                case "3" -> handleReadAll();
                case "4" -> handleUpdate();
                case "5" -> handleDelete();
                case "6" -> {
                    System.out.println("종료합니다.");
                    return;
                }
                default -> System.out.println("[오류] 1~6 중에서 선택하세요.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n1. Create  2. Read  3. Read All  4. Update  5. Delete  6. Exit");
        System.out.print("> ");
    }

    private static void handleCreate() {
        System.out.print("키: ");
        String key = scanner.nextLine().trim();
        System.out.print("값: ");
        String value = scanner.nextLine().trim();
        try {
            manager.create(key, value);
            System.out.println("[완료] '" + key + "' 저장됨");
        } catch (DataPersistenceException e) {
            System.out.println("[오류] " + e.getMessage());
        }
    }

    private static void handleRead() {
        System.out.print("키: ");
        String key = scanner.nextLine().trim();
        manager.read(key).ifPresentOrElse(
                value -> System.out.println("[결과] " + key + " = " + value),
                () -> System.out.println("[결과] '" + key + "' 키가 존재하지 않습니다.")
        );
    }

    private static void handleReadAll() {
        Map<String, Object> all = manager.readAll();
        if (all.isEmpty()) {
            System.out.println("[결과] 저장된 데이터가 없습니다.");
        } else {
            System.out.println("[결과] 전체 " + all.size() + "건");
            all.forEach((k, v) -> System.out.println("  " + k + " = " + v));
        }
    }

    private static void handleUpdate() {
        System.out.print("키: ");
        String key = scanner.nextLine().trim();
        System.out.print("새 값: ");
        String value = scanner.nextLine().trim();
        try {
            manager.update(key, value);
            System.out.println("[완료] '" + key + "' 업데이트됨");
        } catch (DataPersistenceException e) {
            System.out.println("[오류] " + e.getMessage());
        }
    }

    private static void handleDelete() {
        System.out.print("키: ");
        String key = scanner.nextLine().trim();
        try {
            manager.delete(key);
            System.out.println("[완료] '" + key + "' 삭제됨");
        } catch (DataPersistenceException e) {
            System.out.println("[오류] " + e.getMessage());
        }
    }
}
