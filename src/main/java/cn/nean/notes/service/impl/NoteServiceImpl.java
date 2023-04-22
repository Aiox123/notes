package cn.nean.notes.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.nean.notes.common.response.RestResponse;
import cn.nean.notes.mapper.NoteMapper;
import cn.nean.notes.model.dto.NoteDto;
import cn.nean.notes.model.pojo.Note;
import cn.nean.notes.service.NoteService;
import cn.nean.notes.utils.UserHolder;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note>
        implements NoteService {

    @Resource
    NoteMapper noteMapper;

    @Override
    public RestResponse<Object> takeNote(NoteDto noteDto) {
        // NoteDto 对象转为 Note对象
        Note note = noteDtoToNote(noteDto);
        // 保存创建时间
        note.setCreate_time(DateUtil.now());
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
                .readingQuantity(0)
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
