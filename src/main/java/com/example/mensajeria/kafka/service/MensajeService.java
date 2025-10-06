package com.example.mensajeria.kafka.service;

import com.example.mensajeria.kafka.dto.MensajeDTO;

public interface MensajeService {

    MensajeDTO enviarMensaje(MensajeDTO mensajeDTO);

    MensajeDTO enviarMensajePredeterminado();

}