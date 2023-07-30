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
package com.mtfm.wechat_mp.authentication;

import com.mtfm.weixin.mp.PhoneResult;
import com.mtfm.weixin.mp.service.PhoneInfoService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.provisioning.UserDetailsManager;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 小程序认证，需要自定义实现{@link UserDetailsManager}
 */
public class MiniProgramPhoneAuthenticationProvider extends MiniProgramAuthenticationProvider {

    private final PhoneInfoService phoneInfoService;

    public MiniProgramPhoneAuthenticationProvider(UserDetailsManager userDetailsManager,
                                                  PhoneInfoService phoneInfoService) {
        super(userDetailsManager);
        this.phoneInfoService = phoneInfoService;
    }

    public MiniProgramPhoneAuthenticationProvider(UserDetailsChecker postCheck,
                                                  UserDetailsManager userDetailsManager,
                                                  PhoneInfoService phoneInfoService) {
        super(postCheck, userDetailsManager);
        this.phoneInfoService = phoneInfoService;
    }

    protected String tryUser(Authentication authentication) throws IllegalAccessException {
        PhoneResult phoneResult = phoneInfoService.getPhone(determineJsCode(authentication));
        return phoneResult.getPhone_info().getPhoneNumber();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MiniProgramAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private String determineJsCode(Authentication authentication) {
        return (authentication.getCredentials() == null) ? NONE_CODE : authentication.getCredentials().toString();
    }
}
