package cn.nean.notes.rest.note;


import cn.nean.notes.common.response.RestResponse;
import cn.nean.notes.model.dto.NoteDto;
import cn.nean.notes.model.vo.NoteVo;
import cn.nean.notes.service.NoteService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/note")
public class NoteApi {

    @Resource
    NoteService noteService;

    /*
    * 保存笔记
    * */
    @PostMapping("/take")
    public RestResponse<Object> takeNote(@RequestBody NoteDto noteDto){
        return noteService.takeNote(noteDto);
    }

    /*
    * 更新笔记
    * */
    @PostMapping("/update")
    public RestResponse<Object> updateNote(@RequestBody NoteDto noteDto){
        return noteService.updateNote(noteDto);
    }

    /*
    * 发布笔记
    * */
    @PostMapping("/release/{noteId}")
    public RestResponse<Object> releaseNote(@PathVariable Long noteId){
        return noteService.releaseNote(noteId);
    }

    /*
    * 默认查询笔记
    * */
    @GetMapping("/default/query/{noteId}")
    public RestResponse<List<NoteVo>> defaultQuery(@PathVariable(required = false) Long noteId){
        if (noteId == null){
            noteId = 0L;
        }
        return noteService.defaultQuery(noteId);
    }
}
