package tool_all_class;

import com.hankcs.hanlp.HanLP;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

public class simhash_tranlate {
    //传入string，然后利用哈希库计算哈希值
    static int weishu=256;
    public static String getHash(String str){
        MessageDigest message= null;
        try {
            message = MessageDigest.getInstance("SHA-256");
            return new BigInteger(1,message.digest(str.getBytes("UTF-8"))).toString(2);
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
//        BigInteger bigInteger=new BigInteger(String.valueOf(str.hashCode()));
//        return String.valueOf(bigInteger);
//        String erjinzhi=Integer.toBinaryString(str.hashCode());
//        return erjinzhi;
    }
    public static String getsimhash_number(String str){
        int []v=new int[weishu];//特征向量
        List<String> wordlist= HanLP.extractKeyword(str,str.length());//分词
        int size=wordlist.size();
        int j=0;//做外层循环
        for (String keyword:wordlist){
            String keyhashnumber=getHash(keyword);
            if (keyhashnumber.length()<weishu){
                int buwei=weishu-keyhashnumber.length();
                for (int i=0;i<buwei;i++){
                    keyhashnumber+="0";
                }
            }
            for (int i=0;i<v.length;i++){
                if (keyhashnumber.charAt(i)=='1'){
                    v[i] += (10 - (j / (size / 10)));
                } else {
                    v[i] -= (10 - (j / (size / 10)));
                }
            }
            j++;
        }
        //降维
        String simhash="";
        for (int i = 0; i < v.length; i++) {
            // 从高位遍历到低位
            if (v[i] <= 0) {
                simhash += "0";
            } else {
                simhash += "1";
            }
        }

        return simhash;
    }
}
