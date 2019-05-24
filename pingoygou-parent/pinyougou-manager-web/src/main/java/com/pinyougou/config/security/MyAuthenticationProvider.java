package com.pinyougou.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 认证逻辑实现
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private MyUserDetailService userDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //输入框输入的用户名和密码
        String name = authentication.getName();
        String password = (String) authentication.getCredentials();
        //从数据库中查询用户信息
        UserDetails userDetails = userDetailService.loadUserByUsername(name);
        if (userDetails == null){
            throw new BadCredentialsException("The user is not exist!!");
        }
        String dbPassword = userDetails.getPassword();
        if (!password.equals(dbPassword)){
            throw new BadCredentialsException("The password is not right..");
        }
        //获取用户权限
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, authorities);
        return usernamePasswordAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        //表示此函数支持运行
        return true;
    }
}
