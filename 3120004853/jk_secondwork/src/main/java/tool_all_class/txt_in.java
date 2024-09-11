package tool_all_class;

import java.io.*;

public class txt_in {
    //读入txt文件，也包含处理路径
    public static String readTxt(String txtpath){
        String str="";
        String line_txt;
        //文件按行读入到str中
        File file=new File(txtpath);
        FileInputStream file_input=null;
        try {
            file_input=new FileInputStream(file);
            InputStreamReader inputStreamReader=new InputStreamReader(file_input,"UTF-8");
            BufferedReader reader=new BufferedReader(inputStreamReader);
            //字符串拼接
            while((line_txt=reader.readLine())!=null){
                str+=line_txt;
            }
            //结束关闭资源
            inputStreamReader.close();
            reader.close();
            file_input.close();
        } catch (IOException e) {
            System.out.println("读取文件路径出错");
        }
        return str;
    }
    public static  void writeTxt(double txt_elem,String txt_path){
        String str=Double.toString(txt_elem);
        File file=new File(txt_path);
        FileWriter filewriter=null;
        try {
            filewriter=new FileWriter(file,true);
            filewriter.write(str,0,(str.length()>3?4:str.length()));
            filewriter.write("\r\n");
            filewriter.close();
        } catch (IOException e) {
            System.out.println("文件路径有误！未找到指定文件写入");
        }

    }
}

