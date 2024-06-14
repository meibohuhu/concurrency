package jeecgboot;

import java.text.ParseException;
import java.text.SimpleDateFormat;


public class test {
    public static void main(String[] args) throws ParseException {
        System.out.println(getTime().parse("2022-10-20" + " 00:00:00"));

    }

    private static final ThreadLocal<SimpleDateFormat> LOCAL = new ThreadLocal<SimpleDateFormat>();
    private static SimpleDateFormat getTime(){
        SimpleDateFormat time = LOCAL.get();

        if(time == null){
            time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            LOCAL.set(time);
        }
        return time;
    }
}
