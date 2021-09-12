package tacoscloudreactive.security;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.logout.DelegatingServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.stream.Collectors;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig
{
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http)
    {
        http
                .authorizeExchange()
                .pathMatchers("/design", "/orders", "/orders/**").hasAuthority("ROLE_USER")
                .anyExchange().permitAll()

                .and()
                .formLogin().loginPage("/login")
                .authenticationSuccessHandler(new RedirectServerAuthenticationSuccessHandler("/"))
                .requiresAuthenticationMatcher(
                        ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, "/login")
                )

                .and()
                .logout().logoutUrl("/logout")//使用 POST 登出
                .logoutSuccessHandler((exchange, authentication) -> {
                    ServerHttpResponse response = exchange.getExchange().getResponse();
                    response.setStatusCode(HttpStatus.FOUND);
                    response.getHeaders().setLocation(URI.create("/"));
                    response.getCookies().remove("JSESSIONID");
                    return exchange.getExchange().getSession().flatMap(WebSession::invalidate);
                })

                .and()
                .headers().frameOptions().mode(XFrameOptionsServerHttpHeadersWriter.Mode.SAMEORIGIN)

                .and()
                .csrf().disable()
        ;

        return http.build();
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters)
    {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }
}

