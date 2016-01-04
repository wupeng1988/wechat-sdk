package org.singledog.wechat.sdk.handler.analyzer.robot;

/**
 *
 * Created by adam on 16-1-4.
 */
public class RobotTextResult {

    public static final String seprator = "\n";
    private int code;
    private String text;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.getText();
    }
}
