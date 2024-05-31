package proyecto.periodico.controladores;

import java.util.Arrays;
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

    @Autowired
    private InterfazNoticiaToDTO noticiaToDTO;
	
	
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
		Noticia noticiaMasReciente = noticiaServicio.obtenerNoticiaMasReciente();
        NoticiaDTO noticiaDTOMasReciente = noticiaToDTO.noticiaToDto(noticiaMasReciente);
		// Recorre la lista para agregarle a cada noticia su resumen.
		for (NoticiaDTO noticiaDTO : noticiaDTOlist) {
			 noticiaDTO.setResumenNoticia(noticiaServicio.resumirNoticia(noticiaDTO.getDescNoticia()) + "...");
			 noticiaDTOMasReciente.setResumenNoticia2(noticiaServicio.resumirNoticia2(noticiaDTO.getDescNoticia()) + "...");
		}
		List<CategoriaDTO> categoriaDTO = categoriaServicio.buscarTodas();
		 // Agregar la noticia más reciente al modelo
        model.addAttribute("noticiaMasReciente", noticiaDTOMasReciente);
		model.addAttribute("noticias", noticiaDTOlist);
		model.addAttribute("categorias", categoriaDTO);
		return "landing";
	}

	
	// Corregir más tarde con un Seeder
	
	@GetMapping("/auth/landing")
	public String index(Model model) {
		return "/";
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
	        		Noticia noticiaMasReciente = noticiaServicio.obtenerNoticiaMasReciente();
	                NoticiaDTO noticiaDTOMasReciente = noticiaToDTO.noticiaToDto(noticiaMasReciente);
	        		// Recorre la lista para agregarle a cada noticia su resumen.
	        		for (NoticiaDTO noticiaDTO : noticiaDTOlist) {
	        			 noticiaDTO.setResumenNoticia(noticiaServicio.resumirNoticia(noticiaDTO.getDescNoticia()) + "...");
	        			 noticiaDTOMasReciente.setResumenNoticia2(noticiaServicio.resumirNoticia2(noticiaDTO.getDescNoticia()) + "...");
	        		}
	        		model.addAttribute("noticiaMasReciente", noticiaDTOMasReciente);
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
	  /**
		 * Gestiona la solicitud HTTP GET para la url /auth/iniciarRecuperacion 
		 * y muestra la vista de inicio de recuperación.
		 * @param model El modelo en el que se añade como atributo un objeto usuarioDTO vacío para enviarlo a la vista.
		 * @return La vista de inicioRecuperación.html
		 */
		@GetMapping("/auth/iniciarRecuperacion")
		public String mostrarVistainiciarRecuperacion(Model model) {
			 try {
		            model.addAttribute("usuarioDTO", new UsuarioDTO());
		            return "iniciarRecuperacion";
		        } catch (Exception e) {
		            model.addAttribute("error", "Error al procesar la solicitud. Por favor, inténtelo de nuevo.");
		            return "iniciarRecuperacion";
		        }
		}

		/**
		 * Procesa la solicitud HTTP POST para la url /auth/iniciarRecuperacion 
		 * Se utiliza el email del usuario para intentar iniciar el proceso de recuperación.
		 * @param usuarioDTO El objeto UsuarioDTO que recibe del modelo y contiene el email del usuario.
		 * @param model El modelo que se utiliza para enviar mensajes a la vista.
		 * @return La vista de login.html si el envío del email fue exitoso; 
		 * 		   en caso contrario, la vista de inicio de recuperación.html
		 */
		@PostMapping("/auth/iniciarRecuperacion")
		public String procesarInicioRecuperacion(@ModelAttribute UsuarioDTO usuarioDTO, Model model) {
			
			try {
	            boolean envioConExito = usuarioServicio.iniciarResetPassConEmail(usuarioDTO.getEmailUsuario());

	            if (envioConExito) {
	                model.addAttribute("mensajeExitoMail", "Proceso de recuperación OK");
	                return "login";
	            } else {
	                model.addAttribute("mensajeErrorMail", "Error en el proceso de recuperación.");
	            }
	            return "iniciarRecuperacion";
	        } catch (Exception e) {
	            model.addAttribute("error", "Error al procesar la solicitud. Por favor, inténtelo de nuevo.");
	            return "iniciarRecuperacion";
	        }
		}
		
		/**
		 * Gestiona la solicitud HTTP GET para la url /auth/recuperar.
		 * Muestra la vista de recuperación de contraseña y enviando a esta en el modelo el dto con el token
		 * asociado al usuario para recuperar la contraseña, o en caso de no encontrarlo, mostrar un mensaje de error.
		 * @param token El token necesario para recuperar la contraseña obtenido de la url de la solicitud.
		 * @param model El modelo que se utiliza para enviar mensajes y datos en el modelo a la vista.
		 * @return La vista de recuperación de contraseña (recuperar.html) si el token es válido;
		 * 		   de lo contrario, la vista de inicioRecuperacion.html
		 */
		@GetMapping("/auth/recuperar")
		public String mostrarVistaRecuperar(@RequestParam(name = "token") String token, Model model) {
			 try {
		            UsuarioDTO usuario = usuarioServicio.obtenerUsuarioPorToken(token);
		            if (usuario != null) {
		                model.addAttribute("usuarioDTO", usuario);
		            } else {
		                model.addAttribute("mensajeErrorTokenValidez", "El enlace de recuperación no válido o usuario no encontrado");
		                return "iniciarRecuperacion";
		            }
		            return "recuperar";
		        } catch (Exception e) {
		            model.addAttribute("error", "Error al mostrar la vista de recuperar");
		            return "iniciarRecuperacion";
		        }
		}
		
		/**
		 * Procesa la solicitud HTTP POST para la url /auth/recuperar.
		 * Se utiliza el token del usuario para comprobar la validez e intentar recuperar la contraseña.
		 * @param usuarioDTO El objeto UsuarioDTO que recibe del modelo y contiene los nuevos datos de la contraseña.
		 * @param model El modelo que se utiliza para enviar mensajes a la vista.
		 * @return La vista de login.html si la modificación fue exitosa; de lo contrario, la vista de iniciarRecuperación.html
		 */
		@PostMapping("/auth/recuperar")
		public String procesarRecuperacionContraseña(@ModelAttribute UsuarioDTO usuarioDTO, Model model) {
			try {
	            UsuarioDTO usuarioExistente = usuarioServicio.obtenerUsuarioPorToken(usuarioDTO.getToken());

	            if (usuarioExistente == null) {
	                model.addAttribute("mensajeErrorTokenValidez", "El enlace de recuperación no válido");
	                return "iniciarRecuperacion";
	            }
	            if (usuarioExistente.getExpiracionToken().before(Calendar.getInstance())) {
	                model.addAttribute("mensajeErrorTokenExpirado", "El enlace de recuperación ha expirado");
	                return "iniciarRecuperacion";
	            }

	            boolean modificadaPassword = usuarioServicio.modificarContraseñaConToken(usuarioDTO);

	            if (modificadaPassword) {
	                model.addAttribute("contraseñaModificadaExito", "Contraseña modificada OK");
	                return "login";
	            } else {
	                model.addAttribute("contraseñaModificadaError", "Error al cambiar de contraseña");
	                return "iniciarRecuperacion";
	            }
	        } catch (Exception e) {
	            model.addAttribute("error", "Error al procesar la solicitud recuperar");
	            return "iniciarRecuperacion";
	        }
		}
}
