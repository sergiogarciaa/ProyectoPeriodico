package proyecto.periodico.servicios;

import java.util.Arrays;
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

			return null;
			// Guardar en el repositorio o realizar otras operaciones necesarias...
			
		} catch (Exception e) {
			System.out.println("[IMPL-Noticia][InicializarNoticia] " + e.getMessage());
			return null;
		}
		
	}
	
	// Método para calcular el resumen de la noticia
	public String calcularResumen(String descNoticia, int palabras) {
            return null;
	}

}