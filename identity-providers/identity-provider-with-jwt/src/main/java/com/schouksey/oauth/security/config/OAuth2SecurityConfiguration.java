package com.schouksey.oauth.security.config;/*
 * Application    : micro-services
 * Package Name   : com.schouksey.oauth.config
 * Class Name     : OAuth2SecurityConfiguration
 * Author         : SUMIT CHOUKSEY <sumitchouksey2315@gmail.com>
 * Created On     : 11/10/2017
 * Description    : Oauth2 Configuration class
 */

import com.schouksey.identity.provider.service.CustomClientDetailsService;
import com.schouksey.oauth.security.custom.config.CustomAuthenticationProvider;
import com.schouksey.oauth.security.custom.config.CustomOAuth2Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.*;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@Controller
public class OAuth2SecurityConfiguration extends WebMvcConfigurerAdapter
{
    /**
     * @param user
     * @return Principal
     *          -- A user object generated during authentication along with token details
     * @author  Sumit Chouksey "sumitchouksey2315@gmail.com"
     */
    @RequestMapping("/user")
    @ResponseBody
    public Principal user(Principal user){
        return user;
    }

    /**
     * Web Security Configuration
     * <br> 1. Custom Authentication Provider
     * <br> 2. HttpSecurity Control
     * @author  Sumit Chouksey "sumitchouksey2315@gmail.com"
     */
    @Configuration
    @Order(-10)
    protected static class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{

        @Autowired
        private CustomAuthenticationProvider authenticationProvider;

        /**
         * Initialize CustomAuthenticationProvider for Spring Authentication
         * @param auth
         * @throws Exception
         */
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(authenticationProvider);
        }

        /**
         * Initialize authenticationManagerBean
         * @return AutenticationManager
         * @throws Exception
         */
        @Override
        @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .formLogin().loginPage("/login").permitAll()
                    .and()
                    .requestMatchers().antMatchers("/login","/oauth/authorize","oauth/confirm_access")
                    .and()
                    .authorizeRequests().anyRequest().authenticated();
        }
    }

    /**
     * Authorization Server Configurations
     * Spring OAuth2.o Security Configurations
     * <br> 1. Client Authentication
     * <br> 2. Token Generation
     * <br> 3. Custom Error Messages
     * @author  Sumit Chouksey "sumitchouksey2315@gmail.com"
     */
    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter{

        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;

        @Autowired
        private CustomClientDetailsService clientDetailsService;

        @Autowired
        private TokenStore tokenStore;

        @Override
        public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
            security.realm("/secure/client")
                    // Disable http basic authentication for client
                    .allowFormAuthenticationForClients()
                    .tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
        }

        /**
         * Handles CustomClientDetailServices to validate client details
         * @param clients
         * @throws Exception
         */
        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.withClientDetails(clientDetailsService);
        }

        /**
         * Token Generation & exception translator implementation
         * @param endpoints
         * @throws Exception
         */
        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.tokenStore(tokenStore).tokenEnhancer(jwtTokenConverter()).authenticationManager(authenticationManager);
            /**
             * This Logic is return to customize the default response of OAuth2 Error Messages
             * Using some Customized Exception class - Like CustomOAuth2Exception
             * @author Sumit Chouksey "sumitchouksey23152gmail.com"
             */
            endpoints.exceptionTranslator(e->
                    {
                        if (e instanceof CustomOAuth2Exception)
                        {
                            OAuth2Exception oAuth2Exception = (OAuth2Exception) e;
                            CustomOAuth2Exception cc = new CustomOAuth2Exception(oAuth2Exception.getMessage());
                            if(oAuth2Exception.getAdditionalInformation()!=null)
                            {
                                for (Map.Entry<String, String> entry : oAuth2Exception.getAdditionalInformation().entrySet())
                                    cc.addAdditionalInformation(entry.getKey(),entry.getValue());
                            }
                            return ResponseEntity
                                    .status(oAuth2Exception.getHttpErrorCode())
                                    .body(cc);
                        }
                        else
                            throw e;
                    }
            );
        }

        /**
         * Generates Tokens Using JWT Store
         * @author Sumit Chouksey "sumitchouksey23152gmail.com"
         */
        @Bean
        public TokenStore tokenStore(){
            return new JwtTokenStore(jwtTokenConverter());
        }

        /**
         * Generates Token from  JKS Java keystore File
         * @return JwtAccessTokenConverter
         *         - Return JwtAccessTokenConverter Uses Asymmetric key
         */
        @Bean
        protected JwtAccessTokenConverter jwtTokenConverter(){
            KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("oauth2-jwt.jks"), "Shruti@300492".toCharArray());
            JwtAccessTokenConverter converter =new JwtAccessTokenConverter();
            converter.setKeyPair(keyStoreKeyFactory.getKeyPair("oauth2-jwt"));
            return converter;
        }
    }

    @Component
    @EnableResourceServer
    protected class ResourceServerConfiguration extends ResourceServerConfigurerAdapter{
        @Autowired
        TokenStore tokenStore;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.resourceId("identity-provider").tokenStore(tokenStore);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable().anonymous().disable()
                    .authorizeRequests()
                    .antMatchers("/**").authenticated();
        }

        @Bean
        protected JwtAccessTokenConverter jwtTokenEnhancer() {
            JwtAccessTokenConverter converter =  new JwtAccessTokenConverter();
            Resource resource = new ClassPathResource("public.cert");
            String publicKey = null;
            try {
                publicKey = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println(publicKey);
            converter.setVerifierKey(publicKey);
            return converter;
        }
    }
}
