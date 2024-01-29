package proyecto.periodico.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import proyecto.periodico.dao.Categoria;
import proyecto.periodico.dao.Noticia;
import proyecto.periodico.dao.Usuario;
import proyecto.periodico.repositorios.noticiaRepositorio;

@Service
@Transactional
public class ImplementacionNoticia implements InterfazNoticia  {
	
	
	@Autowired
	private noticiaRepositorio Nrepositorio;

	
	@Override
	public Noticia buscarNoticiaPorID(long id) {
		return Nrepositorio.findById(id).orElse(null);
	}

	/**
	 * Método que ejecuta la creación de una noticia.
	 */
	@Override
	public Noticia noticiaCategoriaN(Usuario usu, Categoria categoria) {
		try {

			System.out.println("ES EN LA IMPL ----- " + usu);
			//// Crear una noticia
			Noticia noticia = new Noticia();
			noticia.setTituloNoticia("Caaa");
			noticia.setDescNoticia("La politica en españaaaaaa");
			noticia.setUsuario(usu);	
			noticia.setNoticiaCategoria(categoria);
			Nrepositorio.save(noticia);
			
			System.out.println("GUARDADO TODOOO");
			return noticia;
			// Guardar en el repositorio o realizar otras operaciones necesarias...
			
		} catch (Exception e) {
			System.out.println("[IMPL-Noticia][InicializarNoticia] " + e.getMessage());
			return null;
		}
		
	}

}
