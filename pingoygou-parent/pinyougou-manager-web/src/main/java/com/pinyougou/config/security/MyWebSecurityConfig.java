package com.pinyougou.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private MyPasswordEncoder passwordEncoder;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        //允许静态资源访问
        http.authorizeRequests().antMatchers("/css/**","/img/**","/js/**","/plugins/**").permitAll()
                .and()
                .formLogin().loginPage("/login/login.do").loginProcessingUrl("/login").permitAll()    //设置登陆页面
                .and().authorizeRequests().anyRequest().authenticated();    //其他请求都需要进行认证

    }

    @Autowired
    public void configGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password("123456").roles("admin").and().passwordEncoder(passwordEncoder);
    }
}
