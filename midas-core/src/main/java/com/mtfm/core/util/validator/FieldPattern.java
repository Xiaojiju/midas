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
package com.mtfm.core.util.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 验证
 * @author 一块小饼干
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = FieldValueValidator.class)
public @interface FieldPattern {

    /**
     * 验证对象是否必须，默认是必须的
     * @return true 必须 false 不必须
     */
    boolean required() default true;

    /**
     * 错误提示
     * @return 错误提示
     */
    String message();

    /**
     * 验证组 {@link com.dire.util.validator.ValidateGroup}
     * @return 指定验证组
     */
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String regex() default "";

    Class<? extends Validator>[] importClass() default {};

}
