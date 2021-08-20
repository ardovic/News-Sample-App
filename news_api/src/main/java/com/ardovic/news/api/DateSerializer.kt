package com.ardovic.news.api

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Serializer(forClass = Date::class)
internal object DateSerializer : KSerializer<Date?> {

    private val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)

    override fun serialize(encoder: Encoder, value: Date?) {
        if (value == null) {
            encoder.encodeNull()
        } else {
            encoder.encodeString(dateFormat.format(value))
        }
    }

    override fun deserialize(decoder: Decoder): Date? {
        return dateFormat.parse(decoder.decodeString())
    }
}
