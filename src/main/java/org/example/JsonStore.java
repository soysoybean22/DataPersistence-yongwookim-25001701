package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * JSON 파일을 읽고 쓰는 저수준 I/O 레이어.
 * 파일이 없으면 빈 스토어로 시작하고, 저장 시 파일을 생성한다.
 */
public class JsonStore {

    private final File file;
    private final ObjectMapper mapper;

    public JsonStore(String filePath) {
        this.file = new File(filePath);
        this.mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    }

    public Map<String, Object> load() {
        if (!file.exists()) {
            return new HashMap<>();
        }
        try {
            return mapper.readValue(file, new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            throw new DataPersistenceException("JSON 파일 로드 실패: " + file.getPath(), e);
        }
    }

    public void save(Map<String, Object> data) {
        try {
            file.getParentFile().mkdirs();
            mapper.writeValue(file, data);
        } catch (IOException e) {
            throw new DataPersistenceException("JSON 파일 저장 실패: " + file.getPath(), e);
        }
    }

    public String getFilePath() {
        return file.getAbsolutePath();
    }
}
