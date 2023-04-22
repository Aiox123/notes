package cn.nean.notes.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private Integer id;

    private String username;

    private String password;

    private String cellPhone;

    private String email;

    private Integer type;

    private Integer status;

    private String avatar;

    private String createTime;

}
