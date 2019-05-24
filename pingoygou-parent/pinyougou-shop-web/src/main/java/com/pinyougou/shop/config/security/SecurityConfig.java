package com.pinyougou.shop.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyPasswordEncoder passwordEncoder;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();   //关闭crsf保护
        http.authorizeRequests()
                //允许静态资源访问
                .antMatchers("/css/**","/img/**","/js/**","/plugins/**","/seller/add.do").permitAll()
                //其他资源需要认证
                .anyRequest().authenticated()
                .and()
                //登陆页面允许访问
                .formLogin().loginPage("/shop/shoplogin.do").loginProcessingUrl("/login")
                .defaultSuccessUrl("/admin/index.do").permitAll()
                .and()
                //打开注销功能
                .logout().logoutUrl("/logout").deleteCookies().invalidateHttpSession(true)
                .and()
                //iframe标签的支持
                .headers().frameOptions().sameOrigin();
    }
    @Autowired
    public void configGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        auth.eraseCredentials(false);
    }
}
