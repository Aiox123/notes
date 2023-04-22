package cn.nean.notes.config;

import cn.nean.notes.interceptor.LoginInterceptor;
import cn.nean.notes.interceptor.RefreshTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .excludePathPatterns("/auth/login")
                .excludePathPatterns("/auth/register")
                .excludePathPatterns("/auth/applyCode/{email}")
                .order(1);
        registry.addInterceptor(new RefreshTokenInterceptor(stringRedisTemplate))
                .order(0);
    }
}
