package proyecto.periodico.servicios;

import java.util.List;

import proyecto.periodico.dao.Categoria;
import proyecto.periodico.dto.CategoriaDTO;

public interface InterfazCategoria {
	
	
	/* public Categoria crearCategoria(); */

	public List<CategoriaDTO> buscarTodas();

	public Categoria buscarPorId(Categoria categoria);

	public Categoria buscarPorId(long idCategoria);
	
	public void guardarCategoria(Categoria categoria);
	
	public void eliminar(Long idCategoria);
}
