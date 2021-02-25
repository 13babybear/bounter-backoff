package cn.bounter.backoff;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableRetry
@MapperScan("cn.bounter.backoff.dao")
public class BounterBackoffApplication {

    public static void main(String[] args) {
        SpringApplication.run(BounterBackoffApplication.class, args);
    }

}
