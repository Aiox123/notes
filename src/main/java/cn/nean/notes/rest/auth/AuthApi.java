package cn.nean.notes.rest.auth;

import cn.nean.notes.model.dto.AccountDto;
import cn.nean.notes.common.response.RestResponse;
import cn.nean.notes.service.AuthService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/auth")
public class AuthApi {

    @Resource
    AuthService authService;

    // 发送注册验证码
    @GetMapping("/applyCode/{email}")
    public RestResponse<String> applyCode(@PathVariable String email){
        return authService.applyCode(email);
    }


    // 用户注册
    @PostMapping("/register")
    public RestResponse<Object> register(@RequestBody AccountDto accountDto){
        return authService.register(accountDto);
    }

    // TODO 用户登录 (qq邮箱登录 || 用户账号登录)
    @PostMapping("/login")
    public RestResponse<Object> login(@RequestBody AccountDto accountDto){
        return authService.login(accountDto);
    }
}
