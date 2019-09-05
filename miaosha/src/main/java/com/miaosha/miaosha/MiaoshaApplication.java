package com.miaosha.miaosha;

import com.miaosha.miaosha.config.DataSourceConfig;
import com.miaosha.miaosha.dao.UserInfoMapper;
import com.miaosha.miaosha.entity.UserInfo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages = {"com.miaosha.miaosha"})
//@EnableConfigurationProperties({DataSourceConfig.class})
@MapperScan("com.miaosha.miaosha.dao")
@RestController
public class MiaoshaApplication {
    public static void main(String[] args) {
        SpringApplication.run(MiaoshaApplication.class, args);
    }
}
