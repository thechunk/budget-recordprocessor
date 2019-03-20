package recordstorage

import common.constants.Kafka
import common.entities.BudgetRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import org.springframework.util.SerializationUtils

@Service
class KafkaConsumer : Consumer {
    val logger = LoggerFactory.getLogger(KafkaConsumer::class.java)!!

    @KafkaListener(topics=[Kafka.RECORDS_TOPIC], groupId = "group_id")
    override fun getMessage(message: ByteArray) {
        var r = SerializationUtils.deserialize(message) as BudgetRecord
        logger.debug(r.description)
    }
}