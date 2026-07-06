package com.springhotel.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO utilizado para transferir los datos de un usuario entre la capa de
 * presentacion y la capa de servicio, sin exponer la entidad completa.
 *
 * @author Ana Laura
 * @version 1.0
 */
@Getter
@Setter
public class UsuarioDTO {

    private int id;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String nombreUsuario;

    @NotBlank(message = "La contraseña es obligatoria")
    private String passwordUsuario;

    @NotBlank(message = "El perfil es obligatorio")
    private String perfil;

    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(max = 255, message = "El nombre completo no puede superar los 255 caracteres")
    private String nombreCompleto;
}
