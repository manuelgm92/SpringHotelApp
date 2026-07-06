package com.springhotel.exception;

/**
 * Excepcion lanzada cuando no se encuentra una entidad en la base de datos.
 *
 * <p>Se utiliza en los DAOs cuando una consulta por identificador no devuelve
 * ningun resultado. Por ejemplo, al buscar una habitacion o una incidencia
 * con un {@code id} que no existe en la tabla correspondiente.</p>
 *
 * <p>Los controladores pueden capturar esta excepcion para mostrar al usuario
 * una pagina de error con un mensaje comprensible en lugar de un error HTTP 500.</p>
 *
 * <p>Ejemplo de uso en un DAO:</p>
 * <pre>{@code
 * Habitacion habitacion = session.get(Habitacion.class, id);
 * if (habitacion == null) {
 *     throw new EntidadNoEncontradaException("Habitacion no encontrada con id: " + id);
 * }
 * return habitacion;
 * }</pre>
 *
 * @author  Ana Laura y Manuel
 * @version 1.0
 * @see SpringHotelException
 */
public class EntidadNoEncontradaException extends SpringHotelException {
 
    /**
     * Construye la excepcion con un mensaje descriptivo.
     *
     * @param mensaje descripcion del recurso que no se encontro
     *                (por ejemplo, "Habitacion no encontrada con id: 5").
     */
    public EntidadNoEncontradaException(String mensaje) {
        super(mensaje);
    }
 
    /**
     * Construye la excepcion con un mensaje y la causa original.
     *
     * @param mensaje descripcion del recurso que no se encontro.
     * @param causa   excepcion que origino el fallo.
     */
    public EntidadNoEncontradaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
 
