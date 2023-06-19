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

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

/**
 * <p>统一验证{@link #required}</p>
 * @param <T> 此处泛型主要对应包含required属性的注解
 * @author 一块小饼干
 * @since 1.0.0
 */
public abstract class AbstractValidator<A extends Annotation> implements ConstraintValidator<A, String> {

    private boolean required = false;

    @Override
    public boolean isValid(String e, ConstraintValidatorContext constraintValidatorContext) {
        if (required && !StringUtils.hasText(e)) {
            return false;
        }
        return !required && !StringUtils.hasText(e);
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}
