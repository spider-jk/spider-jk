package org.example;


import tool_all_class.txt_in;
import tool_all_class.simhash_tranlate;
import tool_all_class.hanming_bijiao;

public class paper_check {
    public static void main(String[] args) {
        String first_paper= txt_in.readTxt(args[0]);
        String second_paper=txt_in.readTxt(args[1]);
        String result=args[2];
        String simhash1=simhash_tranlate.getsimhash_number(first_paper);
        String simhash2=simhash_tranlate.getsimhash_number(second_paper);
        System.out.println(simhash1);
        System.out.println(simhash2);
        double similar_number=hanming_bijiao.similar_number(simhash1,simhash2);
        txt_in.writeTxt(similar_number,result);
        System.out.println(similar_number);
    }
}