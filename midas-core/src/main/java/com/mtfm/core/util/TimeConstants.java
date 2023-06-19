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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 公共常量
 * @author 一块小饼干
 * @since 1.0.0
 */
public final class TimeConstants {

    public static final String Y_M_D_H_M_S = "yyyy-MM-dd HH:mm:ss";
    public static final String Y_M_D = "yyyy-MM-dd";
    public static final String H_M_S = "HH:mm:ss";

    public static final DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern(Y_M_D_H_M_S);
    public static final DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern(Y_M_D);
    public static final DateTimeFormatter localTimeFormatter = DateTimeFormatter.ofPattern(H_M_S);

    public static LocalDateTime parseLocalDateTime(String localDateTime) {
        return LocalDateTime.parse(localDateTime, TimeConstants.localDateTimeFormatter);
    }

    public static String parseLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.format(localDateTimeFormatter);
    }

    public static LocalDate parseLocalDate(String localDate) {
        return LocalDate.parse(localDate, TimeConstants.localDateFormatter);
    }

    public static String parseLocalDate(LocalDate localDate) {
        return localDate.format(localDateFormatter);
    }

    public static LocalTime parseLocalTime(String localTime) {
        return LocalTime.parse(localTime, TimeConstants.localTimeFormatter);
    }

    public static String parseLocalTime(LocalTime localTime) {
        return localTime.format(localTimeFormatter);
    }
}
