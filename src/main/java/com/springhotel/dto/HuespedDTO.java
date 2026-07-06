package com.springhotel.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.springhotel.entity.Huesped;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO utilizado para transferir los datos de un huésped entre capas sin exponer
 * directamente la entidad {@link Huesped} en los controladores y vistas.
 *
 * @author Ana Laura
 * @version 1.0
 */
@Getter
@Setter
public class HuespedDTO {

    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombreHuesped;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 150, message = "Los apellidos no pueden superar los 150 caracteres")
    private String apellidosHuesped;

    @NotBlank(message = "El DNI o pasaporte es obligatorio")
    @Size(min = 9, message = "Mínimo debe tener 9 caracteres")
    private String dniPasaporte;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(max = 20, message = "El número de teléfono no puede superar los 20 caracteres")
    private String telefonoHuesped;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    private String email;

    private String direccion;
}
