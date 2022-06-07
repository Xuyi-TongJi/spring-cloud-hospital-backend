package edu.seu.yygh.cmn.config;

import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mapstruct.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xuyitjuseu
 */
@Configuration
@MapperScan("edu.seu.yygh.cmn.mapper")
public class CmnConfig {

    /**
     * 分页插件
     * @return 分页插件Bean
     */
    @Bean
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        return new PaginationInnerInterceptor();
    }
}