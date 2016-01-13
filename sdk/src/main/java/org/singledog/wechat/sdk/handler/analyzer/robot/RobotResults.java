package org.singledog.wechat.sdk.handler.analyzer.robot;

/**
 * Created by adam on 16-1-4.
 */
public enum RobotResults {

    text(100000, RobotTextResult.class),
    link(200000, RobotLinkResult.class),
    news(302000, RobotNewsResult.class),
    dish(308000, RobotDishsResult.class);

    private Class<?> clazz;
    private int code;

    private RobotResults(int code, Class<?> clazz) {
        this.code = code;
        this.clazz = clazz;
    }

    public static RobotResults valueOf(int code) {
        RobotResults[] resultses = RobotResults.values();
        for (RobotResults results : resultses) {
            if (code == results.code())
                return results;
        }

        throw new IllegalArgumentException("No Constant match value : " + code);
    }

    public int code() {
        return code;
    }

    public Class<?> resultClass() {
        return this.clazz;
    }

}
