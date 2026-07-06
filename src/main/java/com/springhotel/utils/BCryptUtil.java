package com.springhotel.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * Utilidad para cifrado y verificación de contraseñas con BCrypt.
 *
 * @author Ana Laura
 * @version 1.0
 */
// IA: clase generada con ayuda de la IA (Claude)
public class BCryptUtil {
	
	/**
     * Genera el hash BCrypt de una contraseña en texto plano.
     *
     * @param passwordPlano contraseña sin cifrar
     * @return hash BCrypt listo para almacenar en base de datos
     */
    public static String hashPassword(String passwordPlano) {
        return BCrypt.withDefaults().hashToString(12, passwordPlano.toCharArray());
    }

    /**
     * Compara una contraseña en texto plano con un hash almacenado.
     *
     * @param passwordPlano contraseña introducida por el usuario
     * @param hash          hash almacenado en base de datos
     * @return {@code true} si coinciden, {@code false} en caso contrario
     */
    public static boolean verificarPassword(String passwordPlano, String hash) {
        BCrypt.Result result = BCrypt.verifyer().verify(passwordPlano.toCharArray(), hash);
        return result.verified;
    }
}
