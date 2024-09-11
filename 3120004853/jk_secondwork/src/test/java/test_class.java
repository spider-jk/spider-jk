import org.junit.Test;
import tool_all_class.hanming_bijiao;
import tool_all_class.simhash_tranlate;
import tool_all_class.txt_in;

public class test_class {
    @Test
    public void origAndAllTest(){
        String[] str = new String[6];
        str[0] = txt_in.readTxt("E:\\jk_java\\jk_secondwork_file\\orig.txt");
        str[1] = txt_in.readTxt("E:\\jk_java\\jk_secondwork_file\\orig_0.8_add.txt");
        str[2] = txt_in.readTxt("E:\\jk_java\\jk_secondwork_file\\orig_0.8_del.txt");
        str[3] = txt_in.readTxt("E:\\jk_java\\jk_secondwork_file\\orig_0.8_dis_1.txt");
        str[4] = txt_in.readTxt("E:\\jk_java\\jk_secondwork_file\\orig_0.8_dis_10.txt");
        str[5] = txt_in.readTxt("E:\\jk_java\\jk_secondwork_file\\orig_0.8_dis_15.txt");
        String ansFileName = "E:\\jk_java\\jk_secondwork_file\\test.txt";
        for(int i = 0; i <= 5; i++){
            double similar_number = hanming_bijiao.similar_number(simhash_tranlate.getsimhash_number(str[0]), simhash_tranlate.getsimhash_number(str[i]));
            System.out.println(similar_number);
            txt_in.writeTxt(similar_number, ansFileName);
        }
    }
}
