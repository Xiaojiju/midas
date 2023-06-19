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
package com.mtfm.core.context.exceptions;

import com.mtfm.core.CodeExpression;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Objects;

/**
 * @author 一块小饼干
 * 异常断言
 */
public abstract class ServiceAssert {

    public static void isTrue(boolean express, CodeExpression codeExpression) {
        if (express) {
            throwWith(codeExpression);
        }
    }

    public static void isNull(Object object, CodeExpression codeExpression) {
        if (Objects.isNull(object)) {
            throwWith(codeExpression);
        }
    }

    public static void isEmpty(Collection<?> collection, CodeExpression codeExpression) {
        if (CollectionUtils.isEmpty(collection)) {
            throwWith(codeExpression);
        }
    }

    public static void overSize(Collection<?> collection, int size, CodeExpression codeExpression) {
        if (!CollectionUtils.isEmpty(collection) && collection.size() > size) {
            throwWith(codeExpression);
        }
    }

    private static void throwWith(CodeExpression codeExpression) {
        if (codeExpression == null) {
            throw new IllegalArgumentException("code expression cannot be null");
        }
        if (codeExpression.getCode() <= 0) {
            throw new IllegalArgumentException("code expression argument 'code' cannot be expressed by 0");
        }
        if (!StringUtils.hasText(codeExpression.getMessage())) {
            throw new IllegalArgumentException("code expression argument 'message' cannot be expressed by null");
        }
        throw new ServiceException(codeExpression);
    }
}
