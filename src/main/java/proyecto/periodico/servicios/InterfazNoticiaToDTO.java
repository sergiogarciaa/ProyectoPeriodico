package proyecto.periodico.servicios;

import java.util.List;

import proyecto.periodico.dao.Noticia;
import proyecto.periodico.dao.Usuario;
import proyecto.periodico.dto.NoticiaDTO;
import proyecto.periodico.dto.UsuarioDTO;

public interface InterfazNoticiaToDTO {

	NoticiaDTO noticiaToDto(Noticia noticia);
	public List<NoticiaDTO> listaNoticiasToDto(List<Noticia> listaNoticia);
}
