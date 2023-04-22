package cn.nean.notes.service;

import cn.nean.notes.common.response.RestResponse;
import cn.nean.notes.model.dto.NoteDto;
import cn.nean.notes.model.pojo.Note;
import com.baomidou.mybatisplus.extension.service.IService;

public interface NoteService  extends IService<Note> {

    RestResponse<Object> takeNote(NoteDto noteDto);

    RestResponse<Object> updateNote(NoteDto noteDto);

    RestResponse<Object> releaseNote(Long noteId);
}
