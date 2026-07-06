package com.springhotel.exception;
 
/**
 * Excepcion base de SpringHotelApp.
 *
 * <p>Todas las excepciones personalizadas del proyecto extienden esta clase.
 * Esto permite capturar cualquier error de la aplicacion con un unico bloque
 * {@code catch (SpringHotelException e)} cuando sea necesario, sin perder
 * la posibilidad de distinguir el tipo concreto de error.</p>
 *
 * <p>Al extender {@link RuntimeException}, las excepciones de la aplicacion son
 * <em>unchecked</em>: no obligan a los metodos intermedios a declararlas con
 * {@code throws}, lo que simplifica la firma de controladores y servicios.</p>
 *
 * @author  Ana Laura y Manuel
 * @version 1.0
 */
public class SpringHotelException extends RuntimeException {
 
    /**
     * Construye una excepcion con un mensaje descriptivo.
     *
     * @param mensaje descripcion legible del error ocurrido.
     */
    public SpringHotelException(String mensaje) {
        super(mensaje);
    }
 
    /**
     * Construye una excepcion con un mensaje y la causa original.
     *
     * <p>Usar esta variante cuando se captura una excepcion de bajo nivel
     * (por ejemplo, una {@link Exception} de Hibernate) y se quiere
     * relanzar como excepcion de aplicacion sin perder el rastro original.</p>
     *
     * @param mensaje descripcion legible del error ocurrido.
     * @param causa   excepcion original que provoco este error.
     */
    public SpringHotelException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
 