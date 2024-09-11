package tool_all_class;

public class hanming_bijiao {
    public static int getdistance(String simhash1,String simhash2){
        int distance=0;
        if (simhash1.length()!=simhash2.length()){
            System.out.println("两个文章的simhash长度不对等，有问题");
            return -1;
        }else {
            for (int i=0;i<simhash1.length();i++){
                if (simhash1.charAt(i)!=simhash2.charAt(i)){
                    distance++;
                }
            }
        }
        return distance;
    }
    public static double similar_number(String simhash1,String simhash2){
        int distance=getdistance(simhash1,simhash2);
        return 0.01*(100- (double) (distance * 100) /256);
    }
}
