package tacoscloud.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService)
    {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
//        return new StandardPasswordEncoder("53cr3t");//已弃用
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        //用户账户
        //存入内存
        auth.inMemoryAuthentication().
                withUser("aa").password(passwordEncoder().encode("1234")).authorities("ROLE_USER")
                .and()
                .withUser("bbb").password(passwordEncoder().encode("1234")).authorities("ROLE_USER");
        //读取外存
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http    //路径权限
                .authorizeRequests()
                .antMatchers("/design", "/orders", "/orders/**").access("hasRole('ROLE_USER')")
                .antMatchers("/").access("permitAll")
                .antMatchers("/**").access("permitAll")
                //登录
                .and()
                .formLogin().loginPage("/login")
                .usernameParameter("username").passwordParameter("password")
                .defaultSuccessUrl("/", true)
                //注销
                .and()
                .logout().logoutSuccessUrl("/")
                //允许同源iframe嵌套
                .and()
                .headers().frameOptions().sameOrigin()

                .and().csrf().disable()
        ;
    }
}
