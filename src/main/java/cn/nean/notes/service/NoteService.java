package cn.nean.notes.service;

import cn.nean.notes.common.response.RestResponse;
import cn.nean.notes.model.dto.NoteDto;
import cn.nean.notes.model.pojo.Note;
import cn.nean.notes.model.vo.NoteVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface NoteService  extends IService<Note> {

    RestResponse<Object> takeNote(NoteDto noteDto);

    RestResponse<Object> updateNote(NoteDto noteDto);

    RestResponse<Object> releaseNote(Long noteId);

    RestResponse<List<NoteVo>> defaultQuery(Long noteId);
}
