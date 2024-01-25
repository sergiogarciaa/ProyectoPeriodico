package proyecto.periodico.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import proyecto.periodico.dao.Noticia;


public interface noticiaRepositorio extends JpaRepository<Noticia, Long>{

}
