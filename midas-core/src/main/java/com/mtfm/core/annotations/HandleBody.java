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
package com.mtfm.core.annotations;

import com.mtfm.core.context.request.RequestBodyHandler;
import com.mtfm.core.context.response.ResponseBodyHandler;

import java.lang.annotation.*;

/**
 * body处理注解
 * {@link #beforeRead()} 为一个数组，必须继承{@link RequestBodyHandler}，执行顺序为数组的先后顺序
 * {@link #beforeWrite()} 为一个数组，必须继承{@link ResponseBodyHandler}, 执行顺序为数组的先后顺序
 * @author 一块小饼干
 * @since 1.0.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HandleBody {

    Class<? extends RequestBodyHandler>[] beforeRead() default {};

    Class<? extends ResponseBodyHandler>[] beforeWrite() default {};

}
