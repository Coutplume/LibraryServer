package com.arthur.project.config;


import org.apache.tomcat.util.http.Rfc6265CookieProcessor;
import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

@Configuration
public class CookieConfig {

//    @Bean
//    public CookieSerializer cookieSerializer() {
//        CookieSerializer serializer = new
//        serializer.setCookieName("JSESSIONID");
//        serializer.setDomainName("localhost");
//        serializer.setCookiePath("/");
//        serializer.setCookieMaxAge(3600);
//        serializer.setSameSite("Lax");  // 设置SameSite属性
//        serializer.setUseHttpOnlyCookie(true);
//        serializer.setUseSecureCookie(false);
//        return serializer;
//    }
}
//    public void setSession(){
//        ResponseCookie responseCookie = ResponseCookie.from("JSESSIONID",httpServletRequest.getSession().getId())
//                .httpOnly(true)
//                .secure(true)
//                .domain("localhost")
//                .path("/")
//                //.maxAge(3600)
//                .sameSite("None")
//                .build()
//                ;
//        httpServletResponse.setHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
//    }
