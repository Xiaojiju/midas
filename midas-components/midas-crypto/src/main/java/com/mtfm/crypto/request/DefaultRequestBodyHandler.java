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
package com.mtfm.crypto.request;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
  * 默认RequestBody处理类
  * @author 一块小饼干
  * @since 1.0.0
  */
public class DefaultRequestBodyHandler implements RequestBodyHandler {

    @Override
    public String Handle(String body) {
        return body;
    }

    @Override
    public InputStream handle(InputStream inputStream) {
        return handle(inputStream, StandardCharsets.UTF_8);
    }

    @Override
    public InputStream handle(InputStream inputStream, Charset charset) {
        return inputStream;
    }
}
