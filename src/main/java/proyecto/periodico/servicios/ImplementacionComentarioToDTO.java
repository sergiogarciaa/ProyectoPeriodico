package proyecto.periodico.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import proyecto.periodico.dao.Comentarios;
import proyecto.periodico.dto.ComentariosDTO;


@Service
public class ImplementacionComentarioToDTO implements InterfazComentarioToDTO{

	@Override
	public ComentariosDTO comentarioToDto(Comentarios comentario) {
	    if (comentario == null) {
	        return null;
	    }
	    
	    ComentariosDTO comentariosDTO = new ComentariosDTO();
	    comentariosDTO.setIdComentario(comentario.getIdComentario());
	    comentariosDTO.setDescComentario(comentario.getDescComentario());
	    comentariosDTO.setFchaPublicacionComentario(comentario.getFchaPublicacionComentario());
	    comentariosDTO.setNoticiaComentario(comentario.getNoticiaComentario());
	    comentariosDTO.setUsuarioComentario(comentario.getUsuarioComentario());
	    // Si necesitas mapear las relaciones con otras entidades, aquí lo harías
	    
	    return comentariosDTO;
	}

	@Override
	public List<ComentariosDTO> listaComentariosToDto(List<Comentarios> listaComentarios) {
		try {
			
			List<ComentariosDTO> listaDto = new ArrayList<>();
			for (Comentarios u : listaComentarios) {
				listaDto.add(this.comentarioToDto(u));
			}
			return listaDto;

		} catch (Exception e) {
			System.out.println(
					"\n[ERROR UsuarioToDtoImpl - listauUsuarioToDto()] - Error al convertir lista de usuario DAO a lista de usuarioDTO (return null): "
							+ e);
		}
		return null;
	}

}
