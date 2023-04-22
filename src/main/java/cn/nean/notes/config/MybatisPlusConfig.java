package cn.nean.notes.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan("cn.nean.notes.mapper")
@Configuration
public class MybatisPlusConfig {
}
