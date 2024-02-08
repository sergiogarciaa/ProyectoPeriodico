package proyecto.periodico.servicios;

import java.util.List;

import proyecto.periodico.dao.Categoria;
import proyecto.periodico.dao.Noticia;
import proyecto.periodico.dao.Usuario;
import proyecto.periodico.dto.NoticiaDTO;
import proyecto.periodico.dto.UsuarioDTO;


public interface InterfazNoticia {

	/**
	 * Busca a un usuario por su identificador asignado en la bbdd
	 * @param id del usuario a buscar
	 * @return El usuario buscado
	 */
	public Noticia buscarNoticiaPorID(long id);
	/**
	 * Busca todos los usuarios registrados
	 * @return la lista de todos los usuariosDTO
	 */
	public List<NoticiaDTO> buscarTodas();
	public String resumirNoticia(String texto);
	public String resumirNoticia2(String texto);
	List<NoticiaDTO> buscarPorCategoria(Long idCategoria);
	
	public String convertToBase64(byte[] bytes);

	public byte[] convertToByteArray(String foto);
	public Noticia obtenerNoticiaMasReciente();
}
