package edu.seu.yygh.hosp.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("edu.seu.yygh.hosp.mapper")
public class HospConfig {
}