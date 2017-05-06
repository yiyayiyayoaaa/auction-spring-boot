package cx.study.auction.bean;

import com.google.gson.annotations.Expose;

/**
 *
 * Created by chengxiao on 2017/4/30.
 */
public class HttpResult<T> {
    @Expose
    private Integer code;
    @Expose
    private String msg;
    @Expose
    private T obj;

    public HttpResult(){

    }

    public HttpResult(Integer code, String msg, T obj) {
        this.code = code;
        this.msg = msg;
        this.obj = obj;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}
