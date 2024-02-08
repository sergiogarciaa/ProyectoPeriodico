package proyecto.periodico.servicios;

import java.util.List;

import proyecto.periodico.dao.Comentarios;
import proyecto.periodico.dto.ComentariosDTO;

public interface InterfazComentarioToDTO {
	
	ComentariosDTO comentarioToDto(Comentarios comentarios);
	
	public List<ComentariosDTO> listaComentariosToDto(List<Comentarios> listaComentarios);
}
