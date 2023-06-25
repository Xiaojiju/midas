/*
 * Copyright 2022 一块小饼干(莫杨)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mtfm.tools;

import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 主要用于组装http请求uri
 */
public class HttpHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpHelper.class);

    private static final String LOWER_QUESTION_MARK = "?";
    private static final String LOWER_AND_MARK = "&";
    private static final String LOWER_EQUAL_MARK = "=";

    public static String getRequestUrl(String url, Map<String, String> variables) {
        if (!StringUtils.hasText(url)) {
            return "";
        }
        if (variables.isEmpty()) {
            return url;
        }
        StringBuilder builder = new StringBuilder(url);
        builder.append(LOWER_QUESTION_MARK);
        variables.forEach((key, value) -> {
            builder.append(key).append(LOWER_EQUAL_MARK).append(value).append(LOWER_AND_MARK);
        });
        url = builder.toString();
        return url.substring(0, url.length() - 1);
    }

    public static String getRequestUrl(String url, Object o) {
        Map<String, String> variables = JSONUtils.convertValue(o, new TypeReference<Map<String, String>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        return getRequestUrl(url, variables);
    }

    public static Map<String, String> getRequestVariables(String url) {
        return null;
    }

    public static String getBodyString(ServletRequest request) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try (InputStream inputStream = request.getInputStream()) {
            reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        catch (IOException e) {
            LOGGER.warn("getBodyString出现问题！");
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
        return sb.toString();
    }
}
