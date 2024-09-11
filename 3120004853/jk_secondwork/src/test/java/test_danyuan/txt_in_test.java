package test_danyuan;

import org.junit.Test;
import tool_all_class.txt_in;

public class txt_in_test {
    @Test
    public void readTxtTest() {
        // 路径存在，正常读取
        String str = txt_in.readTxt("E:\\jk_java\\jk_secondwork_file\\orig.txt");
        String[] strings = str.split(" ");
        for (String string : strings) {
            System.out.println(string);
        }
    }

    @Test
    public void writeTxtTest() {
        // 路径存在，正常写入
        double[] elem = {0.11, 0.22, 0.33, 0.44, 0.55};
        for (int i = 0; i < elem.length; i++) {
            txt_in.writeTxt(elem[i], "E:\\jk_java\\jk_secondwork_file\\test.txt");
        }
    }

    @Test
    public void readTxtFailTest() {
        // 路径不存在，读取失败
        String str = txt_in.readTxt("D:/test/none.txt");
    }

    @Test
    public void writeTxtFailTest() {
        // 路径错误，写入失败
        double[] elem = {0.11, 0.22, 0.33, 0.44, 0.55};
        for (int i = 0; i < elem.length; i++) {
            txt_in.writeTxt(elem[i], "User:/test/ans.txt");
        }
    }
}
