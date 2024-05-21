package proyecto.periodico.servicios;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import proyecto.periodico.dao.Categoria;
import proyecto.periodico.dao.Noticia;
import proyecto.periodico.dao.Usuario;
import proyecto.periodico.dto.NoticiaDTO;
import proyecto.periodico.repositorios.categoriaRepositorio;
import proyecto.periodico.repositorios.noticiaRepositorio;
import proyecto.periodico.repositorios.usuarioRepositorio;

@Service
@Transactional
public class ImplementacionNoticia implements InterfazNoticia  {
	
	@PersistenceContext
    private EntityManager entityManager;
	
	@Autowired
	private noticiaRepositorio Nrepositorio;
	
	@Autowired
	private categoriaRepositorio Crepositorio;
	
	@Autowired
	private InterfazNoticiaToDTO toDto;
	
	@Autowired
	private InterfazCategoriasToDTO CategoriatoDto;

	@Autowired
	private InterfazUsuarioToDTO UsutoDto;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private usuarioRepositorio Usurepositorio;

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
        if (texto != null && texto.length() >= 360) {
            // Subcadena que contiene los primeros 50 caracteres
            return texto.substring(0, 360);
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
	
	private void inicializarNoticiaUsuYCategoria() {
		try {
			// Comprueba si ya existe un usuario admin
			if (!Nrepositorio.existsById((long) 1)) {
				// Ruta de la imagen en tu sistema de archivos
					String rutaImagen = "C:\\Users\\Sergio\\Desktop\\DWS\\periodico\\src\\main\\resources\\static\\img\\imagenNoticia1.jpg";
					// Lee la imagen como un arreglo de bytes
				    byte[] imagenBytes = Files.readAllBytes(new File(rutaImagen).toPath());
					Noticia noticia1 = new Noticia();
					// Comprueba si ya existe un usuario admin
					if (!Crepositorio.existsByTipoCategoria("Deporte")) {						
						// Si no existe, crea un nuevo usuario con rol de administrador
						Categoria categoria2 = new Categoria();
						categoria2.setTipoCategoria("Deporte");
						categoria2.setDescCategoria("Esta categoría trata sobre deporte");
						Crepositorio.save(categoria2);
						CategoriatoDto.categoriaToDTO(categoria2);	
						// Crear Noticia
						noticia1.setNoticiaCategoria(categoria2);
						 // Obtener la fecha y hora actual
				        Calendar fechaActual = Calendar.getInstance();
				        // Formatear la fecha y hora actual como una cadena
				        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						// Si no existe, crea un nuevo usuario con rol de administrador
						
						noticia1.setTituloNoticia("UFC 298 - Volkanovski Vs. Topuria: La Batalla Épica por el Título de Peso Pluma");
						noticia1.setDescNoticia("El mundo de las artes marciales mixtas está a punto de presenciar un enfrentamiento legendario cuando el actual campeón de peso pluma de la UFC, Alexander Volkanovski, se enfrente al desafiante Ilia Topuria en el tan esperado evento UFC 298, programado para el próximo sábado 17 de febrero.\r\n"
								+ "\r\n"
								+ "Las expectativas están en su punto más alto mientras los aficionados de todo el mundo aguardan con ansias esta pelea que promete ser una de las más emocionantes del año. Volkanovski, quien ha demostrado ser un campeón dominante con un récord impresionante en la UFC, se enfrentará a un Topuria hambriento de victoria, determinado a arrebatarle el título al actual campeón.\r\n"
								+ "\r\n"
								+ "Ambos luchadores llegan a esta pelea con una confianza inquebrantable y una preparación meticulosa. Volkanovski, conocido por su agresividad controlada y su habilidad técnica, buscará mantener su reinado como campeón y defender su título con uñas y dientes. Por otro lado, Topuria, un luchador joven y talentoso con un récord impecable en la UFC, está decidido a hacer historia al derrotar a Volkanovski y reclamar el título de peso pluma para sí mismo.\r\n"
								+ "\r\n"
								+ "El enfrentamiento entre Volkanovski y Topuria promete ser una exhibición de habilidad, resistencia y determinación. Con el título de peso pluma en juego, ambos luchadores están dispuestos a dejarlo todo en el octágono y dar a los aficionados una pelea que nunca olvidarán.\r\n"
								+ "\r\n"
								+ "Los expertos predicen un combate lleno de acción y emoción, con intercambios frenéticos, derribos estratégicos y golpes devastadores. La tensión en el aire es palpable mientras los dos guerreros se preparan para enfrentarse en el centro del octágono y luchar por la gloria.\r\n"
								+ "\r\n"
								+ "Con el mundo de las MMA observando con atención, UFC 298 promete ser un evento épico que quedará grabado en la memoria de los aficionados como una de las peleas más destacadas de la historia de la UFC. Ya sea que Volkanovski retenga su título o Topuria logre una sorpresa, una cosa es segura: esta será una batalla que no querrás perderte. ¡Prepárate para presenciar el choque de titanes en UFC 298!");
						noticia1.setFchaPublicacion(formatoFecha.format(fechaActual.getTime()));
						noticia1.setFoto(imagenBytes);
						// Crear admin
						Usuario admin = new Usuario();
						admin.setNombreUsuario("admin");
						admin.setClaveUsuario(passwordEncoder.encode("admin"));
						admin.setDniUsuario("-");
						admin.setEmailUsuario("admin@admin.com");
						admin.setRol("ROLE_4");
						admin.setCuentaConfirmada(true);
						Usurepositorio.save(admin);
						UsutoDto.usuarioToDto(admin);
						// 
						noticia1.setUsuario(admin);
						Nrepositorio.save(noticia1);
						toDto.noticiaToDto(noticia1);
					}
		
			}
		} catch (Exception e) {
			System.out.println("[IMPL-Categoria][inicializarCategorias] " + e.getMessage());
		}
	}
	/**
	 * Método que automatiza la creación de un usuario administrador que se ejecuta
	 * la primera vez que se despliega la aplicación.
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady() {
		try {
			inicializarNoticiaUsuYCategoria();
		} catch (Exception e) {
			System.out.println("[IMPL-Usu][onApplicationReady] " + e.getMessage());
		}
	}

	@Override
	public void eliminar(Long id) {
		Nrepositorio.deleteById(id);
		
	}
}
