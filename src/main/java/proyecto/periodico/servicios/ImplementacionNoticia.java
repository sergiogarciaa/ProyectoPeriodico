package proyecto.periodico.servicios;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import proyecto.periodico.dao.Categoria;
import proyecto.periodico.dao.Noticia;
import proyecto.periodico.dao.Usuario;
import proyecto.periodico.dto.NoticiaDTO;
import proyecto.periodico.dto.UsuarioDTO;
import proyecto.periodico.repositorios.noticiaRepositorio;

@Service
@Transactional
public class ImplementacionNoticia implements InterfazNoticia  {
	
	
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
	
	@PersistenceContext
    private EntityManager entityManager;

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
	
<<<<<<< HEAD
	// Método para calcular el resumen de la noticia
	public String calcularResumen(String descNoticia, int palabras) {
            return null;
=======
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
>>>>>>> 52bd818dcfa9f5861e0df056fc10b3edbc0d8d7a
	}

}