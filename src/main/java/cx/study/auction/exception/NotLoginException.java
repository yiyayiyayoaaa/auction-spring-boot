package cx.study.auction.exception;

/**
 *
 * Created by chengxiao on 2017/5/9.
 */
public class NotLoginException extends RuntimeException{

    public NotLoginException(String message){
        super(message);
    }
}
