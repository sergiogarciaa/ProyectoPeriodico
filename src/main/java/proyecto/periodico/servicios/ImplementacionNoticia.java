package proyecto.periodico.servicios;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import proyecto.periodico.dao.Categoria;
import proyecto.periodico.dao.Noticia;
import proyecto.periodico.dao.Usuario;
import proyecto.periodico.dto.NoticiaDTO;
import proyecto.periodico.dto.UsuarioDTO;
import proyecto.periodico.repositorios.noticiaRepositorio;

@Service
@Transactional
public class ImplementacionNoticia implements InterfazNoticia  {
	
	
	@Autowired
	private noticiaRepositorio Nrepositorio;
	
	@Autowired
	private InterfazNoticiaToDTO toDto;

	
	@Override
	public Noticia buscarNoticiaPorID(long id) {
		return Nrepositorio.findById(id).orElse(null);
	}

	@Override
	public List<NoticiaDTO> buscarTodas() {
		return toDto.listaNoticiasToDto(Nrepositorio.findAll());
	}
	
	/**
	 * Método que ejecuta la creación de una noticia.
	 */
	@Override
	public NoticiaDTO noticiaCategoriaN(Usuario usu, Categoria categoria) {
		try {
			//// Crear una noticia
			Noticia noticia = new Noticia();
			noticia.setTituloNoticia("Caaa");
			noticia.setDescNoticia("La politica en españaaaaaa");
			noticia.setFchaPublicacion(Calendar.getInstance());
			noticia.setUsuario(usu);	
			noticia.setNoticiaCategoria(categoria);
			
			NoticiaDTO noticiaDTO = toDto.noticiaToDto(noticia);
			Nrepositorio.save(noticia);

			return noticiaDTO;
			// Guardar en el repositorio o realizar otras operaciones necesarias...
			
		} catch (Exception e) {
			System.out.println("[IMPL-Noticia][InicializarNoticia] " + e.getMessage());
			return null;
		}
		
	}

}
