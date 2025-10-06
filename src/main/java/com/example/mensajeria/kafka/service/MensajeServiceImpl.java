package com.example.mensajeria.kafka.service;

import com.example.mensajeria.kafka.dto.MensajeDTO;
import com.example.mensajeria.kafka.producer.KafkaProducer;
import com.example.mensajeria.kafka.service.MensajeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class MensajeServiceImpl implements MensajeService {

    private final KafkaProducer kafkaProducer;

    private static final String MENSAJE_PREDETERMINADO = "hola kafka desde Java";

    @Override
    public MensajeDTO enviarMensaje(MensajeDTO mensajeDTO) {
        log.info("Servicio: Preparando envío de mensaje");

        if (mensajeDTO.getTimestamp() == null) {
            mensajeDTO.setTimestamp(LocalDateTime.now());
        }

        if (mensajeDTO.getEstado() == null) {
            mensajeDTO.setEstado("ENVIADO");
        }

        kafkaProducer.enviarMensaje(mensajeDTO.getContenido());

        log.info("Servicio: Mensaje enviado correctamente");

        return mensajeDTO;
    }

    @Override
    public MensajeDTO enviarMensajePredeterminado() {
        log.info("Servicio: Enviando mensaje predeterminado");

        MensajeDTO mensajeDTO = new MensajeDTO(MENSAJE_PREDETERMINADO);

        kafkaProducer.enviarMensaje(MENSAJE_PREDETERMINADO);

        log.info("Servicio: Mensaje predeterminado enviado");

        return mensajeDTO;
    }

}