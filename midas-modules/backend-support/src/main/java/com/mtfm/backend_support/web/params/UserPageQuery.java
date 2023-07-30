package com.mtfm.backend_support.web.params;

import com.mtfm.core.util.page.PageQuery;

public class UserPageQuery extends PageQuery {

    private String username;

    private String nickname;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
