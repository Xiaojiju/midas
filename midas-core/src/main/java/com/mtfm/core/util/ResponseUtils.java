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
package com.mtfm.core.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public final class ResponseUtils {

    public static final String DEFAULT_APPLICATION_CONTENT_TYPE = "application/json;charset=utf-8";

    public static void writeObject(HttpServletResponse response, String text) throws IOException {
        response.setContentType(DEFAULT_APPLICATION_CONTENT_TYPE);
        response.getWriter().write(text);
    }
//    public static void writeObject(HttpServletResponse response, Object object, Map<String, String> headers) {
//
//    }
//
//    public static void writeText(HttpServletResponse response, String text) {
//
//    }
//
//    public static void writeText(HttpServletResponse response, String text, HttpHeaders httpHeaders) {
//
//    }
//
//    public static void writeWithBytes(HttpServletResponse response, byte[] bytes) {
//
//    }
//
//    public static void writeWithBytes(HttpServletResponse response, byte[] bytes, HttpHeaders httpHeaders) {
//
//    }
//
//    public static void writeFile(HttpServletResponse response, String filepath) {
//
//    }
//
//    public static void writeFile(HttpServletResponse response, String filepath, HttpHeaders httpHeaders) {
//
//    }
//
//    public static void writeFile(HttpServletResponse response, File file) {
//
//    }
//
//    public static void writeFile(HttpServletResponse response, File file, HttpHeaders httpHeaders) {
//
//    }
//
}
