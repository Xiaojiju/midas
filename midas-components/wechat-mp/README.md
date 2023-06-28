# 微信小程序登陆组件 [english]()

## [MiniProgramAuthenticationProcessingFilter](./src/main/java/com/mtfm/wechat_mp/filter/MiniProgramAuthenticationProcessingFilter.java)

该类微信小程序登陆拦截器, 默认的小程序登陆api路径为 **/solar/api/v1/mp/login** ,如果需要自定义路径，需要重新在spring容器中重新覆盖登陆认证拦截器;
其中也可以自定义认证成功后的处理类 **AuthenticationSuccessHandler** 和失败后的处理类 **AuthenticationFailureHandler**, 如果进行重写该
类，则必须使用[MiniProgramAuthenticationToken](./src/main/java/com/mtfm/wechat_mp/authentication/MiniProgramAuthenticationToken.java)
进行调用provider，因为在 **ProviderManager** 中，需要调用supports方法进行判定使用对应的provider进行认证；

## [MiniProgramUserDetailsAuthenticationProvider](./src/main/java/com/mtfm/wechat_mp/authentication/MiniProgramUserDetailsAuthenticationProvider.java)

小程序认证处理类，其中包含了 **OauthCodeService** 远程微信认证Bean，但是在使用之前，需要在配置文件中配置微信小程序的AppId和secret，具体需要
参考 **WechatMiniProgramConfiguration**, 认证过程中会进行两次获取用户操作，第一次如果没有匹配搭配微信用户，会在异常中直接创建用户，创建完成
后，再次获取用户，如果没有获取到，则抛出创建失败的异常；

## 引用依赖
```
        <dependency>
            <groupId>com.mtfm</groupId>
            <artifactId>wechat-mp</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
```

在引入该组件后，由于没有构建 **SecurityFilterChain**,所以在引入后，需进行手动引入拦截器链，示例：
```
public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
    security.logout().disable()
            .csrf().disable()
            .addFilterAfter(miniProgramAuthenticationProcessingFilter, LogoutFilter.class)
            .sessionManagement().disable();
    return security.build();
}
```
其次需要注意的是，因为该组件仅仅实现微信小程序认证功能，并没有进行过度的实现**UserDetailsManager**接口，需要引入后，进行自定义实现；但是需要注意
的是，在实现**createUser**方法的时候，需要强转**UserDetails**为[CreateUser](./src/main/java/com/mtfm/wechat_mp/authentication/CreateUser.java)，
具体请查看[MiniProgramUserDetailsAuthenticationProvider](./src/main/java/com/mtfm/wechat_mp/authentication/MiniProgramUserDetailsAuthenticationProvider.java),
所以最好在通用UserDetailsManager实现类进行适配代理，将CreateUser转为项目中的对象；




