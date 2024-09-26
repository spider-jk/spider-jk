package org.example;
import org.junit.jupiter.api.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileWorkTest {
    private static final String TEST_FILE_PATH = "testFile.txt";

    @AfterEach
    public void tearDown() {
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) {
            file.delete(); // 删除测试文件
        }
    }

    @Test
    public void testWriteFile() throws IOException {
        String content = "Hello World";
        file_work.WriteFile(TEST_FILE_PATH, content);

        List<String> lines = new ArrayList<>();
        file_work.ReadFile(TEST_FILE_PATH, lines);

        assertEquals(1, lines.size());
        assertEquals("HelloWorld", lines.get(0)); // 验证写入内容去掉空格
    }


    @Test
    public void testClearFile() throws IOException {
        file_work.WriteFile(TEST_FILE_PATH, "Test Content");
        file_work.ClearFile(TEST_FILE_PATH);

        List<String> lines = new ArrayList<>();
        file_work.ReadFile(TEST_FILE_PATH, lines);

        assertEquals(0, lines.size()); // 验证文件已清空
    }

    @Test
    public void testReadFileNonExistent() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            file_work.ReadFile("nonExistentFile.txt", new ArrayList<>());
        });
        assertEquals("文件不存在", exception.getMessage()); // 验证异常信息
    }
}
