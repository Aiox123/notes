package cn.nean.notes.interceptor;



import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.nean.notes.common.exception.EchoNotesException;
import cn.nean.notes.model.pojo.User;
import cn.nean.notes.utils.JwtUtil;
import cn.nean.notes.utils.UserHolder;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static cn.nean.notes.common.constants.RedisConstants.*;


@Slf4j
public class RefreshTokenInterceptor implements HandlerInterceptor {

    private final StringRedisTemplate stringRedisTemplate;

    public RefreshTokenInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 请求头获取 token
        String token = request.getHeader("token");
        if(StrUtil.isBlank(token)){
            return true;
        }
        // 3. 解析 token
        String userId;
        //获取userId
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            throw new EchoNotesException("token 不合法!");
        }
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash()
                .entries(AUTH_USER_KEY + userId);
        // 判断 用户是否存在
        if(userMap.isEmpty()){
            return true;
        }
        // 将查询到的 Hash 数据转为 UserDto对象
        User userDto = BeanUtil.fillBeanWithMap(userMap, new User(), false);
        // 存在，保持用户信息到 ThreadLocal
        UserHolder.saveUser(userDto);
        // 刷新 token 有效期
        stringRedisTemplate.expire(AUTH_USER_KEY + userId , AUTH_LOGIN_TTL , TimeUnit.MINUTES);
        stringRedisTemplate.expire(AUTH_LOGIN_KEY + userId , AUTH_LOGIN_TTL , TimeUnit.MINUTES);
        return true;
    }
}
