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

import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>验证属性值</p>
 * {@link FieldValueValidator#validators}验证顺序是按照引入的解析器的顺序执行的
 * 优先验证正则表达式，在正则表示验证成功后，才会继续验证
 * @author 一块小饼干
 * @since 1.0.0
 */
public class FieldValueValidator extends AbstractValidator<FieldPattern> {

    private String regex;
    private Class<? extends Validator>[] validators;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (super.isValid(s, constraintValidatorContext)) {
            return true;
        }
        boolean flag = true;
        if (StringUtils.hasText(regex)) {
            flag = regexMatch(s);
        }
        return flag && validate(s);
    }

    @Override
    public void initialize(FieldPattern constraintAnnotation) {
        regex = constraintAnnotation.regex();
        super.setRequired(constraintAnnotation.required());
        validators = constraintAnnotation.importClass();
    }

    private boolean regexMatch(String source) {
        if (StringUtils.hasText(regex)) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(source);
            return matcher.matches();
        }
        return false;
    }

    private boolean validate(String source) {
        if (validators.length == 0) {
            return true;
        }
        try {
            for (Class<? extends Validator> clazz : validators) {
                Constructor<? extends Validator> constructor = clazz.getDeclaredConstructor();
                constructor.setAccessible(true);
                Validator validator = constructor.newInstance();
                if (!validator.validate(source)) {
                    return false;
                }
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
