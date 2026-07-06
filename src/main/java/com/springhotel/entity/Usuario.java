package com.springhotel.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidad Usuario que representa a un usuario del sistema hotelero.
 * <p>
 * Mapea la tabla {@code usuarios} de la base de datos y almacena las 
 * credenciales de acceso, el perfil y el nombre completo de cada 
 * usuario.
 * <p>
 * 
 * @author Manuel
 * @version 1.0
 */
@Entity
@Table(name="usuarios")
public class Usuario implements Serializable {
	
	/**
	 * Identificador unico de version para la serializacion.
	 * Asegura que el objeto serializado guardado en la sesion coincida con la clase actual.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Identificador unico del usuario generado automaticamente por la
	 * base de datos.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	/**
	 * Nombre de usuario utilizado para iniciar sesion en el sistema
	 * Corresponde a la columna {@code usuario} de la tabla.
	 */
	@Column (name = "nombre_usuario")
	private String nombreUsuario;
	
	/**
     * Contrasena del usuario.
     * Corresponde a la columna {@code password_usuario} de la tabla.
     */
	@Column (name = "password_usuario")
	private String passwordUsuario;
	
	/**
     * Perfil asignado al usuario.
     * Corresponde a la columna {@code perfil} de la tabla.
     */
	@Column (name = "perfil")
	private String perfil;
	
	/**
     * Nombre completo del usuario (nombre y apellidos).
     * Corresponde a la columna {@code nombre_completo} de la tabla.
     */
	@Column (name = "nombre_completo")
	private String nombreCompleto;
	
	/** Constructor vacio (obligatorio para Hibernate) */
	public Usuario (){
		
	}
	
	/** 
	 * Constructor con parametros (sin id, se auto-genera en la BBDD) 
	 * @param nombreUsuario    nombre de usuario para el inicio de sesion
     * @param passwordUsuario  contrasena del usuario
     * @param perfil           perfil del usuario
     * @param nombreCompleto   nombre completo del usuario
	 */
	public Usuario(String nombreUsuario, String passwordUsuario, String perfil, String nombreCompleto) {
		this.nombreUsuario = nombreUsuario;
		this.passwordUsuario = passwordUsuario;
		this.perfil = perfil;
		this.nombreCompleto = nombreCompleto;
	}
	
	/**
     * Devuelve el identificador unico del usuario.
     * @return el {@code id} autogenerado del usuario
     */
	public int getId() {
		return id;
	}
	
	/**
     * Establece el identificador del usuario.
     * @param id el identificador a establecer
     */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
     * Devuelve el nombre de usuario utilizado para el inicio de sesion.
     * @return el nombre de usuario
     */
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	
	/**
     * Establece el nombre de usuario utilizado para el inicio de sesion.
     * @param usuario el nombre de usuario a establecer
     */
	public void setNombreUsuario(String usuario) {
		this.nombreUsuario = usuario;
	}
	
	/**
     * Devuelve la contrasena del usuario.
     * @return la contrasena del usuario
     */
	public String getPasswordUsuario() {
		return passwordUsuario;
	}
	
	/**
     * Establece la contrasena del usuario.
     * @param passwordUsuario la contrasena a establecer
     */
	public void setPasswordUsuario(String passwordUsuario) {
		this.passwordUsuario = passwordUsuario;
	}
	
	/**
     * Devuelve el perfil de permisos del usuario.
     * @return el perfil del usuario
     */
	public String getPerfil() {
		return perfil;
	}
	
	/**
     * Establece el perfil de permisos del usuario.
     * @param perfil el perfil a establecer
     */
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	
	/**
     * Devuelve el nombre completo del usuario.
     * @return el nombre completo del usuario
     */
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	
	/**
     * Establece el nombre completo del usuario.
     * @param nombreCompleto el nombre completo a establecer
     */
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	
	/**
     * Devuelve una cadena de texto del objeto {@code Usuario},
     * incluyendo todos sus campos. 
     * @return cadena con los valores de todos los atributos del usuario
     */
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombreUsuario=" + nombreUsuario + ", passwordUsuario=" + passwordUsuario + ", perfil="
				+ perfil + ", nombreCompleto=" + nombreCompleto + "]";
	}
		
}
