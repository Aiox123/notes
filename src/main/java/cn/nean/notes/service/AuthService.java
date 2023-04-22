package cn.nean.notes.service;

import cn.nean.notes.model.dto.AccountDto;
import cn.nean.notes.model.pojo.User;
import cn.nean.notes.common.response.RestResponse;
import com.baomidou.mybatisplus.extension.service.IService;

public interface AuthService extends IService<User> {

    /*
    *  账号注册
    * */
    RestResponse<Object> register(AccountDto accountDto);

    RestResponse<String> applyCode(String email);

    RestResponse<Object> login(AccountDto accountDto);
}
