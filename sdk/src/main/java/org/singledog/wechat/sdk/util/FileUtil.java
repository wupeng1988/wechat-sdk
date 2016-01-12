package org.singledog.wechat.sdk.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by adam on 1/3/16.
 */
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
    private static final Map<String, String> FILE_CONTENT_CACHE = new ConcurrentHashMap<>();

    public static String replace(String json, Map<String, Object> variables){
        for(Map.Entry<String, Object> entry : variables.entrySet()) {
            String key = entry.getKey();
            String value = (entry.getValue() == null ? "" : String.valueOf(entry.getValue()));
            json = json.replace("${"+key+"}", value);
        }

        return json;
    }

    public static synchronized String readString(String name) throws FileNotFoundException {
        if(FILE_CONTENT_CACHE.containsKey(name))
            return FILE_CONTENT_CACHE.get(name);

        String json = null;

        Resource resource = null;
        int index = name.indexOf(":");
        if (index > 0) {
            String type = name.substring(0, index);
            String path = name.substring(type.length() + 1);
            if ("file".equals(type)) {
                resource = new FileSystemResource(path);
            } else {
                resource = new ClassPathResource(path);
            }
        } else {
            resource = new ClassPathResource(name);
        }

        InputStream is = null;
        BufferedReader br = null;
        try {
            is = resource.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String flag = null;
            while((flag = br.readLine()) != null) {
                sb.append(flag);
            }
            json = sb.toString();

            logger.info("read file : {} ,content : {}" , name, sb.toString());
            FILE_CONTENT_CACHE.put(name, json);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if(is != null)
                    is.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }

            try {
                if(br != null)
                    br.close();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }

        return json;
    }

}
