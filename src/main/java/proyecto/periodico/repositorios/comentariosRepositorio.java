package proyecto.periodico.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import proyecto.periodico.dao.Comentarios;

public interface comentariosRepositorio extends JpaRepository<Comentarios, Long>{

}
