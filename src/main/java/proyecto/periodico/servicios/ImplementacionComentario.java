package proyecto.periodico.servicios;

import java.util.List;

import org.springframework.stereotype.Service;

import proyecto.periodico.dao.Comentarios;
import proyecto.periodico.dao.Noticia;

@Service
public class ImplementacionComentario implements InterfazComentario{

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

}
