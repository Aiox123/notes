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
}
