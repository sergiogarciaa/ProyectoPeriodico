package proyecto.periodico.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import proyecto.periodico.dao.Categoria;

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
	
	
	public Categoria crearCategoria() {
		Categoria categoria = new Categoria();
		categoria.setTipoCategoria("Deporte");
		categoria.setDescCategoria("Futbol");
		System.out.println(categoria);
		Crepositorio.save(categoria);
		
		return categoria;
	}
	
	
}
