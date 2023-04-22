package cn.nean.notes.mapper;

import cn.nean.notes.model.dto.AccountDto;
import cn.nean.notes.model.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {


    User queryByUsernameOrEmail(@Param("account")AccountDto accountDto);
}
