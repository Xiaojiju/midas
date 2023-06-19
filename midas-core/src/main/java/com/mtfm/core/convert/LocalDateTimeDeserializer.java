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
package com.mtfm.core.convert;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.mtfm.core.util.TimeConstants;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Jackson反序列转换LocalDateTime
 * @author 一块小饼干
 * @since 1.0.0
 */
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(TimeConstants.Y_M_D_H_M_S);
            return LocalDateTime.parse(jsonParser.getText(), dateTimeFormatter);
        } catch (Exception e) {
            throw new RuntimeException("property: " + jsonParser.getCurrentName() + "," + jsonParser.getText() + "required " + TimeConstants.Y_M_D_H_M_S);
        }
    }
}
