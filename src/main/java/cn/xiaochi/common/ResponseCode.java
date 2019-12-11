package cn.xiaochi.common;

/**
 * 状态
 */
public enum ResponseCode {

    SUCCESS(1,"SUCCESS"),
    ERROR(0,"ERROR");

    private int code;
    private String message;

    ResponseCode(int code,String message){
        this.code = code;
        this.message = message;
    }

    public int getCode(){
        return code;
    }

    public String getMessage(){
        return message;
    }
}
