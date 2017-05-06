package cx.study.auction.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * Created by chengxiao on 2017/4/23.
 */
public class DefaultNicknameUtil {

    public static String buildNickname(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        int i1 = (int) (Math.random() * 10 - 1);
        return "pai"+dateFormat.format(new Date()) + i1;
    }
}
