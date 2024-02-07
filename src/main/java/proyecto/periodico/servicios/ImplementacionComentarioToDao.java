package proyecto.periodico.servicios;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import proyecto.periodico.dao.Comentarios;
import proyecto.periodico.dto.ComentariosDTO;
import proyecto.periodico.dto.UsuarioDTO;

@Service
public class ImplementacionComentarioToDao implements InterfazComentarioToDao {

	@Override
	public Comentarios comentarioToDao(ComentariosDTO comentariosDTO) {
		 if (comentariosDTO == null) {
	            return null;
	        }
	        
	        Comentarios comentario = new Comentarios();
	        comentario.setIdComentario(comentariosDTO.getIdComentario());
	        comentario.setDescComentario(comentariosDTO.getDescComentario());
	        System.out.println(comentariosDTO.getDescComentario());
	        comentario.setFchaPublicacionComentario(comentariosDTO.getFchaPublicacionComentario());
	        // Verificar si la lista noticiaComentario en el DTO es null
	        if (comentariosDTO.getNoticiaComentario() != null) {
	            comentario.setNoticiaComentario(comentariosDTO.getNoticiaComentario());
	        } else {
	            // Si es null, inicializarla como una lista vacía
	            comentario.setNoticiaComentario(new ArrayList<>());
	            System.out.println("Inicializada la lista de noticias");
	        }
	        if (comentariosDTO.getUsuarioComentario() != null) {
	            comentario.setUsuarioComentario(comentariosDTO.getUsuarioComentario());
	        } else {
	            // Si es null, inicializarla como una lista vacía
	            comentario.setUsuarioComentario(new ArrayList<>());
	            System.out.println("Inicializada la lista de usuuarios");
	        }
	        // Si necesitas mapear las relaciones con otras entidades, aquí lo harías
	        
	        return comentario;
	}

}
