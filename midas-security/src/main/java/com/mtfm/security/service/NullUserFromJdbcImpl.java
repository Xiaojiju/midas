package com.mtfm.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;

public class NullUserFromJdbcImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(NullUserFromJdbcImpl.class);

    public NullUserFromJdbcImpl() {

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (logger.isDebugEnabled()) {
            logger.debug("you should implement service code by yourself");
        }
        return User.withUsername("Debug").accountExpired(true).accountLocked(true).credentialsExpired(true)
                .authorities(new ArrayList<>()).password("[protected]").build();
    }
}
