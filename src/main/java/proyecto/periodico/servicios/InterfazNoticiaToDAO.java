package proyecto.periodico.servicios;

import java.util.List;

import proyecto.periodico.dao.Categoria;
import proyecto.periodico.dao.Noticia;
import proyecto.periodico.dao.Usuario;
import proyecto.periodico.dto.NoticiaDTO;

public interface InterfazNoticiaToDAO {
	/**
	 * Metodo que convierte campo a campo un objeto UsuarioDTO a DAO
	 * @param ausuarioDTO el objeto usuarioDTO
	 * @return Usuario convertido a DAO
	 */
	public Noticia noticiaToDao(NoticiaDTO NoticiaDTO, Usuario usuario, Categoria categoria);
	
	/**
	 * Metodo que convierte toda una lista de objetos UsuarioDTO a lista de DAOs
	 * @param listaUsuarioDTO lista cargadas de objetos usuarioDTO
	 * @return Lista de usuarios DAO
	 */
	public List<Noticia> listaNoticiaToDao(List<NoticiaDTO>listaNoticiaDTO);

}
