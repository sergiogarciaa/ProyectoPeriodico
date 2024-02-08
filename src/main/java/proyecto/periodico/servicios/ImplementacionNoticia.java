package proyecto.periodico.servicios;

import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import proyecto.periodico.dao.Noticia;
import proyecto.periodico.dto.NoticiaDTO;

import proyecto.periodico.repositorios.noticiaRepositorio;

@Service
@Transactional
public class ImplementacionNoticia implements InterfazNoticia  {
	
	@PersistenceContext
    private EntityManager entityManager;
	
	@Autowired
	private noticiaRepositorio Nrepositorio;
	
	@Autowired
	private InterfazNoticiaToDTO toDto;

	
	@Override
	public Noticia buscarNoticiaPorID(long id) {
		return Nrepositorio.findById(id).orElse(null);
	}

	@Override
	public List<NoticiaDTO> buscarTodas() {
		return toDto.listaNoticiasToDto(Nrepositorio.findAll());
	}
	

    @Override
    public List<NoticiaDTO> buscarPorCategoria(Long idCategoria) {
        return entityManager.createQuery(
            "SELECT n FROM Noticia n WHERE n.noticiaCategoria.id = :idCategoria", Noticia.class)
            .setParameter("idCategoria", idCategoria)
            .getResultList()
            .stream()
            .map(toDto::noticiaToDto)
            .collect(Collectors.toList());
    }

	@Override
	public String resumirNoticia(String texto) {
		// Verifica si el texto no es nulo y tiene al menos 50 caracteres
        if (texto != null && texto.length() >= 30) {
            // Subcadena que contiene los primeros 50 caracteres
            return texto.substring(0, 35);
        } else {
            // Si el texto tiene menos de 50 caracteres o es nulo, devuelve el texto original
            return texto;
        }
	}
	
	@Override
	public String resumirNoticia2(String texto) {
		// Verifica si el texto no es nulo y tiene al menos 50 caracteres
        if (texto != null && texto.length() >= 200) {
            // Subcadena que contiene los primeros 50 caracteres
            return texto.substring(0, 200);
        } else {
            // Si el texto tiene menos de 50 caracteres o es nulo, devuelve el texto original
            return texto;
        }
	}
	
	public   String convertToBase64(byte[] data) {
        if (data != null && data.length > 0) {
            return Base64.getEncoder().encodeToString(data);
        }
        return null;
    }

	 public byte[] convertToByteArray(String base64String) {
	        if (base64String != null && !base64String.isEmpty()) {
	            return Base64.getDecoder().decode(base64String);
	        }
	        return null;
	    }
	 
	public Noticia obtenerNoticiaMasReciente() {
		  // Recupera todas las noticias ordenadas por fecha de publicación en orden descendente
        List<Noticia> noticias = Nrepositorio.findAllByOrderByFchaPublicacionDesc();

        // Verifica si hay noticias
        if (!noticias.isEmpty()) {
            // La primera noticia en la lista será la más reciente debido al ordenamiento
            return noticias.get(0);
        } else {
            // Si no hay noticias, devuelve null o maneja el caso según tus necesidades
            return null;
        }
	}
}