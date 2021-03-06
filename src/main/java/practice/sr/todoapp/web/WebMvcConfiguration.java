package practice.sr.todoapp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import practice.sr.todoapp.commons.web.error.ReadableErrorAttributes;
import practice.sr.todoapp.commons.web.view.CommaSeparatedValuesView;
import practice.sr.todoapp.security.UserSessionRepository;
import practice.sr.todoapp.security.web.method.UserSessionHandlerMethodArgunmentResolver;
import practice.sr.todoapp.security.web.servlet.RolesVerifyHandlerInterceptor;
import practice.sr.todoapp.security.web.servlet.UserSessionFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring Web MVC 설정
 *
 * @author springrunner.kr@gmail.com
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    UserSessionRepository sessionRepository;

    public WebMvcConfiguration(UserSessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    // 정적 리소스 지원
    // 기존 resource handler 외에 resource handler를 추가하는 방법
    // 추가 시 기존 resource handler 작동 안하는 듯함.
    /*@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**")
                .addResourceLocations("assets/","classpath:assets/")
                .setCachePeriod(31556926);
    }*/

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        // registry.enableContentNegotiation();
        // 위와 같이 직접 설정하면, 스프링부트가 구성한 ContentNegotiatingViewResolver 전략이 무시된다.
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UserSessionHandlerMethodArgunmentResolver(sessionRepository));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RolesVerifyHandlerInterceptor());
    }

    @Bean
    public ErrorAttributes errorAttributes(MessageSource messageSource) {
        return new ReadableErrorAttributes(messageSource);
    }

    @Bean
    public FilterRegistrationBean<UserSessionFilter> userSessionFilter(UserSessionRepository sessionRepository) {
        FilterRegistrationBean<UserSessionFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new UserSessionFilter(sessionRepository));

        return registrationBean;
    }

    /**
     * 스프링부트가 생성한 ContentNegotiatingViewResolver를 조작할 목적으로 작성된 컴포넌트
     */
    @Configuration
    public static class ContentNegotiationCustomizer {

        // 기존 view들에 새로 만든 view를 추가하여 viewResolver에 등록.
        @Autowired
        public void configurer(ContentNegotiatingViewResolver viewResolver) {
            List<View> defaultViews = new ArrayList<>(viewResolver.getDefaultViews());
            defaultViews.add(new CommaSeparatedValuesView());
            viewResolver.setDefaultViews(defaultViews);
        }

    }

}
