package com.minimarket.cliente_service.client;

import com.minimarket.cliente_service.dto.NotificacionRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="notificacion-service",url="${notificacion.service.url}")
public interface NotificacionClient {
    @PostMapping("api/notificaciones")
    void enviarNotificacion(@RequestBody NotificacionRequestDTO dto);
}
