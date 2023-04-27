package cn.nean.notes.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteVo {

    private Long id;

    private Integer userId;

    private String nickname;

    private String avatar;

    private String title;

    private String content;

    private List<String> tags;

    private Integer collects;

    private Integer likes;

    private Integer comments;

    private Integer readCounts;

    private Integer forwards;

    private String time;
}
