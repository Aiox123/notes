package cn.nean.notes.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {

    private String username;

    private String password;

    private String cellPhone;

    private String email;

    private String codeKey;

    private String code;

    /*
    * 登录方式（账号、邮箱、手机号）
    * */
    private String type;
}
