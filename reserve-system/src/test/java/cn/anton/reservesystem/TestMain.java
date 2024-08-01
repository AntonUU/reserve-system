package cn.anton.reservesystem;

import cn.anton.reservesystem.util.AliOSSUtil;
import com.aliyuncs.exceptions.ClientException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 类说明:
 *
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2024/8/1 21:19
 */
@SpringBootTest
public class TestMain {

    @Autowired
    private AliOSSUtil ossUtil;

    @Test
    public void testPutFileByFilePath() throws ClientException, IOException {
        ossUtil.putFileByFilePath("test1.jpg", "/Users/antonluo/Desktop/25c4483c77037c5788d9acf4751dfbd7.jpg");
    }

    @Test
    public void testPutFileByStream() throws ClientException, IOException {
        FileInputStream file = new FileInputStream(new File("/Users/antonluo/Desktop/iShot_2024-07-30_16.38.08.png"));
        ossUtil.putFileByStream("test2.png", file);
    }

}
