package dev.ktcloud.black.order.common.adapter.configuration.kafka

import dev.ktcloud.black.order.common.adapter.infrastructure.kafka.model.InventoryReservedResultMessage
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer

@Configuration
class KafkaConfig(
    @Value("\${spring.kafka.bootstrap-servers}")
    private val bootstrapServers: String
) {
    @Bean
    fun inventoryReservedResultConsumerFactory(): ConsumerFactory<String, InventoryReservedResultMessage> {
        val deserializer = JsonDeserializer(InventoryReservedResultMessage::class.java).apply {
            addTrustedPackages("dev.ktcloud.black.*")
            setUseTypeHeaders(false)
        }

        val configProps = mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to JsonDeserializer::class.java,
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest"
        )

        return DefaultKafkaConsumerFactory(configProps, StringDeserializer(), deserializer)
    }

    @Bean
    fun inventoryReservedResultContainerFactory(
        configurer: ConcurrentKafkaListenerContainerFactoryConfigurer
    ): ConcurrentKafkaListenerContainerFactory<String, InventoryReservedResultMessage> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, InventoryReservedResultMessage>()
        val cf = inventoryReservedResultConsumerFactory()

        factory.consumerFactory = cf

        configurer.configure(
            factory as ConcurrentKafkaListenerContainerFactory<Any, Any>,
            cf as ConsumerFactory<Any, Any>
        )

        return factory
    }
}