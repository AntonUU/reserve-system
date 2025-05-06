package cn.anton.reservesystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("cn.anton.reservesystem.dao")
public class ReserveSystemApplication {

        public static void main(String[] args) {
        SpringApplication.run(ReserveSystemApplication.class, args);
    }
}

