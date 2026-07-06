package com.springhotel.exception;

/**
 * Excepcion lanzada cuando las credenciales de acceso son incorrectas.
 *
 * <p>Se utiliza en el DAO de usuarios cuando el usuario o la contrasena
 * proporcionados durante el login no coinciden con ningun registro de la base
 * de datos.</p>
 *
 * <p>El controlador de login captura esta excepcion para mostrar un mensaje
 * de error en el formulario sin revelar si fue el usuario o la contrasena
 * lo que fallo (buena practica de seguridad).</p>
 *
 * <p>Ejemplo de uso en el DAO de usuarios:</p>
 * <pre>{@code
 * Usuario usuario = obtenerPorCredenciales(nombreUsuario, password);
 * if (usuario == null) {
 *     throw new CredencialesInvalidasException("Usuario o contrasena incorrectos");
 * }
 * }</pre>
 *
 * @author  Ana Laura y Manuel
 * @version 1.0
 * @see SpringHotelException
 */
public class CredencialesInvalidasException extends SpringHotelException {
 
    /**
     * Construye la excepcion con un mensaje descriptivo.
     *
     * @param mensaje descripcion del error de autenticacion.
     */
    public CredencialesInvalidasException(String mensaje) {
        super(mensaje);
    }
 
    /**
     * Construye la excepcion con un mensaje y la causa original.
     *
     * @param mensaje descripcion del error de autenticacion.
     * @param causa   excepcion que origino el fallo.
     */
    public CredencialesInvalidasException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
