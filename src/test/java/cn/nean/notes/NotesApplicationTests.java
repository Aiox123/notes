package cn.nean.notes;

import cn.nean.notes.mapper.NoteMapper;
import cn.nean.notes.mapper.UserMapper;
import cn.nean.notes.model.dto.AccountDto;
import cn.nean.notes.model.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class NotesApplicationTests {

    @Resource
    UserMapper userMapper;

    @Resource
    NoteMapper noteMapper;

    @Test
    void contextLoads() {
/*        AccountDto accountDto = AccountDto.builder().username("").email("").build();
        User user = userMapper.queryByUsernameOrEmail(accountDto);
        System.out.println(user);*/
        System.out.println(noteMapper.selectById(1L));
    }

}
