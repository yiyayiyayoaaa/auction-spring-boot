package cx.study.auction.constants;

/**
 *
 * Created by chengxiao on 2017/4/30.
 */
public class Constants {

    public interface UserLevel{
        int ORDINARY = 0; //普通
        int SUPPER = 1;   //超级
    }

    public interface UserStatus{
        int USER_ING = 0;  //使用中
        int DISABLE = 1;   //停用
    }

    public interface AuctionStatus{
        int REGISTER = 0;   //默认 已登记
        int WAIT_AUCTION = 1;  //待拍卖
        int AUCTION = 2;        //拍卖中
        int SUCCESS = 3;        //成交
        int UNSOLD = 4;         //流拍
        int OTHER = 5;          //其他
        int OFF = 6;             //下架
        int CANCEL = 7;         //取消
    }
}
