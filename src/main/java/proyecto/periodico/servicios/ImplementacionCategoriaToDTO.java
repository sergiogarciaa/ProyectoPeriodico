package proyecto.periodico.servicios;

import java.util.ArrayList;

import java.util.List;

import org.springframework.stereotype.Service;

import proyecto.periodico.dao.Categoria;
import proyecto.periodico.dto.CategoriaDTO;


@Service
public class ImplementacionCategoriaToDTO implements InterfazCategoriasToDTO{

	@Override
	public CategoriaDTO categoriaToDTO(Categoria categoria) {
		try {
		 CategoriaDTO dto = new CategoriaDTO();
         dto.setIdCategoria(categoria.getIdCategoria());
         dto.setTipoCategoria(categoria.getTipoCategoria());
         dto.setDescCategoria(categoria.getDescCategoria());
         // Puedes agregar más atributos según sea necesario

         return dto;
     } catch (Exception e) {
         System.out.println("[ERROR ImplementacionCategoriaToDto - categoriaToDto()] - Error al convertir noticia DAO a categoriaDTO (return null): " + e);
         return null;
     }
	}

	@Override
	public List<CategoriaDTO> listaCategoriaToDto(List<Categoria> listaCategoria) {
		try {
			
			List<CategoriaDTO> listaDto = new ArrayList<>();
			for (Categoria c : listaCategoria) {
				listaDto.add(this.categoriaToDTO(c));
			}
			return listaDto;

		} catch (Exception e) {
			System.out.println(
					"\n[ERROR CategoriaToDTO - listaCategoriaToDto()] - Error al convertir lista de usuario DAO a lista de categoriaDTO (return null): "
							+ e);
		}
		return null;
	}

}
