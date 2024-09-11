package test_danyuan;

import org.junit.Test;
import tool_all_class.txt_in;
import tool_all_class.hanming_bijiao;
import tool_all_class.simhash_tranlate;

public class haiming_test {

    @Test
    public void getHammingDistanceTest() {
        String str0 = txt_in.readTxt("E:\\jk_java\\jk_secondwork_file\\orig.txt");
        String str1 = txt_in.readTxt("E:\\jk_java\\jk_secondwork_file\\orig_0.8_add.txt");
        int distance = hanming_bijiao.getdistance(simhash_tranlate.getsimhash_number(str0), simhash_tranlate.getsimhash_number(str1));
        System.out.println("海明距离：" + distance);
        System.out.println("相似度: " + (100 - (double)distance * 100 / 256) + "%");
    }

    @Test
    public void getHammingDistanceFailTest() {
        // 测试str0.length()!=str1.length()的情况
        String str0 = "10101010";
        String str1 = "1010101";
        System.out.println(hanming_bijiao.getdistance(str0, str1));
    }

    @Test
    public void getSimilarityTest() {
        String str0 = txt_in.readTxt("E:\\jk_java\\jk_secondwork_file\\orig.txt");
        String str1 = txt_in.readTxt("E:\\jk_java\\jk_secondwork_file\\orig_0.8_add.txt");
        int distance = hanming_bijiao.getdistance(simhash_tranlate.getsimhash_number(str0), simhash_tranlate.getsimhash_number(str1));
        double similarity = hanming_bijiao.similar_number(simhash_tranlate.getsimhash_number(str0), simhash_tranlate.getsimhash_number(str1));
        System.out.println("str0和str1的汉明距离: " + distance);
        System.out.println("str0和str1的相似度:" + similarity);
    }

}
