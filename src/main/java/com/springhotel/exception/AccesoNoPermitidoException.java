package com.springhotel.exception;

/**
 * Excepcion lanzada cuando un usuario intenta realizar una operacion
 * para la que no tiene permisos segun su perfil.
 *
 * <p>En SpringHotelApp existen dos perfiles:</p>
 * <ul>
 *   <li><b>RECEPCIONISTA</b>: puede consultar, crear, modificar y eliminar.</li>
 *   <li><b>SUPERVISOR</b>: solo puede consultar y ver el detalle.</li>
 * </ul>
 *
 * <p>Esta excepcion se lanza en los controladores cuando un SUPERVISOR intenta
 * acceder a una operacion restringida (alta, modificacion, eliminacion).</p>
 *
 * <p>Ejemplo de uso en un controlador:</p>
 * <pre>{@code
 * String perfil = (String) session.getAttribute("perfil");
 * if (!"RECEPCIONISTA".equals(perfil)) {
 *     throw new AccesoNoPermitidoException("Operacion no permitida para el perfil: " + perfil);
 * }
 * }</pre>
 *
 * @author  Ana Laura y Manuel
 * @version 1.0
 * @see SpringHotelException
 */
public class AccesoNoPermitidoException extends SpringHotelException {
 
    /**
     * Construye la excepcion con un mensaje descriptivo.
     *
     * @param mensaje descripcion del acceso denegado.
     */
    public AccesoNoPermitidoException(String mensaje) {
        super(mensaje);
    }
 
    /**
     * Construye la excepcion con un mensaje y la causa original.
     *
     * @param mensaje descripcion del acceso denegado.
     * @param causa   excepcion que origino el fallo.
     */
    public AccesoNoPermitidoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
 
