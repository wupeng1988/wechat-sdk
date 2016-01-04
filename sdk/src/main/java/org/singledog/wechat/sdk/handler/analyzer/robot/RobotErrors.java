package org.singledog.wechat.sdk.handler.analyzer.robot;

import java.awt.*;

/**
 * Created by adam on 16-1-4.
 */
public enum RobotErrors {

    key_error(40001, "key错误或未激活"),
    content_error(40002, "请求内容info为空"),
    count_down(40004, "当天请求次数已使用完"),
    function_error(40005, "暂不支持所请求的功能"),
    upgrade_error(40006, "图灵机器人服务器正在升级"),
    format_error(40007, "数据格式异常")
    ;

    private int code;
    private String message;

    private RobotErrors(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

    public static RobotErrors valueOf(int code) {
        RobotErrors[] errors = RobotErrors.values();
        for (RobotErrors error : errors) {
            if (code == error.code()) {
                return error;
            }
        }

        throw  new IllegalArgumentException("No Constant match value : " + code);
    }

}
