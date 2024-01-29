package proyecto.periodico.repositorios;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import proyecto.periodico.dao.Categoria;

@Service
public interface categoriaRepositorio extends JpaRepository<Categoria, Long> {
	
}
