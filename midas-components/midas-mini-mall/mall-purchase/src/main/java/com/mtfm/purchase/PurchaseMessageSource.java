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
package com.mtfm.purchase;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 绝大部分注册为Spring bean的类可以直接实现{@link org.springframework.context.MessageSourceAware}进行容器自动
 * 注入{@link org.springframework.context.MessageSource}，本类主要用于那些没有注册为Spring bean的类，可以直接使用
 * {@link PurchaseMessageSource#getAccessor()}进行直接使用spring的国际化；
 */
public class PurchaseMessageSource extends ResourceBundleMessageSource {

    public PurchaseMessageSource() {
        this.addBasenames("i18n/purchase_messages");
    }

    public static MessageSourceAccessor getAccessor() {
        return new MessageSourceAccessor(new PurchaseMessageSource());
    }
}
