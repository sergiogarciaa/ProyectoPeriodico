package proyecto.periodico.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyecto.periodico.dao.Noticia;
import proyecto.periodico.dao.Usuario;
import proyecto.periodico.dto.NoticiaDTO;
import proyecto.periodico.dto.UsuarioDTO;

@Service
public class ImplementacionNoticiaToDTO implements InterfazNoticiaToDTO {

	
    @Override
    public NoticiaDTO noticiaToDto(Noticia noticia) {
        try {
        	ImplementacionNoticia iPac = new ImplementacionNoticia();
            NoticiaDTO dto = new NoticiaDTO();
            dto.setIdNoticia(noticia.getIdNoticia());
            dto.setTituloNoticia(noticia.getTituloNoticia());
            dto.setDescNoticia(noticia.getDescNoticia());
            dto.setFoto(iPac.convertToBase64(noticia.getFoto()));
            dto.setEstado_suscripcion(noticia.getEstado_suscripcion());
            dto.setFchaPublicacionMostrarWeb(noticia.getFchaPublicacion());
            dto.setIdCategoria(noticia.getNoticiaCategoria());
            dto.setIdUsuario(noticia.getUsuario());
            dto.setComentarios(noticia.getNoticiaComentario());

            return dto;
        } catch (Exception e) {
            System.out.println("[ERROR ImplementacionNoticiaToDto - noticiaToDto()] - Error al convertir noticia DAO a noticiaDTO (return null): " + e);
            return null;
        }
    }

	@Override
	public List<NoticiaDTO> listaNoticiasToDto(List<Noticia> listaNoticia){
		try {
				
			List<NoticiaDTO> listaDto = new ArrayList<>();
			for (Noticia n : listaNoticia) {
				listaDto.add(this.noticiaToDto(n));
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
