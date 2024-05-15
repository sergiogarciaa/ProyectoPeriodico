package proyecto.periodico.servicios;

import java.util.List;

import proyecto.periodico.dao.Comentarios;
import proyecto.periodico.dao.Noticia;

public interface InterfazComentario {

	List<Comentarios> obtenerComentariosPorNoticia(Noticia noticia);
	
	Comentarios buscarComentarioPorID(long idComentario);
	
	public void eliminarComentario(long idComentario);
}
