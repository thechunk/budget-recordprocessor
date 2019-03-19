package recordstorage

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@EnableAutoConfiguration
@ComponentScan
class RecordStorageApplication

fun main(args: Array<String>) {
    runApplication<RecordStorageApplication>(*args)
}