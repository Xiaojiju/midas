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
package com.mtfm.promotion.enums;

import com.mtfm.tools.enums.BaseEnum;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 活动
 */
public enum Activity implements BaseEnum {

    NEWCOMER_ACTIVITY(0, "newcomer activity"),

    FLASH_SALE(1, "flash sale");

    private final int code;

    private final String desc;

    Activity(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
