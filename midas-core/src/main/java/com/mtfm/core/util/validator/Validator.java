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

/**
 * <p>定义值验证方法，可以进行实现此定义，对要求不同或者不同场景的值进行验证</p>
 * <br/><p>例如：</p>
 * <pre>
 *     public class IDValidator implements Validator<String> {
 *
 *         public boolean validate(String source) {
 *             return source != null && source.length();
 *         }
 *     }
 * </pre>
 * @param <T> 需要验证的属性类型
 * @author 一块小饼干
 * @since 1.0.0
 */
public interface Validator {

    /**
     * 验证属性值
     * @param source 属性值
     * @return true 成功 false 失败
     * @since 1.0.0
     */
    boolean validate(Object source);

}
