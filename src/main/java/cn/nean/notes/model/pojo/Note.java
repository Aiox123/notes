package cn.nean.notes.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Note {

    private Long id;

    private Integer userId;

    private String title;

    private String content;

    private String tags;

    private Integer collects;

    private Integer likes;

    private Integer comments;

    private Integer forwards;

    private Integer status;

    private String create_time;
}
