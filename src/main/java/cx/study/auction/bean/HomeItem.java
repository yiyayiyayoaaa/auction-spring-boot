package cx.study.auction.bean;

import com.google.gson.annotations.Expose;

/**
 * 首页布局
 * Created by cheng.xiao on 2017/3/9.
 */

public class HomeItem<T> {
    public static final int TITLE = 0;
    public static final int CONTENT = 1;
    @Expose
    private int type;
    @Expose
    private T obj;

    public HomeItem() {
    }

    public HomeItem(int type, T obj) {
        this.type = type;
        this.obj = obj;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}
