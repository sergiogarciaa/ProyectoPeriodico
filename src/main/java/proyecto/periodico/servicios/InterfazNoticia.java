package proyecto.periodico.servicios;

import proyecto.periodico.dao.Categoria;
import proyecto.periodico.dao.Noticia;
import proyecto.periodico.dao.Usuario;


public interface InterfazNoticia {

	/**
	 * Busca a un usuario por su identificador asignado en la bbdd
	 * @param id del usuario a buscar
	 * @return El usuario buscado
	 */
	public Noticia buscarNoticiaPorID(long id);
	public Noticia noticiaCategoriaN(Usuario usu, Categoria categoria);

}
