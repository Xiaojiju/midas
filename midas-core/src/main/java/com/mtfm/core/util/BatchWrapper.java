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

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 批量列表参数封装
 * @author 一块小饼干
 * @since 1.0.0
 * @param <T> 对应参数类型
 *     public RestResult<List<String>> test(@RequestBody BatchWrapper<String> wrapper) {
 *         return RestResult.success(wrapper.getTargets());
 *     }
 */
public class BatchWrapper<T> {

    @Valid
    @Size(
            min = 1,
            groups = {
                    ValidateGroup.Create.class,
                    ValidateGroup.Update.class,
                    ValidateGroup.Delete.class},
            message = "数组最少1个元素，最大100个元素")
    private List<T> targets;

    public List<T> getTargets() {
        return targets;
    }

    public void setTargets(List<T> targets) {
        this.targets = targets;
    }
}
