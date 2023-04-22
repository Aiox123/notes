package cn.nean.notes.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.codec.Base62;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.nean.notes.model.dto.AccountDto;
import cn.nean.notes.model.pojo.User;
import cn.nean.notes.common.response.RestResponse;
import cn.nean.notes.config.EmailUtils;
import cn.nean.notes.mapper.UserMapper;
import cn.nean.notes.service.AuthService;
import cn.nean.notes.utils.JwtUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static cn.nean.notes.common.constants.RedisConstants.*;

@Service
@Slf4j
public class AuthServiceImpl extends ServiceImpl<UserMapper, User>
        implements AuthService{

    @Resource
    UserMapper userMapper;
    @Resource
    EmailUtils emailUtils;
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public RestResponse<String> applyCode(String email) {
        // 生成验证码
        String code = generateCode();
        // 发送到 qq邮箱
        emailUtils.sendVerificationCode(email,"EchoNotes账号注册验证码5分钟内有效",code);
        //  验证码保存到 redis 缓存 5 分钟内有效
        email = email.substring(0, email.indexOf('@'));
        String key = AUTH_CODE_KEY + email;
        stringRedisTemplate.opsForValue().set(key,code,AUTH_CODE_TTL, TimeUnit.MINUTES);
        return RestResponse.success("验证码已发送!");
    }


    @Override
    public RestResponse<Object> register(AccountDto accountDto) {
        // 判断验证码是否正确
        boolean isCorrect = verificationCode(accountDto);
        if(!isCorrect){
            return RestResponse.validFail("验证码错误");
        }
        // 生成账号
        accountDto.setUsername(generateAccount());
        // 根据账号 生成密码
        accountDto.setPassword(generatePassword(accountDto.getUsername()));
        // 对象转化 -> 账号入库
        User user = accountToUser(accountDto);
        int isSave = userMapper.insert(user);
        if (isSave > 0){
            return RestResponse.success("注册成功,您的账号: "+ user.getUsername());
        }
        return RestResponse.validFail("系统繁忙请稍后重试");
    }

    @Override
    public RestResponse<Object> login(AccountDto accountDto) {
        // 根据账号或邮箱去查询数据库是否存在该账号
        User user = userMapper.queryByUsernameOrEmail(accountDto);
        if(user == null){
            return RestResponse.validFail("账号不存在,请确认账号!");
        }
        // 匹配密码是否正确
        boolean isMatch = matchPassword(accountDto, user);
        if(!isMatch){
            return RestResponse.validFail("密码错误!");
        }
        String userID = user.getId().toString();
        // 生成 token
        String token = JwtUtil.createJWT(userID);
        // 将生成的 token 存入 redis
        stringRedisTemplate.opsForValue().set(AUTH_LOGIN_KEY + userID,token,AUTH_LOGIN_TTL, TimeUnit.MINUTES);
        // 将 user 对象转化为 Map 结构
        Map<String, Object> userMap = userToMap(user);
        // 使用Redis Hash结构 存储用户信息
        stringRedisTemplate.opsForHash().putAll(AUTH_USER_KEY + userID,userMap);
        // 设置有效期
        stringRedisTemplate.expire(AUTH_USER_KEY + userID,AUTH_USER_TTL,TimeUnit.MINUTES);
        Map<String,Object> result = new HashMap<>();
        result.put("token",token);
        result.put("user",user);
        return RestResponse.success(result);
    }

    /*
    * 将 user 对象转化为 Map 结构
    * */
    private Map<String, Object> userToMap(User user){
        return  BeanUtil.beanToMap(user, new HashMap<>(),
                CopyOptions.create().
                        setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));
    }

    /*
    * 生成随机验证码
    * */
    private String generateCode(){
        Random random = new Random();
        //把随机生成的数字转成字符串
        StringBuilder code = new StringBuilder(String.valueOf(random.nextInt(9)));
        for (int i = 0; i < 5; i++) {
            code.append(random.nextInt(9));
        }
        return code.toString();
    }

    /*
    *  将 AccountDto 对象转成 User对象
    * */
    private User accountToUser(AccountDto accountDto){
        String now = DateUtil.now();
        return User.builder()
                .username(accountDto.getUsername())
                .password(accountDto.getPassword())
                .cellPhone(accountDto.getCellPhone())
                .email(accountDto.getEmail())
                .type(0)
                .status(1)
                // TODO 待完善补全用户头像信息
                .avatar("")
                .createTime(now)
                .build();
    }

    /*
    * 判断验证码是否正确
    * */
    private boolean verificationCode(AccountDto accountDto) {
        String email = accountDto.getEmail().substring(0, accountDto.getEmail().indexOf('@'));
        // 模拟从 redis 中获取验证码
        String key = AUTH_CODE_KEY + email;
        String code = stringRedisTemplate.opsForValue().get(key);
        Optional<AccountDto> accountOptional = Optional.of(accountDto);
        assert code != null;
        return accountOptional
                .map(AccountDto::getCode)
                .filter(code::equals)
                .isPresent();
    }

    /*
    * 生成 EchoNotes 平台账号 (可能会重复)
    *  */
    private String generateAccount(){
        int id = RandomUtil.randomInt(10000000, 99999999);
        return "EN-" + id;
    }

    /*
    * 生成 EchoNotes 平台 密码 (初始密码为 Echo + 账号数字部分)
    * 第一次登录后提醒修改密码
    * */
    private static String generatePassword(String account){
        // 拼接密码
        String password = "Echo" + account.substring(3);
        // 加密密码 并返会
        return Base62.encode(password);
    }

    /*
    *  匹配密码是否正确
    * */
    private boolean matchPassword(AccountDto accountDto,User user){
        String password = Base62.decodeStr(user.getPassword());
        return password.equals(accountDto.getPassword());
    }
}
