package com.springhotel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Entidad que representa a un huesped registrado en el sistema hotelero.
 * <p>
 * Se mapea a la tabla {@code huespedes} de la base de datos. Contiene la
 * informacion personal y de contacto del huesped, asi como restricciones
 * de validacion sobre sus campos.
 * </p>
 * <p>
 * Los campos {@code dniPasaporte} y {@code email} estan marcados como
 * {@code unique} a nivel de base de datos, por lo que no puede haber dos
 * huespedes con el mismo DNI/pasaporte o el mismo email.
 * </p>
 * <p>
 * Los campos {@code nombreHuesped}, {@code apellidosHuesped} y
 * {@code dniPasaporte} se almacenan siempre en mayusculas, conversion
 * que se realiza automaticamente en sus respectivos setters.
 * </p>
 *
 * @author Manuel
 * @version 1.0
 */
// IA: javadoc generado con ayuda de IA(Claude)

@Entity
@Table(name="huespedes")
public class Huesped {
	
	 /**
     * Identificador unico del huesped, generado automaticamente
     * por la base de datos mediante estrategia de autoincremento.
     */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	/**
     * Nombre del huesped. Campo obligatorio con un maximo de 100 caracteres.
     * Se almacena siempre en mayusculas.
     */
	@NotBlank(message = "El nombre es obligatorio")
	@Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
	@Column(name = "nombre")
	private String nombreHuesped;
	
	/**
     * Apellidos del huesped. Campo obligatorio con un maximo de 150 caracteres.
     * Se almacena siempre en mayusculas.
     */
	@NotBlank(message = "Los apellidos son obligatorios")
	@Size(max = 150, message = "Los apellidos no pueden superar los 150 caracteres")
	@Column(name = "apellidos")
	private String apellidosHuesped;
	
	/**
     * DNI o numero de pasaporte del huesped. Campo obligatorio con un minimo
     * de 9 caracteres. Debe ser unico en la base de datos.
     * Se almacena siempre en mayusculas.
     */
	@NotBlank(message = "El DNI o pasaporte es obligatorio")
	@Size(min = 9, message = "Mínimo debe tener 9 caracteres")
	@Column(name = "dni_pasaporte",  unique = true)
	private String dniPasaporte;
	
	/**
     * Numero de telefono del huesped. Campo obligatorio con un maximo
     * de 20 caracteres para admitir prefijos internacionales.
     */
	@NotBlank(message = "El teléfono es obligatorio")
	@Size(max = 20, message = "El número de teléfono no puede superar los 20 caracteres")
	@Column(name = "telefono")
	private String telefonoHuesped;
	
	 /**
     * Direccion de correo electronico del huesped. Campo obligatorio que debe
     * tener un formato de email valido. Debe ser unico en la base de datos.
     */
	@NotBlank(message = "El email es obligatorio")
	@Email(message = "El email no tiene un formato válido")
	@Column(name = "email", unique = true)
	private String email;
	
	/**
     * Direccion postal del huesped. Campo opcional, sin restricciones
     * de formato ni longitud definidas a nivel de entidad.
     */
	@Column(name = "direccion")
	private String direccion;
	
	 /**
     * Constructor por defecto requerido por JPA para la instanciacion
     * de entidades mediante reflexion.
     */
	public Huesped() {
		
	}
	
	/**
     * Constructor con todos los campos para crear un huesped con datos completos.
     *
     * @param id               identificador unico del huesped
     * @param nombreHuesped    nombre del huesped
     * @param apellidosHuesped apellidos del huesped
     * @param dniPasaporte     DNI o numero de pasaporte del huesped
     * @param telefonoHuesped  numero de telefono del huesped
     * @param email            direccion de correo electronico del huesped
     * @param direccion        direccion postal del huesped
     */
	public Huesped(Integer id, String nombreHuesped, String apellidosHuesped, String dniPasaporte, String telefonoHuesped,
			String email, String direccion) {
		this.id = id;
		this.nombreHuesped = nombreHuesped;
		this.apellidosHuesped = apellidosHuesped;
		this.dniPasaporte = dniPasaporte;
		this.telefonoHuesped = telefonoHuesped;
		this.email = email;
		this.direccion = direccion;
	}
	
	/**
     * Obtiene el identificador unico del huesped.
     *
     * @return identificador del huesped, o {@code null} si aun no ha sido persistido
     */
	public Integer getId() {
		return id;
	}
	
	/**
     * Establece el identificador unico del huesped.
     *
     * @param id identificador a asignar
     */
	public void setId(Integer id) {
		this.id = id;
	}
	
	 /**
     * Obtiene el nombre del huesped.
     *
     * @return nombre del huesped en mayusculas, o {@code null} si no esta definido
     */
	public String getNombreHuesped() {
		return nombreHuesped;
	}
	
	/**
     * Establece el nombre del huesped convirtiendolo automaticamente a mayusculas.
     * Si el valor recibido es {@code null}, se almacena {@code null}.
     *
     * @param nombreHuesped nombre del huesped; puede ser {@code null}
     */
	public void setNombreHuesped(String nombreHuesped) {
		if (nombreHuesped != null) {
			this.nombreHuesped = nombreHuesped.toUpperCase();
		} else {
			this.nombreHuesped = null;
		}
	}
	
	 /**
     * Obtiene los apellidos del huesped.
     *
     * @return apellidos del huesped en mayusculas, o {@code null} si no estan definidos
     */
	public String getApellidosHuesped() {
		return apellidosHuesped;
	}
	
	 /**
     * Establece los apellidos del huesped convirtiendolos automaticamente a mayusculas.
     * Si el valor recibido es {@code null}, se almacena {@code null}.
     *
     * @param apellidosHuesped apellidos del huesped; puede ser {@code null}
     */
	public void setApellidosHuesped(String apellidosHuesped) {
		if (apellidosHuesped != null) {
			this.apellidosHuesped = apellidosHuesped.toUpperCase();
		} else {
			this.apellidosHuesped = null;
		}
	}
	
	 /**
     * Obtiene el DNI o numero de pasaporte del huesped.
     *
     * @return DNI o pasaporte en mayusculas, o {@code null} si no esta definido
     */
	public String getDniPasaporte() {
		return dniPasaporte;
	}
	
	 /**
     * Establece el DNI o numero de pasaporte del huesped convirtiendolo
     * automaticamente a mayusculas. Si el valor recibido es {@code null},
     * se almacena {@code null}.
     *
     * @param dniPasaporte DNI o pasaporte del huesped; puede ser {@code null}
     */
	public void setDniPasaporte(String dniPasaporte) {
		if (dniPasaporte != null) {
			this.dniPasaporte = dniPasaporte.toUpperCase();
		} else {
			this.dniPasaporte = null;
		}
	}
	
	/**
     * Obtiene el numero de telefono del huesped.
     *
     * @return numero de telefono del huesped, o {@code null} si no esta definido
     */
	public String getTelefonoHuesped() {
		return telefonoHuesped;
	}
	
	/**
     * Establece el numero de telefono del huesped.
     *
     * @param telefonoHuesped numero de telefono a asignar
     */
	public void setTelefonoHuesped(String telefonoHuesped) {
		this.telefonoHuesped = telefonoHuesped;
	}
	
	/**
     * Obtiene el email del huesped.
     *
     * @return direccion de correo electronico del huesped,
     *         o {@code null} si no esta definida
     */
	public String getEmail() {
		return email;
	}
	
	/**
     * Establece la direccion de correo electronico del huesped.
     *
     * @param email direccion de correo electronico a asignar
     */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
     * Obtiene la direccion postal del huesped.
     *
     * @return direccion postal del huesped, o {@code null} si no esta definida
     */
	public String getDireccion() {
		return direccion;
	}
	
	/**
     * Establece la direccion postal del huesped.
     *
     * @param direccion direccion postal a asignar
     */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	/**
     * Devuelve una representacion en cadena de texto del objeto {@code Huesped}
     * con todos sus campos, util para depuracion y registro de logs.
     *
     * @return cadena con todos los campos del huesped
     */
	@Override
	public String toString() {
		return "Huesped [id=" + id + ", nombreHuesped=" + nombreHuesped + ", apellidosHuesped=" + apellidosHuesped
				+ ", dniPasaporte=" + dniPasaporte + ", telefonoHuesped=" + telefonoHuesped + ", email=" + email
				+ ", direccion=" + direccion + "]";
	}	
	
}
