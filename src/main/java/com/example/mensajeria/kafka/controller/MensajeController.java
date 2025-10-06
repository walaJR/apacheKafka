package com.example.mensajeria.kafka.controller;

import com.example.mensajeria.kafka.dto.MensajeDTO;
import com.example.mensajeria.kafka.service.MensajeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/mensajes")
@RequiredArgsConstructor
@Slf4j
public class MensajeController {

    private final MensajeService mensajeService;

    // Endpoint con mensaje predeterminado
    @PostMapping("/enviar/predeterminado")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<MensajeDTO> enviarMensajePredeterminado() {
        log.info("Controller: Recibida petición para enviar mensaje predeterminado");

        try {
            MensajeDTO mensajeDTO = mensajeService.enviarMensajePredeterminado();

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(mensajeDTO);

        } catch (Exception e) {
            log.error("Controller: Error al enviar mensaje predeterminado", e);
            throw new RuntimeException("Error al enviar mensaje predeterminado", e);
        }
    }

    // Endpoint con mensaje personalizado
    @PostMapping("/enviar")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<MensajeDTO> enviarMensaje(@Valid @RequestBody MensajeDTO mensajeDTO) {
        log.info("Controller: Recibida petición para enviar mensaje: {}", mensajeDTO.getContenido());

        try {
            MensajeDTO mensajeEnviado = mensajeService.enviarMensaje(mensajeDTO);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(mensajeEnviado);

        } catch (Exception e) {
            log.error("Controller: Error al enviar mensaje", e);
            throw new RuntimeException("Error al enviar mensaje", e);
        }
    }

    // Endpoint de health check

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Servicio de mensajería Kafka funcionando correctamente");
    }
}