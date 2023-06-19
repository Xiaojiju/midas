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
package com.mtfm.core.context.request;

import org.springframework.http.HttpInputMessage;

import javax.servlet.http.HttpServletRequestWrapper;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
  * request body 处理定义
  * 默认是进行不处理，{@link com.mtfm.core.context.request.DefaultRequestBodyHandler}
  * 主要是以application/json，如果是另外的格式，需要使用{@link HttpServletRequestWrapper}进行包装处理，request只能读取一次body，
  * 需要进行注意处理；或者使用拦截器和AOP切面进行，但都需要进行注意body的读取，不可多次读；
  * @author 一块小饼干
  * @since 1.0.0
  */
public interface RequestBodyHandler {

    /**
     * 字符串处理，解析body的字符串
     * @param body RequestBody
     * @return 处理后的字符串
     */
    String Handle(String body);

    /**
     * 直接处理{@link HttpInputMessage}中的{@link HttpInputMessage#getBody()}
     * 中RequestBody流，读取流可以使用辅助工具类{@link com.mtfm.core.util.tools.IOUtils#read(InputStream)}，默认的编码集为UTF-8;
     * @param inputStream {@link HttpInputMessage#getBody()}
     * @return 处理后的流，可以使用辅助工具类{@link com.mtfm.core.util.tools.IOUtils#write(String)}
     */
    InputStream handle(InputStream inputStream);

    /**
     * 基本操作与{@link #handle(InputStream)}是一样的，只是需要显示参数{@link Charset},推荐使用{@link java.nio.charset.StandardCharsets}类
     * 进行静态的引入编码集；
     * @param inputStream {@link HttpInputMessage#getBody()}
     * @param charset 编码集
     * @return 处理后的流，可以使用辅助工具类{@link com.mtfm.core.util.tools.IOUtils#write(String)}
     */
    InputStream handle(InputStream inputStream, Charset charset);

    // 新增返回Handler Chain
    // 全局进行注册到Chain，如果注解指定，则只执行全局是否执行的handler和注解指定的

}
