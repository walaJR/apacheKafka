package com.example.mensajeria.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.topic.name}")
    private String topicName;

    public void enviarMensaje(String mensaje) {
        log.info("Enviando mensaje al topic '{}': {}", topicName, mensaje);

        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, mensaje);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Mensaje enviado exitosamente: [{}] con offset: [{}]",
                        mensaje,
                        result.getRecordMetadata().offset());
            } else {
                log.error("Error al enviar mensaje: [{}] debido a: {}",
                        mensaje,
                        ex.getMessage(),
                        ex);
            }
        });
    }
}