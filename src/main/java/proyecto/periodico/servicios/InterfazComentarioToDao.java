package proyecto.periodico.servicios;

import proyecto.periodico.dao.Comentarios;
import proyecto.periodico.dao.Usuario;
import proyecto.periodico.dto.ComentariosDTO;
import proyecto.periodico.dto.UsuarioDTO;

public interface InterfazComentarioToDao {
	/**
	 * Metodo que convierte campo a campo un objeto UsuarioDTO a DAO
	 * @param ausuarioDTO el objeto usuarioDTO
	 * @return Usuario convertido a DAO
	 */
	public Comentarios comentarioToDao(ComentariosDTO comentariosDTO);
}
