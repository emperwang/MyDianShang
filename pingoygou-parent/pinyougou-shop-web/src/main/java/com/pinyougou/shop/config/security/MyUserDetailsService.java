package com.pinyougou.shop.config.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbSeller;
import com.pinyougou.service.SellerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Reference
    private SellerService sellerService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //通过主键查找到seller
        TbSeller seller = sellerService.findOne(username);
        //授权用户角色
        List<GrantedAuthority> authorities = new ArrayList<>();
        //用户存在且用户已经审核通过才能登陆
        if (seller != null && seller.getStatus().equals("1")){
            authorities.add(new SimpleGrantedAuthority("ROLE_SELLER"));
            User user = new User(username, seller.getPassword(), authorities);
            return user;
        }
        return null;
    }
}
