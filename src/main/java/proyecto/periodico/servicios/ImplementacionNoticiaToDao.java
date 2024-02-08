package proyecto.periodico.servicios;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyecto.periodico.dao.Categoria;
import proyecto.periodico.dao.Noticia;
import proyecto.periodico.dao.Usuario;
import proyecto.periodico.dto.NoticiaDTO;
import proyecto.periodico.repositorios.noticiaRepositorio;

@Service
public class ImplementacionNoticiaToDao implements InterfazNoticiaToDAO {
	@Autowired
	private noticiaRepositorio Nrepositorio;
	

	  @Override
	    public Noticia noticiaToDao(NoticiaDTO noticiaDTO, Usuario usuario, Categoria categoria) {
	        try {
	        	ImplementacionNoticia iPac = new ImplementacionNoticia();
	            Noticia noticiaDao = new Noticia();
	            noticiaDao.setIdNoticia(noticiaDTO.getIdNoticia());
	            noticiaDao.setTituloNoticia(noticiaDTO.getTituloNoticia());
	            noticiaDao.setDescNoticia(noticiaDTO.getDescNoticia());
	            noticiaDao.setFoto(iPac.convertToByteArray(noticiaDTO.getFoto()));
	            noticiaDao.setEstado_suscripcion(noticiaDTO.getEstado_suscripcion());
	            noticiaDao.setFchaPublicacion(noticiaDTO.getFchaPublicacionMostrarWeb());
	            // Asignar el usuario y la categoría a la noticia
	            noticiaDao.setUsuario(usuario);
	            noticiaDao.setNoticiaCategoria(categoria);
	            
	            
	            Nrepositorio.save(noticiaDao);
	            // Puedes agregar más atributos según sea necesario

	            return noticiaDao;
	        } catch (Exception e) {
	            System.out.println("[ERROR ImplementacionNoticia - noticiaToDao()] - Error al convertir noticiaDTO a noticia DAO (return null): " + e);
	            return null;
	        }
	    }

	@Override
	public List<Noticia> listaNoticiaToDao(List<NoticiaDTO> listaNoticiaDTO) {
		// TODO Auto-generated method stub
		return null;
	}

}
