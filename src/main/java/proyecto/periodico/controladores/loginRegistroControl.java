package proyecto.periodico.controladores;

import java.util.Arrays;
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

import jakarta.servlet.http.HttpServletRequest;
import proyecto.periodico.dao.Categoria;
import proyecto.periodico.dao.Noticia;
import proyecto.periodico.dao.Usuario;
import proyecto.periodico.dto.CategoriaDTO;
import proyecto.periodico.dto.NoticiaDTO;
import proyecto.periodico.dto.UsuarioDTO;
import proyecto.periodico.repositorios.noticiaRepositorio;
import proyecto.periodico.servicios.ImplementacionNoticia;
import proyecto.periodico.servicios.ImplementacionNoticiaToDTO;
import proyecto.periodico.servicios.ImplementacionUsuario;
import proyecto.periodico.servicios.InterfazCategoria;
import proyecto.periodico.servicios.InterfazNoticia;
import proyecto.periodico.servicios.InterfazNoticiaToDTO;
import proyecto.periodico.servicios.InterfazUsuario;
import proyecto.periodico.servicios.InterfazUsuarioToDTO;

@Controller
public class loginRegistroControl {

	@Autowired
    private InterfazUsuario usuarioServicio;
    
    @Autowired
    private InterfazCategoria categoriaServicio;
    
    @Autowired
    private InterfazNoticia noticiaServicio;
    
    @Autowired
    private InterfazUsuarioToDTO usuarioToDTO;

	
	
	/**
	 * Gestiona la solicitud HTTP GET para /auth/login y muestra la página de inicio
	 * de sesión
	 * 
	 * @param model Modelo que se utiliza para enviar un usuarioDTO vacio a la
	 *              vista.
	 * @return La vista de inicio de sesión (login.html).
	 */
	@GetMapping("/auth/login")
	public String login(Model model) {
		// Se agrega un nuevo objeto UsuarioDTO al modelo para el formulario de login
		model.addAttribute("usuarioDTO", new UsuarioDTO());
	        return "login";
	}


	@GetMapping("/")
	public String index1(Model model) {
		List<NoticiaDTO> noticiaDTOlist = noticiaServicio.buscarTodas();
		// Recorre la lista para agregarle a cada noticia su resumen.
		for (NoticiaDTO noticiaDTO : noticiaDTOlist) {
			 noticiaDTO.setResumenNoticia(noticiaServicio.resumirNoticia(noticiaDTO.getDescNoticia()) + "...");
		}
		
		System.out.println("VER RESUMEN: " + noticiaDTOlist);
		List<CategoriaDTO> categoriaDTO = categoriaServicio.buscarTodas();
		model.addAttribute("noticias", noticiaDTOlist);
		model.addAttribute("categorias", categoriaDTO);
		return "landing";
	}

	
	// Corregir más tarde con un Seeder
	
	@GetMapping("/auth/landing")
	public String index(Model model) {
		List<NoticiaDTO> noticiaDTOlist = noticiaServicio.buscarTodas();
		List<CategoriaDTO> categoriaDTO = categoriaServicio.buscarTodas();
		for (NoticiaDTO noticiaDTO : noticiaDTOlist) {
			 noticiaDTO.setResumenNoticia(noticiaServicio.resumirNoticia(noticiaDTO.getDescNoticia()) + "...");
		}
		
		System.out.println("Mostrando Todas las Noticias LANDING" + noticiaDTOlist);
		model.addAttribute("noticias", noticiaDTOlist);
		model.addAttribute("categorias", categoriaDTO);
		return "landing";
	}


	/**
	 * Gestiona la solicitud HTTP GET para mostrar la página de registro.
	 * 
	 * @param model Modelo que se utiliza para enviar un usuarioDTO vacio a la
	 *              vista.
	 * @return La vista de registro de usuario (registrar.html).
	 */
	@GetMapping("/auth/registrar")
	public String registrarGet(Model model) {
		try {
            model.addAttribute("usuarioDTO", new UsuarioDTO());
            return "registro";
        } catch (Exception e) {
            model.addAttribute("error", "Error al procesar la solicitud. Por favor, inténtelo de nuevo.");
            return "registro";
        }
	}

	/**
	 * Procesa la solicitud HTTP POST para registro de un nuevo usuario.
	 * 
	 * @param usuarioDTO El objeto UsuarioDTO que recibe en el modelo y contiene los
	 *                   datos del nuevo usuario.
	 * @return La vista de inicio de sesión (login.html) si fue exitoso el registro;
	 *         de lo contrario, la vista de registro de usuario (registro.html).
	 */
	@PostMapping("/auth/registrar")
	public String registrarPost(@ModelAttribute UsuarioDTO usuarioDTO, Model model) {

		UsuarioDTO nuevoUsuario = usuarioServicio.registrar(usuarioDTO);

		 if (nuevoUsuario != null && !nuevoUsuario.isCuentaConfirmada()) {
			// Si el usuario y el DNI no son null es que el registro se completo
			// correctamente
			model.addAttribute("mensajeRegistroExitoso", "Registro del nuevo usuario OK");
			return "login";
		} 
		 else if (nuevoUsuario.isCuentaConfirmada()) {
				return "login";
			} else {
				model.addAttribute("mensajeErrorMail", "Ya existe un usuario con ese email");
				return "registro";
			}
		}

	/**
	 * Gestiona la solicitud HTTP GET para llevar a la página de home una vez
	 * logeado con exito.
	 * 
	 * @return La vista de home.html
	 */
	@GetMapping("/privada/index")
	public String loginCorrecto(Model model, Authentication authentication) {
		try {
	            boolean cuentaConfirmada = usuarioServicio.estaLaCuentaConfirmada(authentication.getName());

	            if (cuentaConfirmada) {
	                model.addAttribute("nombreUsuario", authentication.getName());
	                List<NoticiaDTO> noticiaDTOlist = noticiaServicio.buscarTodas();
	        		List<CategoriaDTO> categoriaDTO = categoriaServicio.buscarTodas();
	        		for (NoticiaDTO noticiaDTO : noticiaDTOlist) {
	       			 noticiaDTO.setResumenNoticia(noticiaServicio.resumirNoticia(noticiaDTO.getDescNoticia()) + "...");
	       		}
	        		model.addAttribute("noticias", noticiaDTOlist); 
	        		model.addAttribute("categorias", categoriaDTO);
	                return "index";
	            } else {
	                model.addAttribute("cuentaNoVerificada", "Error al confirmar su email");
	                return "login";
	            }
	        } catch (Exception e) {
	            model.addAttribute("error", "Error al procesar la solicitud. Por favor, inténtelo de nuevo.");
	            return "login";
	        }
	}
	  @GetMapping("/auth/confirmacionCorreo")
	    public String confirmarCuenta(Model model, @RequestParam("token") String token) {
	        try {
	            boolean confirmacionExitosa = usuarioServicio.confirmarCuenta(token);

	            if (confirmacionExitosa) {
	                model.addAttribute("cuentaVerificada", "Su dirección de correo ha sido confirmada correctamente");
	            } else {
	                model.addAttribute("cuentaNoVerificada", "Error al confirmar su email");
	            }

	            return "login";
	        } catch (Exception e) {
	            model.addAttribute("error", "Error al procesar la solicitud. Por favor, inténtelo de nuevo.");
	            return "login";
	        }
	    }
}
