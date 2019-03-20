package recordstorage

import java.io.Serializable

interface Consumer {
    fun getMessage(message: ByteArray)
}