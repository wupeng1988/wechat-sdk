package org.singledog.programmer;

import org.singledog.wechat.sdk.SDKScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by adam on 16-1-4.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = {SDKScan.class, Application.class})
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        /**
         * 出现这个吉祥马就是程序启动成功的标识， 勿删 ！！
         */
        logger.error(" application startup success !\n" +
                "                           _(\\_/) \n" +
                "                         ,((((^`\\\n" +
                "                        ((((  (6 \\ \n" +
                "                      ,((((( ,    \\\n" +
                "  ,,,_              ,(((((  /\"._  ,`,\n" +
                " ((((\\\\ ,...       ,((((   /    `-.-'\n" +
                " )))  ;'    `\"'\"'\"\"((((   (      \n" +
                "(((  /            (((      \\\n" +
                " )) |                      |\n" +
                "((  |        .       '     |\n" +
                "))  \\     _ '      `t   ,.')\n" +
                "(   |   y;- -,-\"\"'\"-.\\   \\/  \n" +
                ")   / ./  ) /         `\\  \\\n" +
                "   |./   ( (           / /'\n" +
                "   ||     \\\\          //'|\n" +
                "   ||      \\\\       _//'||\n" +
                "   ||       ))     |_/  ||\n" +
                "   \\_\\     |_/          ||\n" +
                "   `'\"                  \\_\\\n" +
                "                        `'\"");

    }

}
