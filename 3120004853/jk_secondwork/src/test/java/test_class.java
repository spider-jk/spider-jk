import org.example.paper_check;
import org.junit.Test;
import tool_all_class.hanming_bijiao;
import tool_all_class.simhash_tranlate;
import tool_all_class.txt_in;

public class test_class {
    @Test
    public void origAndAllTest(){
        String[] str = new String[6];
        str[0] = "E:\\jk_java\\jk_secondwork_file\\orig.txt";
        str[1] = "E:\\jk_java\\jk_secondwork_file\\orig_0.8_add.txt";
        str[2] = "E:\\jk_java\\jk_secondwork_file\\orig_0.8_del.txt";
        str[3] = "E:\\jk_java\\jk_secondwork_file\\orig_0.8_dis_1.txt";
        str[4] = "E:\\jk_java\\jk_secondwork_file\\orig_0.8_dis_10.txt";
        str[5] = "E:\\jk_java\\jk_secondwork_file\\orig_0.8_dis_15.txt";
        String ansFileName = "E:\\jk_java\\jk_secondwork_file\\test.txt";
        String[] GOOD=new String[3];
        GOOD[0]=str[0];
        GOOD[2]=ansFileName;
        for(int i = 0; i <= 5; i++){
            GOOD[1]=str[i];
            paper_check.main(GOOD);
        }
    }
}
