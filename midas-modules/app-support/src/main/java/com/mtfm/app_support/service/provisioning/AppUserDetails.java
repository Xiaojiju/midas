package com.mtfm.app_support.service.provisioning;

import com.mtfm.app_support.entity.AppUserBaseInfo;
import com.mtfm.security.AppUser;
import com.mtfm.tools.enums.Judge;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

public class AppUserDetails extends AppUser implements Serializable {

    private AppUserBaseInfo appUserBaseInfo;

    public AppUserDetails() {
        this(null);
    }

    public AppUserDetails(AppUserBaseInfo appUserBaseInfo) {
        this(appUserBaseInfo, new AppUser());
    }

    public AppUserDetails(AppUserBaseInfo appUserBaseInfo, AppUser appUser) {
        this(appUser.getId(), appUser.getUsername(), appUser.getIdentifier(), appUser.getPassword(), appUser.getSecretExpiredTime(),
                appUser.getAccountExpiredTime(), appUser.getValidated(), appUser.getAccountLocked(), appUser.getAdditionalKey(),
                appUser.getUsernameExpiredTime(), appUser.getSecretAccess(), appUser.getThirdPart(), appUser.getLoginAccess(),
                appUser.getAuthorities(), appUserBaseInfo);
    }

    public AppUserDetails(String id, String username, String identifier, String password, LocalDateTime secretExpiredTime,
                          LocalDateTime accountExpiredTime, Judge validated, Judge accountLocked, String additionalKey,
                          LocalDateTime usernameExpiredTime, Judge secretAccess, Judge thirdPart, Judge loginAccess,
                          Collection<? extends GrantedAuthority> authorities, AppUserBaseInfo appUserBaseInfo) {
        super(id, username, identifier, password, secretExpiredTime, accountExpiredTime, validated, accountLocked,
                additionalKey, usernameExpiredTime, secretAccess, thirdPart, loginAccess, authorities);
        this.appUserBaseInfo = appUserBaseInfo;
    }

    public AppUserBaseInfo getAppUserBaseInfo() {
        return appUserBaseInfo;
    }

    public void setAppUserBaseInfo(AppUserBaseInfo appUserBaseInfo) {
        this.appUserBaseInfo = appUserBaseInfo;
    }
}
