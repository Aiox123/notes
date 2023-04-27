package cn.nean.notes.common.enums;

import cn.nean.notes.mapper.UserMapper;
import cn.nean.notes.model.dto.AccountDto;
import cn.nean.notes.model.pojo.User;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public enum LoginMethods {

    //指向内部枚举的同一个属性即可执行相同重复功能
    Account {
        @Override
        public User toLogin(AccountDto accountDto, UserMapper userMapper) {
            return userMapper.queryByUsername(accountDto.getUsername());
        }
    },

    Email {
        @Override
        public User toLogin(AccountDto accountDto, UserMapper userMapper) {
            return userMapper.queryByEmail(accountDto.getEmail());
        }
    },

    Phone {
        @Override
        public User toLogin(AccountDto accountDto, UserMapper userMapper) {
            log.info("手机号密码登录");
            return null;
        }
    };
    public abstract User toLogin(AccountDto accountDto, UserMapper userMapper);
}
