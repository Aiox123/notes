package cn.nean.notes.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.nean.notes.common.response.RestResponse;
import cn.nean.notes.mapper.NoteMapper;
import cn.nean.notes.mapper.UserMapper;
import cn.nean.notes.model.dto.NoteDto;
import cn.nean.notes.model.dto.UserDto;
import cn.nean.notes.model.pojo.Note;
import cn.nean.notes.model.vo.NoteVo;
import cn.nean.notes.service.NoteService;
import cn.nean.notes.utils.UserHolder;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cn.nean.notes.common.constants.SysConstants.DEFAULT_PAGE_SIZE;

@Service
@Slf4j
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note>
        implements NoteService {

    @Resource
    NoteMapper noteMapper;

    @Resource
    UserMapper userMapper;

    @Override
    public RestResponse<Object> takeNote(NoteDto noteDto) {
        // NoteDto 对象转为 Note对象
        Note note = noteDtoToNote(noteDto);
        // 保存创建时间
        note.setCreateTime(DateUtil.now());
        // 存入 db
        boolean isSave = save(note);
        if(isSave){
            return RestResponse.success("保存成功!");
        }
        return RestResponse.validFail("保存失败!");
    }

    @Override
    public RestResponse<Object> updateNote(NoteDto noteDto) {
        // NoteDto 对象转为 Note对象
        Note note = noteDtoToNoteByUpdate(noteDto);
        // 更新 db
        int isUpdate = noteMapper.updateNote(note);
        if(isUpdate > 0){
            return RestResponse.success("更新成功!");
        }
        return RestResponse.validFail("更新失败!");
    }

    @Override
    public RestResponse<Object> releaseNote(Long noteId) {
        // 更新笔记状态
        int isUpdate = noteMapper.updateStatus(noteId);
        if(isUpdate > 0){
            // TODO 异步将数据存入 ES
            return RestResponse.success("发布成功!");
        }
        return RestResponse.validFail("发布失败!");
    }

    @Override
    public RestResponse<List<NoteVo>> defaultQuery(Long noteId) {
        List<Note> notes = noteMapper.defaultQueryNotes(noteId, DEFAULT_PAGE_SIZE);
        List<NoteVo> noteVos = new ArrayList<>(10);
        for (Note note : notes) {
            UserDto userDto = userMapper.queryUserDtoByUserId(note.getUserId());
            noteVos.add(noteToNoteVo(note, userDto));
        }
        return RestResponse.success(noteVos);
    }

    /*
    *  Note -> NoteVo
    * */
    private NoteVo noteToNoteVo(Note note,UserDto userDto){
        List<String> tags = tagToTagList(note.getTags());
        return NoteVo.builder()
                .id(note.getId())
                .userId(userDto.getId())
                .nickname(userDto.getNickname())
                .avatar(userDto.getAvatar())
                .title(note.getTitle())
                .content(note.getContent())
                .tags(tags)
                .likes(note.getLikes())
                .collects(note.getCollects())
                .comments(note.getComments())
                .forwards(note.getForwards())
                .readCounts(note.getReadCounts())
                .create_time(note.getCreateTime())
                .build();
    }

    /*
    * tag String -> List tags
    * */
    private List<String> tagToTagList(String tag){
        return Arrays.asList(tag.split(","));
    }

    /*
     * 更新 -> NoteDto 对象转为 Note对象
     * */
    private Note noteDtoToNoteByUpdate(NoteDto noteDto){
        String tag = tagListToStr(noteDto.getTags());
        return Note.builder()
                .title(noteDto.getTitle())
                .content(noteDto.getContent())
                .tags(tag)
                .build();
    }

    /*
    * NoteDto 对象转为 Note对象
    * */
    private Note noteDtoToNote(NoteDto noteDto){
        String tag = tagListToStr(noteDto.getTags());
        Integer userId = UserHolder.getUser().getId();
        return Note.builder()
                .userId(userId)
                .title(noteDto.getTitle())
                .content(noteDto.getContent())
                .tags(tag)
                .collects(0)
                .likes(0)
                .comments(0)
                .readCounts(0)
                .forwards(0)
                .status(0)
                .build();
    }

    /*
    * 将 Tags List 转化成 Tag String
    * */
    private String tagListToStr(List<String> tags){
        StringBuilder tag = new StringBuilder();
        for (String s : tags) {
            tag.append(s).append(',');
        }
        tag.deleteCharAt(tag.length() - 1);
        return tag.toString();
    }
}
