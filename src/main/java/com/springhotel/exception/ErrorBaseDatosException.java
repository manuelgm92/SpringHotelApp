package com.springhotel.exception;


/**
* Excepcion lanzada cuando se produce un error en el acceso a la base de datos.
*
* <p>Se usa en las implementaciones de los DAOs para envolver cualquier
* excepcion de Hibernate ({@link org.hibernate.HibernateException},
* {@link javax.persistence.PersistenceException}, etc.) y relanzarla
* como una excepcion de aplicacion con un mensaje claro.</p>
*
* <p>Ejemplo de uso en un DAO:</p>
* <pre>{@code
* try {
*     session.save(habitacion);
* } catch (HibernateException e) {
*     throw new ErrorBaseDatosException("Error al guardar la habitacion", e);
* }
* }</pre>
*
* @author  Ana Laura y Manuel
* @version 1.0
* @see SpringHotelException
*/
public class ErrorBaseDatosException extends SpringHotelException {

   /**
    * Construye la excepcion con un mensaje descriptivo.
    *
    * @param mensaje descripcion del error de base de datos.
    */
   public ErrorBaseDatosException(String mensaje) {
       super(mensaje);
   }

   /**
    * Construye la excepcion con un mensaje y la causa original.
    *
    * @param mensaje descripcion del error de base de datos.
    * @param causa   excepcion de Hibernate u ORM que origino el fallo.
    */
   public ErrorBaseDatosException(String mensaje, Throwable causa) {
       super(mensaje, causa);
   }
}
