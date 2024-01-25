package proyecto.periodico.servicios;

import proyecto.periodico.dao.Noticia;

public interface InterfazNoticia {
	/**
	 * Busca a un usuario por su identificador asignado en la bbdd
	 * @param id del usuario a buscar
	 * @return El usuario buscado
	 */
	public Noticia buscarNoticiaPorID(long id);
}
