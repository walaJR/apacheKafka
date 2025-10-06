package com.example.mensajeria.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}", containerFactory = "kafkaListenerContainerFactory")
    public void consumirMensaje(
            @Payload String mensaje,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {

        log.info("===========================================");
        log.info("Mensaje recibido del topic: {}", topic);
        log.info("Partición: {}", partition);
        log.info("Offset: {}", offset);
        log.info("Contenido del mensaje: {}", mensaje);
        log.info("===========================================");

        procesarMensaje(mensaje);
    }

    private void procesarMensaje(String mensaje) {
        try {
            log.info("Procesando mensaje: {}", mensaje);

            if (mensaje.contains("error")) {
                throw new RuntimeException("Error simulado en el procesamiento");
            }

            log.info("Mensaje procesado exitosamente");
        } catch (Exception e) {
            log.error("Error al procesar el mensaje: {}", e.getMessage(), e);
        }
    }
}