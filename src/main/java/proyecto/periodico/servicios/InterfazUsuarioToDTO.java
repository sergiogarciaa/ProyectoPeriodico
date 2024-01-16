package proyecto.periodico.servicios;

import java.util.List;

import proyecto.periodico.dao.Usuario;
import proyecto.periodico.dto.UsuarioDTO;


public interface InterfazUsuarioToDTO {
	/**
	 * Método que convierte campo a campo un objeto entidad Usuario a usuarioDTO
	 * @param u El usuario a transformar
	 * @return El DTO del usuario
	 */
	public UsuarioDTO usuarioToDto(Usuario u);
	
	/**
	 * Metodo que convierte todos los objetos entidad usuario DAO a una lista UsuarioDTO 
	 * @param listaUsuario
	 * @return
	 */
	public List<UsuarioDTO> listaUsuarioToDto(List<Usuario> listaUsuario);

}
