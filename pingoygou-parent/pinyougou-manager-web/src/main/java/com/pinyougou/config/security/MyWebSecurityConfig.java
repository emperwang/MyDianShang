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

   /* @Autowired
    private MyPasswordEncoder passwordEncoder;*/
   @Autowired
   private MyAuthenticationProvider authenticationProvider;

   @Autowired
   private MyFailHandler failHandlerl;

   @Autowired
   private MySuccessHandler successHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        //允许静态资源访问
        http.authorizeRequests().antMatchers("/css/**","/img/**","/js/**","/plugins/**").permitAll()
                .and()
                .formLogin().loginPage("/login/login.do").loginProcessingUrl("/login").failureHandler(failHandlerl)
                .successHandler(successHandler).permitAll()    //设置登陆页面
                .and().authorizeRequests().anyRequest().authenticated()    //其他请求都需要进行认证
                .and()
                //启用注销功能,并删除相关的认证信息和无效httpSession以及删除cookie(可以指定cookie的name来指定删除哪些cookie)
                //默认注销地址/logout,也可以重新指定,并且也可以指定注销成功或失败的处理函数
                .logout().clearAuthentication(true).invalidateHttpSession(true).deleteCookies()
                .and()
                .headers().frameOptions().sameOrigin();    //对iframe标签的支持打开
    }

    @Autowired
    public void configGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //定义用户以及密码,并使用指定的密码加密算法
        /*auth.inMemoryAuthentication()
                .withUser("admin").password("123456").roles("admin").and().passwordEncoder(passwordEncoder);*/
        //使用自己定义的用户判断逻辑
        auth.authenticationProvider(authenticationProvider);
    }
}
