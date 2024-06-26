package proyecto.periodico.controladores;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import proyecto.periodico.dao.Categoria;
import proyecto.periodico.dao.Noticia;
import proyecto.periodico.dao.Usuario;
import proyecto.periodico.dto.CategoriaDTO;
import proyecto.periodico.dto.NoticiaDTO;
import proyecto.periodico.dto.UsuarioDTO;
import proyecto.periodico.servicios.ImplementacionNoticia;
import proyecto.periodico.servicios.InterfazCategoria;
import proyecto.periodico.servicios.InterfazCategoriasToDTO;
import proyecto.periodico.servicios.InterfazNoticia;
import proyecto.periodico.servicios.InterfazNoticiaToDAO;
import proyecto.periodico.servicios.InterfazUsuario;

@Controller
public class PeriodistaControl {
	@Autowired
    private InterfazUsuario usuarioServicio;
    
    @Autowired
    private InterfazCategoria categoriaServicio;
    
    @Autowired
    private InterfazNoticia noticiaServicio;
    
    @Autowired
    private InterfazNoticiaToDAO noticiaToDao;
    
	@GetMapping("/privada/zonaPeriodista")
	public String zonaPeriodista(Model model, HttpServletRequest request) {
		 // Obtener listas de categorías y usuarios desde tus servicios
        List<CategoriaDTO> categoriasDTO = categoriaServicio.buscarTodas();
        System.out.println(categoriasDTO);

        // Agregar las listas al modelo para que el formulario pueda acceder a ellas
        model.addAttribute("categoriasDTO", categoriasDTO);

        // Agregar un objeto NoticiaDTO al modelo para que el formulario pueda mostrar y editar los datos
        model.addAttribute("noticiaDTO", new NoticiaDTO());
        
        if (request.isUserInRole("ROLE_1")) {
			return "redirect:/privada/index";	
		}
		if (request.isUserInRole("ROLE_2") || request.isUserInRole("ROLE_4")) {
			return "periodista";
		}
		return "index";
	}
	
	@PostMapping("/guardarNoticia")
	public String guardarNoticia(@ModelAttribute("noticiaDTO") NoticiaDTO noticiaDTO, Authentication authentication, @RequestParam("file") MultipartFile foto) {
	    try {
	    	if (!foto.isEmpty()) {
				String convertedImage = noticiaServicio.convertToBase64(foto.getBytes());
				noticiaDTO.setFoto(convertedImage);
			}
	    	// Consultar usuario a traves del email
	    	Usuario usuario = usuarioServicio.buscarPorEmail(authentication.getName());

	        // Consultar la categoría por ID desde la base de datos o utilizarla directamente si la tienes en el DTO
	        Categoria categoria = categoriaServicio.buscarPorId(noticiaDTO.getIdCategoria());
	        
	        // Obtener la fecha y hora actual
	        Calendar fechaActual = Calendar.getInstance();
	        // Formatear la fecha y hora actual como una cadena
	        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        noticiaDTO.setFchaPublicacionMostrarWeb(formatoFecha.format(fechaActual.getTime()));
	        // Crear una entidad Noticia a partir del DTO
	        Noticia noticia = noticiaToDao.noticiaToDao(noticiaDTO, usuario, categoria);


	        // Verificar si la noticia se guardó correctamente
	        if (noticia != null) {
	            System.out.println("Noticia Creada.");
	            return "redirect:/privada/zonaPeriodista";
	        } else {
	        	System.out.println("Noticia No Creada.");
	            return "redirect:/privada/index";
	        }
	    } catch (Exception e) {
	        System.out.println("ERROR");
	        return "redirect:/privada/index";
	    }
	}
	
	@GetMapping("/privada/crearCategoria")
    public String crearCategoria(Model model, HttpServletRequest request) {
		if (request.isUserInRole("ROLE_2") || request.isUserInRole("ROLE_4")) {
			model.addAttribute("categorias", categoriaServicio.buscarTodas());
	        model.addAttribute("categoriaForm", new Categoria());
	        return "formularioNuevaCategoria";
		}
		else {
			return "redirect:/privada/index";
		}
		
		
    }
	
	@PostMapping("/privada/guardarCategoria")
    public String guardarCategoria(@ModelAttribute("categoriaForm") Categoria categoria) {
		categoriaServicio.guardarCategoria(categoria);
        return "redirect:/privada/zonaPeriodista";
    }
	
	// Método para eliminar una categoría
    @GetMapping("/privada/eliminarCategoria/{id}")
    public String eliminarCategoria(@PathVariable("id") Long idCategoria, HttpServletRequest request, Model model) {
    	if (request.isUserInRole("ROLE_2") || request.isUserInRole("ROLE_4")) {
    		try {
    			categoriaServicio.eliminar(idCategoria);
    			model.addAttribute("categoriaBorrada", "Categoria borrada correctamente");
    			model.addAttribute("categorias", categoriaServicio.buscarTodas());
    	        model.addAttribute("categoriaForm", new Categoria());
                return "formularioNuevaCategoria";
                
            } catch (Exception e) {
                // Si hay un error al intentar eliminar la categoría, mostrar un mensaje de error
                model.addAttribute("noSePuedeEliminar", "No se puede eliminar una categoría que tiene noticia/s");
                model.addAttribute("categorias", categoriaServicio.buscarTodas());
    	        model.addAttribute("categoriaForm", new Categoria());
                return "formularioNuevaCategoria";
            }
    	}
    	else {
			return "redirect:/privada/index";
		}
    }
    
    // Método para eliminar una noticia
    @GetMapping("/privada/eliminarNoticia/{id}")
    public String eliminarNoticia(@PathVariable("id") Long idNoticia, Authentication authentication, Model model) {
        try {
            Usuario usuario = usuarioServicio.buscarPorEmail(authentication.getName());
            Noticia noticia = noticiaServicio.buscarNoticiaPorID(idNoticia);

            if (noticia.getUsuario().getIdUsuario() == usuario.getIdUsuario() 
            		|| usuario.getRol().equals("ROLE_3")
            		|| usuario.getRol().equals("ROLE_4")) {
                noticiaServicio.eliminar(idNoticia);
                model.addAttribute("noticiaBorrada", "Noticia borrada correctamente");
            } else {
                model.addAttribute("error", "No tienes permisos para borrar esta noticia.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error al intentar eliminar la noticia.");
        }
        return "redirect:/privada/index";
    }
}