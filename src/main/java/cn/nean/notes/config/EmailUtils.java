package cn.nean.notes.config;


import cn.nean.notes.common.exception.EmailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class EmailUtils {

    @Resource
    private JavaMailSender mailSender;

    @Value("${spring.mail.from}")
    private String from;

    /*
    *  用户注册发送验证码
    * */
    public void sendVerificationCode(String to,String subject,String verificationCode){
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(verificationCode);
            mailSender.send(message);
            log.info("注册验证码已发送:{}",message.toString());
        }catch (Exception e){
            log.error(e.getMessage());
            EmailException.cast(e.getMessage());
        }
    }
}
