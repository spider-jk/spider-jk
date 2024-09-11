package test_danyuan;

import org.junit.Test;
import tool_all_class.simhash_tranlate;
import tool_all_class.txt_in;

public class simhash_test {
    @Test
    public void gethash_test(){
        String[] strs={"今天","天气","晴朗","我们","去","公园","打","乒乓球"};
        for(String str : strs){
            String str_hash= simhash_tranlate.getHash(str);
            System.out.println(str+" 的hash值有"+str_hash.length()+"位");
            System.out.println("值为"+str_hash);
        }
    }
    @Test
    public void getsimhash_test(){
        String str1= txt_in.readTxt("E:\\jk_java\\jk_secondwork_file\\orig.txt");
        String str2=txt_in.readTxt("E:\\jk_java\\jk_secondwork_file\\orig_0.8_add.txt");
        System.out.println("原文的simhash为:"+simhash_tranlate.getsimhash_number(str1));
        System.out.println("增加版的simhash为:"+simhash_tranlate.getsimhash_number(str2));
    }
}
