package cn.nean.notes.mapper;


import cn.nean.notes.model.pojo.Note;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteMapper extends BaseMapper<Note> {

    int updateNote(@Param("note")Note note);

    int updateStatus(@Param("noteId") Long noteId);

    List<Note> defaultQueryNotes(@Param("noteId") Long noteId,@Param("pages") Integer pages);
}
