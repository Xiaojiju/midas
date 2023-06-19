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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * jackson tools
 * @author 一块小饼干
 * @since 1.0.0
 */
public final class JSONUtils {

    private static final Logger logger = LoggerFactory.getLogger(JSONUtils.class);
    private static ObjectMapper objectMapper;
    private static final String BLANK_EMPTY = "";

    private static final Set<JsonReadFeature> JSON_READ_FEATURES_ENABLED = new HashSet<>();

    static {
        try {
            JSON_READ_FEATURES_ENABLED.add(JsonReadFeature.ALLOW_JAVA_COMMENTS); //允许在JSON中使用Java注释
            JSON_READ_FEATURES_ENABLED.add(JsonReadFeature.ALLOW_UNQUOTED_FIELD_NAMES); //允许 json 存在没用双引号括起来的 field
            JSON_READ_FEATURES_ENABLED.add(JsonReadFeature.ALLOW_SINGLE_QUOTES); //允许 json 存在使用单引号括起来的 field
            JSON_READ_FEATURES_ENABLED.add(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS); //允许 json 存在没用引号括起来的 ascii 控制字符
            JSON_READ_FEATURES_ENABLED.add(JsonReadFeature.ALLOW_LEADING_ZEROS_FOR_NUMBERS); //允许 json number 类型的数存在前导 0 (例: 0001)
            JSON_READ_FEATURES_ENABLED.add(JsonReadFeature.ALLOW_NON_NUMERIC_NUMBERS); //允许 json 存在 NaN, INF, -INF 作为 number 类型
            JSON_READ_FEATURES_ENABLED.add(JsonReadFeature.ALLOW_MISSING_VALUES); //允许 只有Key没有Value的情况
            JSON_READ_FEATURES_ENABLED.add(JsonReadFeature.ALLOW_TRAILING_COMMA); //允许数组json的结尾多逗号
            //初始化
            objectMapper = initMapper();
            objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL,true);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("jackson config error", e);
            }
        }
    }

    public static ObjectMapper initMapper() {
        JsonMapper.Builder builder = JsonMapper.builder().enable(JSON_READ_FEATURES_ENABLED.toArray(new JsonReadFeature[0]));
        return builder.build();
    }

    /**
     * JavaObject 转 Json String
     * @param object 需要转换的java对象
     * @param <T> 对象的泛型类
     * @return 转换后的json字符串
     */
    public static <T> String toJsonString(T object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage());
            }
        }
        return BLANK_EMPTY;
    }

    /**
     * Json String 转 JavaObject
     * @param json 需要被转换的json字符串
     * @param targetClass 转换后的目标java类型
     * @param <T> 指定类型
     * @return 指定对象
     */
    public static <T> T parse(String json, Class<T> targetClass) {
        try {
            return objectMapper.readValue(json, targetClass);
        } catch (JsonProcessingException e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage());
            }
        }
        return null;
    }
}
