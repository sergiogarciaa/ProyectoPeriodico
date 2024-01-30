package proyecto.periodico.repositorios;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import proyecto.periodico.dao.Categoria;

@Service
public interface categoriaRepositorio extends JpaRepository<Categoria, Long> {
	/**
	 * Checkea si existe un usuario con el nombre de usuario especificado.
	 * @param nombreUsuario El nombre de usuario del usuario a buscar.
	 * @return true si existe un usuario con el nombre de usuario especificado, false en caso contrario.
	 */
	public boolean existsByTipoCategoria(String tipoCategoria);
}
