package csdn.util;

import java.util.UUID;

public class IDUtil {
    public static String getId(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public static String getCId(){
        String cId =  UUID.randomUUID().toString().replaceAll("-","");
        return cId.substring(0,11);
    }
}
