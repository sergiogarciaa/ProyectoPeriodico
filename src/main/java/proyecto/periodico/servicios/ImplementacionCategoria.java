package proyecto.periodico.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import proyecto.periodico.dao.Categoria;
import proyecto.periodico.dao.Usuario;
import proyecto.periodico.dto.CategoriaDTO;
import proyecto.periodico.dto.NoticiaDTO;
import proyecto.periodico.repositorios.categoriaRepositorio;
import proyecto.periodico.repositorios.noticiaRepositorio;
import proyecto.periodico.repositorios.usuarioRepositorio;

@Service
@Transactional
public class ImplementacionCategoria implements InterfazCategoria{
	@Autowired
	private noticiaRepositorio Nrepositorio;
	@Autowired
	private categoriaRepositorio Crepositorio;
	@Autowired
	private usuarioRepositorio Urepositorio;
	
	
	@Autowired
	private InterfazCategoriasToDTO toDto;
	
	/*
	 * public Categoria crearCategoria() { Categoria categoria = new Categoria();
	 * categoria.setTipoCategoria("Deporte"); categoria.setDescCategoria("Futbol");
	 * System.out.println(categoria); Crepositorio.save(categoria);
	 * toDto.listaCategoriaToDto(Crepositorio.findAll()); return categoria; }
	 */

	@Override
	public List<CategoriaDTO> buscarTodas() {
		return toDto.listaCategoriaToDto(Crepositorio.findAll());
	}


	@Override
	public Categoria buscarPorId(Categoria idCategoria) {
		return Crepositorio.findById(idCategoria.getIdCategoria()).orElse(null);
	}


	@Override
	public Categoria buscarPorId(long idCategoria) {
		// TODO Auto-generated method stub
		return Crepositorio.findById(idCategoria).orElse(null);
	}
	/**
	 * Método que ejecuta la creación de un usuario administrador con su rol de
	 * administrador.
	 */
	private void inicializarCategorias() {
		try {
			// Comprueba si ya existe un usuario admin
			if (!Crepositorio.existsByTipoCategoria("Política")) {
				// Si no existe, crea un nuevo usuario con rol de administrador
				Categoria categoria1 = new Categoria();
				categoria1.setTipoCategoria("Política");
				categoria1.setDescCategoria("Esta categoría trata sobre política");
				Crepositorio.save(categoria1);
				toDto.categoriaToDTO(categoria1);	
			}
		} catch (Exception e) {
			System.out.println("[IMPL-Categoria][inicializarCategorias] " + e.getMessage());
		}
		try {
			// Comprueba si ya existe un usuario admin
			if (!Crepositorio.existsByTipoCategoria("Deporte")) {
				// Si no existe, crea un nuevo usuario con rol de administrador
				Categoria categoria2 = new Categoria();
				categoria2.setTipoCategoria("Deporte");
				categoria2.setDescCategoria("Esta categoría trata sobre deporte");
				Crepositorio.save(categoria2);
				toDto.categoriaToDTO(categoria2);	
			}
		} catch (Exception e) {
			System.out.println("[IMPL-Categoria][inicializarCategorias] " + e.getMessage());
		}
		try {
			// Comprueba si ya existe un usuario admin
			if (!Crepositorio.existsByTipoCategoria("Mundo")) {
				// Si no existe, crea un nuevo usuario con rol de administrador
				Categoria categoria3 = new Categoria();
				categoria3.setTipoCategoria("Mundo");
				categoria3.setDescCategoria("Esta categoría trata sobre mundo");
				Crepositorio.save(categoria3);
				toDto.categoriaToDTO(categoria3);	
			}
		} catch (Exception e) {
			System.out.println("[IMPL-Categoria][inicializarCategorias] " + e.getMessage());
		}
		try {
			// Comprueba si ya existe un usuario admin
			if (!Crepositorio.existsByTipoCategoria("Ciencia")) {
				// Si no existe, crea un nuevo usuario con rol de administrador
				Categoria categoria4 = new Categoria();
				categoria4.setTipoCategoria("Ciencia");
				categoria4.setDescCategoria("Esta categoría trata sobre ciencia");
				Crepositorio.save(categoria4);
				toDto.categoriaToDTO(categoria4);	
			}
		} catch (Exception e) {
			System.out.println("[IMPL-Categoria][inicializarCategorias] " + e.getMessage());
		}
		try {
			// Comprueba si ya existe un usuario admin
			if (!Crepositorio.existsByTipoCategoria("Tecnología")) {
				// Si no existe, crea un nuevo usuario con rol de administrador
				Categoria categoria4 = new Categoria();
				categoria4.setTipoCategoria("Tecnología");
				categoria4.setDescCategoria("Esta categoría trata sobre tecnología");
				Crepositorio.save(categoria4);
				toDto.categoriaToDTO(categoria4);	
			}
		} catch (Exception e) {
			System.out.println("[IMPL-Categoria][inicializarCategorias] " + e.getMessage());
		}
	}

	/**
	 * Método que automatiza la creación de un usuario administrador que se ejecuta
	 * la primera vez que se despliega la aplicación.
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady() {
		try {
			inicializarCategorias();
		} catch (Exception e) {
			System.out.println("[IMPL-Usu][onApplicationReady] " + e.getMessage());
		}
	}


	
}
