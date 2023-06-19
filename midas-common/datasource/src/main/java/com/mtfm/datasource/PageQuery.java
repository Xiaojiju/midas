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
package com.mtfm.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * @param <T> 分页转换器，将core中定义的分页对象，转换为MybatisPlus的{@link com.baomidou.mybatisplus.extension.plugins.pagination.Page}对象
 */
public class PageQuery<T> extends com.mtfm.core.util.page.PageQuery implements PageHelper<T> {

    @Override
    public Page<T> toPage() {
        Page<T> page = new Page<>();
        page.setSize(super.getSize())
                .setCurrent(super.getCurrent());
        return page;
    }

}
