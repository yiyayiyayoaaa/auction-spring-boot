package cx.study.auction.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * Created by cheng.xiao on 2017/4/17.
 */
public class OrderNumUtil {

    public static String createOrderNum(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        int i1 = (int) (Math.random() * 10 - 1);
        int i2 = (int) (Math.random() * 10 - 1);
        int i3 = (int) (Math.random() * 10 - 1);
        return dateFormat.format(new Date()) + i1 + i2 + i3;
    }
}
