package com.example.mensajeria.kafka.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MensajeDTO {

    @NotBlank(message = "El mensaje no puede estar vacío")
    private String contenido;

    private LocalDateTime timestamp;

    private String estado;

    public MensajeDTO(String contenido) {
        this.contenido = contenido;
        this.timestamp = LocalDateTime.now();
        this.estado = "ENVIADO";
    }
}