package cn.nean.notes.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteDto {

    private Long id;

    private Integer userId;

    private String title;

    private String content;

    private List<String> tags;
}
