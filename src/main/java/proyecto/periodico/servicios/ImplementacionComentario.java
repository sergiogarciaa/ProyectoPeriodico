package proyecto.periodico.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyecto.periodico.dao.Comentarios;
import proyecto.periodico.dao.Noticia;
import proyecto.periodico.repositorios.comentariosRepositorio;

@Service
public class ImplementacionComentario implements InterfazComentario{

	@Autowired
	private comentariosRepositorio Crepositorio;

	public List<Comentarios> obtenerComentariosPorNoticia(Noticia noticia) {
		// Verifica que la noticia no sea nula
	    if (noticia == null) {
	        throw new IllegalArgumentException("La noticia proporcionada es nula");
	    }

	    // Obtiene los comentarios asociados a la noticia
	    List<Comentarios> comentarios = noticia.getNoticiaComentario();

	    // Retorna la lista de comentarios
	    return comentarios;
	}

	@Override
	public Comentarios buscarComentarioPorID(long idComentario) {
		 return Crepositorio.findById(idComentario).orElse(null);
	}
	
	public void eliminarComentario(long idComentario) {
	    // Buscar el comentario por su ID
	    Comentarios comentario = Crepositorio.findById(idComentario).orElse(null);

	    // Verificar si el comentario existe
	    if (comentario != null) {
	        // Eliminar el comentario
	    	Crepositorio.delete(comentario);
	    }
	}


}
