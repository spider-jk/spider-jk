package org.example;

import org.junit.jupiter.api.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    private final String exercisesFilePath = "Exercises.txt";
    private final String answersFilePath = "Answers.txt";

    @BeforeEach
    public void setUp() throws IOException {
        file_work.ClearFile(exercisesFilePath);
        file_work.ClearFile(answersFilePath);
    }

    @Test
    public void testGenerateExercises() throws IOException {
        Main.generate(5, 10);
        List<String> exercises = new ArrayList<>();
        List<String> answers = new ArrayList<>();
        file_work.ReadFile(exercisesFilePath, exercises);
        file_work.ReadFile(answersFilePath, answers);

        assertEquals(5, exercises.size());
        assertEquals(5, answers.size());
    }

    @Test
    public void testInvalidArgumentCount() {
        // 测试参数个数不合法的情况
        String[] args = {"-n", "5", "-r", "10", "extraArg"}; // 这里的参数个数为 5
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            Main.main(args); // 调用 main 方法来进行参数检查
        });
        assertEquals("输入的参数个数有误", exception.getMessage());
    }


    @Test
    public void testJudgeMismatch() throws IOException {
        file_work.WriteFile(exercisesFilePath, "2 + 3 =");
        file_work.WriteFile(exercisesFilePath, "4 + 7 =");
        file_work.WriteFile(answersFilePath, "6");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            Main.judge(exercisesFilePath, answersFilePath);
        });
        assertEquals("题目数量与答案数量不一致", exception.getMessage());
    }

    @Test
    public void testValidJudgment() throws IOException {
        file_work.WriteFile(exercisesFilePath, "2 + 3 =");
        file_work.WriteFile(answersFilePath, "5");

        Main.judge(exercisesFilePath, answersFilePath); // 不会抛出异常
    }
}
