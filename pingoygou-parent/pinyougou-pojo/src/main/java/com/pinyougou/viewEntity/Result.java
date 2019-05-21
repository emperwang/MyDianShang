package com.pinyougou.viewEntity;

import java.io.Serializable;

/**
 *  结果视图，如添加，修改是否成功
 */
public class Result implements Serializable{
    private boolean success; // 是否成功
    private String message;  //提示信息

    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
