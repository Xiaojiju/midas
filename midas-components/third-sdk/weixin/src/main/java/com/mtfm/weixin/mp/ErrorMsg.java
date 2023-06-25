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
package com.mtfm.weixin.mp;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 错误响应
 */
public class ErrorMsg implements Serializable {

    private String errcode;
    private String errmsg;

    public boolean success() {
        if (StringUtils.hasText(errcode)) {
            return String.valueOf(HttpStatus.OK.value()).equals(this.errcode);
        }
        return true;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
