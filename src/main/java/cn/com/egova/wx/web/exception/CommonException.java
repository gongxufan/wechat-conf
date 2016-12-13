package cn.com.egova.wx.web.exception;

/**
 * Created by gongxufan on 2016/9/26.
 */
public class CommonException extends RuntimeException{
    private String errorMsg;
    private int errorCode;

    public CommonException(String errorMsg){
        this.errorMsg = errorMsg;
        this.errorCode = 0;
    }

    @Override
    public String getMessage() {
        return this.errorMsg;
    }

    public CommonException(String errorMsg, int errorCode){
        this.errorMsg  = errorMsg;
        this.errorCode = 0;
    }
}
