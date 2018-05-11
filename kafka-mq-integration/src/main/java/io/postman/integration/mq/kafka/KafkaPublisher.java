package io.postman.integration.mq.kafka;

import io.postman.common.exception.EventComponentException;
import io.postman.common.util.LoggerUtil;
import io.postman.common.util.StringUtil;
import io.postman.integration.Callback;
import io.postman.integration.Publisher;
import org.apache.kafka.clients.producer.*;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.concurrent.Future;

/**
 * Created by caojun on 2017/11/13.
 */
@Service("kafkaPublisher")
public class KafkaPublisher implements Publisher {

    private Producer<Object, Object> producer;

    public KafkaPublisher(){

    }
    /**
     * 构造方法
     *
     * @param brokers broker列表
     * @param acks 确认类型
     * @param codec 压缩类型
     * @param batch 批量大小
     */
    public KafkaPublisher(final String brokers, final int retries, final String acks,
                          final String codec, final int batch){
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        props.put(ProducerConfig.RETRIES_CONFIG, retries);
        props.put(ProducerConfig.ACKS_CONFIG, acks);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, codec);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, codec);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, batch);
        // 默认参数
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);

        try {
            producer = new KafkaProducer<>(props);
        } catch (Exception e) {
            LoggerUtil.error("KafkaProducer 初始化错误：{}", StringUtil.getStackTrace(e));
        }
    }

    /**
     * 初始化方法
     *
     * @param brokers broker列表
     * @param acks 确认类型
     * @param codec 压缩类型
     * @param batch 批量大小
     */
    public void initialize(final String brokers, final int retries, final String acks,
                          final String codec, final int batch){
        if (producer != null)
            return;
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        props.put(ProducerConfig.RETRIES_CONFIG, retries);
        props.put(ProducerConfig.ACKS_CONFIG, acks);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, codec);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, codec);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, batch);
        // 默认参数
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);

        try {
            producer = new KafkaProducer<>(props);
        } catch (Exception e) {
            LoggerUtil.error("KafkaProducer 初始化错误：{}", StringUtil.getStackTrace(e));
        }
    }

    public void sendNIO(String msgName, Object routingKey, Object value, final Callback callback) {
        if (producer == null)
            throw new EventComponentException("KafkaProducer is not initialize");
        if (StringUtil.isEmptyOrNull(msgName) || value == null
                || callback == null)
            throw new EventComponentException("params is empty!");
        producer.send(new ProducerRecord<>(msgName, routingKey, value),
                (metadata, exception) -> callback.onCompletion(metadata!=null ? metadata.toString() : null, exception)
        );
    }

    public String sendBIO(String msgName, Object routingKey, Object value) {
        if (producer == null)
            throw new EventComponentException("KafkaProducer is not initialize");
        if (StringUtil.isEmptyOrNull(msgName) || value == null)
            throw new EventComponentException("params is empty!");
        try {
            Future<RecordMetadata> future = producer.send(new ProducerRecord<>(msgName, routingKey, value));
            if (future != null)
                return future.get().toString();
            else
                return null;
        } catch (Exception e) {
            LoggerUtil.error("kafka发送消息出错，错误信息：{}", StringUtil.getStackTrace(e));
            throw new EventComponentException("kafka发送消息出错，错误信息：{}",e);
        }
    }
}
