package com.springhotel.exception;

/**
 * Excepcion lanzada cuando un argumento introducido en un formulario no es valido.
 *
 * <p>Esta excepcion se lanza en los controladores cuando un SUPERVISOR intenta
 * acceder a una operacion restringida (alta, modificacion, eliminacion).</p>
 *
 * <p>Esta excepcion se utiliza en los controladores o servicios cuando los datos
 * proporcionados por el usuario no cumplen las reglas de validacion definidas
 * (campos vacios, formato incorrecto, valores fuera de rango, etc).</p>
 * 
 * <p>Ejemplo de uso en un controlador:</p>
 * <pre>{@code
 * if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
 *     throw new ArgumentoNoValidoException("El nombre de usuario es obligatorio");
 * }
 * }</pre>
 *
 * @author  Ana Laura y Manuel
 * @version 1.0
 * @see SpringHotelException
 */
public class ArgumentoNoValidoException extends SpringHotelException {
	/**
     * Construye la excepcion con un mensaje descriptivo.
     *
     * @param mensaje descripcion de argumento no válido.
     */
    public ArgumentoNoValidoException(String mensaje) {
        super(mensaje);
    }

	/**
     * Construye la excepcion con un mensaje y la causa original.
     *
     * @param mensaje descripcion de argumento no válido.
     * @param causa   excepcion que origino el fallo.
     */
    public ArgumentoNoValidoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
