package proyecto.periodico.servicios;

import java.util.List;

import proyecto.periodico.dao.Categoria;
import proyecto.periodico.dto.CategoriaDTO;



public interface InterfazCategoriasToDTO {
	CategoriaDTO categoriaToDTO(Categoria categoria);
	public List<CategoriaDTO> listaCategoriaToDto(List<Categoria> listaCategoria);
}
