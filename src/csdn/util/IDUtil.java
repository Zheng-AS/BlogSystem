package csdn.util;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class IDUtil {
    public static String getId(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public static String getCId(){
        String cId =  UUID.randomUUID().toString().replaceAll("-","");
        return cId.substring(0,11);
    }

    public static String getTime(){
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return sdf.format(date);
    }

    @Test
    public void test1(){
        getTime();
    }
}
