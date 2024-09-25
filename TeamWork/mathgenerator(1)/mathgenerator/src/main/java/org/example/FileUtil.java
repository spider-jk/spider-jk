package org.example;
import java.io.*;
import java.util.List;

public class FileUtil {
    public static void WriteFile(String path, String str) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            if (!file.createNewFile()) {
                System.out.println("创建失败");
                System.exit(1);
            }
        }
        try (FileWriter fw = new FileWriter(file, true)) {
            fw.write(str + "\n"); // 逐行写入文件
        } catch (IOException e) {
            throw new IOException("写入文件失败");
        }
    }
    public static void ClearFile(String path) throws IOException {
        FileWriter fw = new FileWriter(path, false); // false 参数表示清空文件
        fw.close();
    }

    public static void ReadFile(String path, List<String> list) {
        File file = new File(path);
        if (!file.exists()) {
            throw new RuntimeException("文件不存在");
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) { // 逐行读取文件
                int index = line.indexOf(".");
                if (line.contains("=")) // 判断是否为等式
                    line = line.substring(index + 1, line.length() - 1);
                else
                    line = line.substring(index + 1);
                list.add(line.trim()); // 去除空格，加入到列表中
            }
        } catch (Exception e) {
            throw new RuntimeException("读取文件失败");
        }
    }
}
