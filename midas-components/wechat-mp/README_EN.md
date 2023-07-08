# WECHAT MINI PROGRAM AUTHENTICATION COMPONENT [中文](./README.md)

## [MiniProgramAuthenticationProcessingFilter](./src/main/java/com/mtfm/wechat_mp/filter/MiniProgramAuthenticationProcessingFilter.java)

This type of WeChat mini program login interceptor, the default mini program login API path is **/solar/api/v1/mp/login**. 
If you need to customize the path, you need to re cover the login authentication interceptor in the Spring container;
You can also customize the processing class **AuthenticationSuccessHandle** after successful authentication and the 
processing class **AuthenticationFailureHandle** after failure. If you rewrite this
Class, you must use [MiniProgramAuthenticationToken] (./src/main/java/com/mtfm/wechat_mp/authentication/MiniProgramAuthenticationToken. java)
Call the provider because in **ProviderManager**, the supports method needs to be called to determine and use the 
corresponding provider for authentication;

## [MiniProgramUserDetailsAuthenticationProvider](./src/main/java/com/mtfm/wechat_mp/authentication/MiniProgramUserDetailsAuthenticationProvider.java)

Applet authentication processing class, which includes the **OauthCodeService** remote WeChat authentication bean. 
However, before using it, it is necessary to configure the AppId and secret of the WeChat mini program in the configuration 
file. The specific requirements are Referring to **WechatMiniProgramConfiguration**, during the authentication process, 
there will be two user acquisition operations. If there is no matching WeChat user for the first time, the user will be 
directly created in the exception, and the creation is completed
After obtaining the user again, if it is not obtained, an exception of creation failure will be thrown;

## dependency
```
        <dependency>
            <groupId>com.mtfm</groupId>
            <artifactId>wechat-mp</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
```

After import this component, as the **SecurityFilterChain** was not built, it is necessary to manually introduce the
interceptor chain. Example:

```
public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
    security.logout().disable()
            .csrf().disable()
            .addFilterAfter(miniProgramAuthenticationProcessingFilter, LogoutFilter.class)
            .sessionManagement().disable();
    return security.build();
}
```

Secondly, it should be noted that since this component only implements the authentication function of WeChat mini programs 
and does not overly implement the **UserDetailsManager** interface, it needs to be introduced and customized for 
implementation; But it needs to be noted When implementing the **createUser** method, it is necessary to strongly 
convert **UserDetails** to [CreateUser] (./src/main/java/com/mtfm/wechat_mp/authentication/CreateUser. java),
For details, please refer to [MiniProgramUserDetailsAuthenticationProvider] (./src/main/java/com/mtfm/wechat_mp/authentication/MiniProgramUserDetailsAuthenticationProvider. java),
Therefore, it is best to use the universal UserDetailsManager implementation class for adaptation proxies to convert 
CreateUser into an object in the project;
