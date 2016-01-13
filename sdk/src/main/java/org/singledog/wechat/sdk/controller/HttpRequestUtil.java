package org.singledog.wechat.sdk.controller;

import org.slf4j.Logger;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by adam on 16-1-4.
 */
public class HttpRequestUtil {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(HttpRequestUtil.class);

    public static final String readString(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        try {
            StringBuilder sb = new StringBuilder();
            String tmp = null;
            while ((tmp = br.readLine()) != null) {
                sb.append(tmp);
            }
            return sb.toString();
        } finally {
            if (br != null)
                br.close();
            if (is != null)
                is.close();
        }
    }

    public static final void writeToResponse(HttpServletResponse response, String message, String charset) {
        OutputStream os = null;
        if (StringUtils.isEmpty(charset)) {
            charset = "UTF-8";
        }

        try {
            os = response.getOutputStream();
            os.write(message.getBytes(charset));
            os.flush();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (os != null)
                    os.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }

    }

}
