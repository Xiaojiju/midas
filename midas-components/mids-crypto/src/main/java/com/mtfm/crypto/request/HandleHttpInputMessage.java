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

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
/**
  * 处理HttpInputMessage的消息流
  * @author 一块小饼干
  * @since 1.0.0
  */
public class HandleHttpInputMessage implements HttpInputMessage {

    private HttpInputMessage inputMessage;
    private Charset charset;
    private RequestBodyHandler requestBodyHandler;

    public HandleHttpInputMessage(HttpInputMessage inputMessage, Charset charset, RequestBodyHandler requestBodyHandler) {
        this.inputMessage = inputMessage;
        this.charset = charset;
        this.requestBodyHandler = requestBodyHandler;
    }

    @Override
    public InputStream getBody() throws IOException {
        InputStream inputStream = inputMessage.getBody();
        return requestBodyHandler.handle(inputStream, charset);
    }

    @Override
    public HttpHeaders getHeaders() {
        return inputMessage.getHeaders();
    }

    public HttpInputMessage getInputMessage() {
        return inputMessage;
    }

    public void setInputMessage(HttpInputMessage inputMessage) {
        this.inputMessage = inputMessage;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public RequestBodyHandler getRequestBodyHandler() {
        return requestBodyHandler;
    }

    public void setRequestBodyHandler(RequestBodyHandler requestBodyHandler) {
        this.requestBodyHandler = requestBodyHandler;
    }
}
