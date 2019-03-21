package recordstorage

import common.constants.Kafka
import common.engine.Consumer
import common.entities.BudgetRecord
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import org.springframework.util.SerializationUtils

@Service
class RecordKafkaConsumer : Consumer {
    val logger = LoggerFactory.getLogger(RecordKafkaConsumer::class.java)!!

    @KafkaListener(topics=[Kafka.RECORDS_TOPIC])
    override fun getMessage(record: ConsumerRecord<String, ByteArray>) {
        val r = SerializationUtils.deserialize(record.value()) as BudgetRecord
        logger.debug("Receive: %s from %s".format(record.key(), record.topic()))
    }
}