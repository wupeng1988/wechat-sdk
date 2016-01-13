package org.singledog.wechat.sdk.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author wupeng
 * @version 1.0
 * @date 2016-01-13
 * @modify
 * @copyright Navi Tsp
 */
@Component
@Profile("enable-timer")
public class TimerCheckFilter extends OpenTspFilterAdaptor {

    private static final Logger logger = LoggerFactory.getLogger(TimerCheckFilter.class);
    //TODO move to configuration
    private final long timeout = 4000;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long start = System.currentTimeMillis();
        chain.doFilter(request, response);
        long between = System.currentTimeMillis() - start;

        if (between < timeout) {
            logger.debug("request cost time : {}", between);
        } else {
            logger.warn("request cost time : {}", between);
        }

    }
}
