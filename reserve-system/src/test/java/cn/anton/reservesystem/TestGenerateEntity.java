package cn.anton.reservesystem;

//import com.baomidou.mybatisplus.annotation.FieldFill;
//import com.baomidou.mybatisplus.generator.FastAutoGenerator;
//import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
//import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
//import com.baomidou.mybatisplus.generator.fill.Column;
//import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.junit4.SpringRunner;

//import java.nio.file.Paths;


@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestGenerateEntity {

    private final String JDBC_URL = "jdbc:mysql://localhost:3306/access_reservation?serverTimezone=Asia/Shanghai";
    private final String USERNAME = "root";
    private final String PASSWORD = "Dong1123";


    /*
    // 文件生成
    @Test
    public void testGenerateEntityFile(){
        FastAutoGenerator.create(JDBC_URL, USERNAME, PASSWORD)
                .globalConfig(builder -> builder
                        .author("丶Anton")
                        .outputDir("/Users/antonluo/Desktop/entity")
                        .commentDate("yyyy-MM-dd")
                )
                .packageConfig(builder -> builder
                        .parent("cn.anton.reservesystem")
                        .entity("entity")
                        .mapper("mapper")
                        .service("service")
                        .serviceImpl("service.impl")
                        .xml("mapper.xml")
                )
                .strategyConfig(builder -> builder
                        .entityBuilder()
                        .enableLombok()
                        .addTableFills(
                                new Column("create_time", FieldFill.INSERT)
                        )
                )
                .templateEngine(new VelocityTemplateEngine())
                .execute();
    }*/

}
