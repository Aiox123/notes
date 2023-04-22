package cn.nean.notes.service.impl;

import cn.nean.notes.mapper.NoteMapper;
import cn.nean.notes.model.pojo.Note;
import cn.nean.notes.service.NoteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note>
        implements NoteService {
}
