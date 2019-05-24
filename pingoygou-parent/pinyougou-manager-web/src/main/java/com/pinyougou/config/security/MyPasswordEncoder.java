package com.pinyougou.config.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyPasswordEncoder implements PasswordEncoder {
    //密码加密
    @Override
    public String encode(CharSequence rawPassword) {
        String s = rawPassword.toString();
        return s;
    }
    //密码对比
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        boolean equals = encodedPassword.equals(rawPassword.toString());
        return equals;
    }
}
