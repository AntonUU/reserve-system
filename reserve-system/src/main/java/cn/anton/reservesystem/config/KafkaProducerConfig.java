package cn.anton.reservesystem.config;

import cn.anton.commonpackage.common.constant.KafkaConstant;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaAdmin;

/**
 * 类说明:
 *
 * @author 丶Anton
 * @email itanton666@gmail.com
 * @date 2024/6/7 09:49
 */
@Configuration
@EnableKafka
public class KafkaProducerConfig {

    @Autowired
    private KafkaAdmin kafkaAdmin;

    @Bean
    public NewTopic initialTopic() {
        return new NewTopic(KafkaConstant.RESERVE_SYSTEM_TOPIC_NAME,
                KafkaConstant.RESERVE_SYSTEM_NUM_PARTITIONS,
                KafkaConstant.RESERVE_SYSTEM_REPLICATION_FACTOR
        );
    }

}
