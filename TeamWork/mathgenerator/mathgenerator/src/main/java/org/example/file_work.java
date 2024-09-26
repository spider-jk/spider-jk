package org.example;
import java.io.*;
import java.util.List;

public class file_work {
    public static void WriteFile(String path, String str) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            if (!file.createNewFile()) {
                System.out.println("创建失败");
                System.exit(1);
            }
        }
        try (FileWriter fw = new FileWriter(file, true)) {
            String str2=str.replaceAll(" ", "");//去所有空格
            fw.write(str2 + "\n"); // 逐行写入文件
        } catch (IOException e) {
            throw new IOException("文件写入出现了错误");
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
                    line = line.substring(index + 1, line.length() - 1);//意思是读取“.”到“=”之间的表达式
                else
                    line = line.substring(index + 1);//一般用不到
                list.add(line.trim()); // 去首尾空格
            }
        } catch (Exception e) {
            throw new RuntimeException("文件读取操作出现问题");
        }
    }
}
