package proyecto.periodico.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import proyecto.periodico.dao.Categoria;
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
	public Categoria buscarPorId(long idCategoria) {
		return Crepositorio.findById(idCategoria).orElse(null);
	}
	
	
}
