package proyecto.periodico.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import proyecto.periodico.dao.Noticia;
import proyecto.periodico.repositorios.noticiaRepositorio;
import proyecto.periodico.repositorios.usuarioRepositorio;



@Service
@Transactional
public class ImplementacionNoticia implements InterfazNoticia  {
	@Autowired
	private noticiaRepositorio Nrepositorio;
	
	
	@Override
	public Noticia buscarNoticiaPorID(long id) {
		return Nrepositorio.findById(id).orElse(null);
	}

}
