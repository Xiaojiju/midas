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

import com.mtfm.core.util.validator.ValidateGroup;

import javax.validation.constraints.NotNull;

/**
 * 单个单数请求封装
 * @author 一块小饼干
 * @param <T> 对应参数类型
 * @since 1.0.0
 * public RestResult<String> test(Target<String> test) {
 *     return RestResult.success(test.getTarget());
 * }
 */
public class Target<T> {

    @NotNull(
            groups = {
                    ValidateGroup.Create.class,
                    ValidateGroup.Update.class,
                    ValidateGroup.Delete.class,
                    ValidateGroup.Select.class},
            message = "指定target不能为空")
    private T target;

    public T getTarget() {
        return target;
    }

    public void setTarget(T target) {
        this.target = target;
    }
}
