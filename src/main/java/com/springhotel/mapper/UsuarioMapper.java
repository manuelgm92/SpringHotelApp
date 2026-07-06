package com.springhotel.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.springhotel.dto.UsuarioDTO;
import com.springhotel.entity.Usuario;

/**
 * Mapper que convierte entre la entidad {@link Usuario} y su DTO {@link UsuarioDTO}.
 *
 * @author Ana Laura
 * @version 1.0
 */
public class UsuarioMapper {

	/**
	 * Convierte una entidad {@link Usuario} en su DTO {@link UsuarioDTO} correspondiente.
	 *
	 * @param usuario entidad usuario a convertir
	 * @return objeto {@link UsuarioDTO} con los datos mapeados, o {@code null} si el parámetro es nulo
	 */
    public static UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombreUsuario(usuario.getNombreUsuario());
        dto.setPasswordUsuario(usuario.getPasswordUsuario());
        dto.setPerfil(usuario.getPerfil());
        dto.setNombreCompleto(usuario.getNombreCompleto());
        return dto;
    }

	/**
	 * Convierte un {@link UsuarioDTO} en su entidad {@link Usuario} correspondiente.
	 *
	 * @param dto objeto DTO con los datos del usuario
	 * @return entidad {@link Usuario} con los datos mapeados, o {@code null} si el parámetro es nulo
	 */
    public static Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) {
            return null;
        }

        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setNombreUsuario(dto.getNombreUsuario());
        usuario.setPasswordUsuario(dto.getPasswordUsuario());
        usuario.setPerfil(dto.getPerfil());
        usuario.setNombreCompleto(dto.getNombreCompleto());
        return usuario;
    }

	/**
	 * Convierte una lista de entidades {@link Usuario} en una lista de DTOs {@link UsuarioDTO}.
	 *
	 * @param usuarios lista de entidades a convertir
	 * @return lista de DTOs mapeados, o {@code null} si la lista es nula
	 */
    public static List<UsuarioDTO> toDTOList(List<Usuario> usuarios) {
        if (usuarios == null) {
            return null;
        }
        return usuarios.stream().map(UsuarioMapper::toDTO).collect(Collectors.toList());
    }
}
