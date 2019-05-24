package com.pinyougou.config.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailService implements UserDetailsService {

    /**
     * 在此处可以从数据库中根据用户名字查询用户,以及用户相关的权限,并返回用户信息
     * @param username  输入框输入的用户名
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //在此处可以从数据库中查询用户
        /*User user = userService.getUser(username);
        if (user ==null){
            throw new RuntimeException("user is not exist");
        }*/
        //这此先定义一个用户
        User user = new User("admin", "123456", AuthorityUtils.commaSeparatedStringToAuthorityList("admin,user"));
        return user;
    }
}
