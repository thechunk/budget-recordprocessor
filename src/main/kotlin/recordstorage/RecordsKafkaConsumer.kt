package recordstorage

import common.constants.Kafka
import common.engine.Consumer
import common.entities.MessageRequest
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import org.springframework.util.SerializationUtils

@Service
class RecordKafkaConsumer(val budgetService: BudgetService) : Consumer {
    val logger = LoggerFactory.getLogger(RecordKafkaConsumer::class.java)!!

    @KafkaListener(topics=[Kafka.RECORDS_TOPIC])
    override fun getMessage(record: ConsumerRecord<String, ByteArray>) {
        val r = SerializationUtils.deserialize(record.value()) as MessageRequest
        val tokens = r.tokens
        if (tokens?.google != null) {
            budgetService.processRecord(r.record, tokens.google)
        }
        logger.debug("Receive: %s from %s".format(record.key(), record.topic()))
    }
}