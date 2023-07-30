package com.mtfm.security.provisioning;

import com.mtfm.security.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class NullUserFromJdbcImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(NullUserFromJdbcImpl.class);

    public NullUserFromJdbcImpl() {

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (logger.isDebugEnabled()) {
            logger.debug("you should implement service code by yourself");
        }
        return new AppUser();
    }
}
