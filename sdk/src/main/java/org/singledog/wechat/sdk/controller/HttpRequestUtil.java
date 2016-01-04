package org.singledog.wechat.sdk.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by adam on 16-1-4.
 */
public class HttpRequestUtil {

    public static final String readString(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        try {
            StringBuilder sb = new StringBuilder();
            String tmp = null;
            while ((tmp = br.readLine()) != null) {
                sb.append(tmp).append("\r\n");
            }
            return sb.toString();
        } finally {
            if (br != null)
                br.close();
            if (is != null)
                is.close();
        }
    }

}
