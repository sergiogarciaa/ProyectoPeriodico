package proyecto.periodico.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import proyecto.periodico.dao.Noticia;


@Service
public interface noticiaRepositorio extends JpaRepository<Noticia, Long>{
	// Método para obtener todas las noticias
	List<Noticia> findAll();

	List<Noticia> findAllByOrderByFchaPublicacionDesc();
	
}
