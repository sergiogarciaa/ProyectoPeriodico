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
import proyecto.periodico.dao.Usuario;
import proyecto.periodico.dto.UsuarioDTO;
import proyecto.periodico.servicios.InterfazUsuario;


@Controller
public class loginRegistroControl {

	@Autowired
	private InterfazUsuario usuarioServicio;

	/**
	 * Gestiona la solicitud HTTP GET para /auth/login y muestra la página de inicio de sesión
	 * @param model Modelo que se utiliza para enviar un usuarioDTO vacio a la vista.
	 * @return La vista de inicio de sesión (login.html).
	 */
	@GetMapping("/auth/login")
	public String login(Model model) {
		// Se agrega un nuevo objeto UsuarioDTO al modelo para el formulario de login
		model.addAttribute("usuarioDTO", new UsuarioDTO());
		return "login";
	}
	@GetMapping("/")
	public String index1() {
		return "landing";
	}

	@GetMapping("/auth/landing")
	public String index(Model model) {
		model.addAttribute("usuarioDTO", new UsuarioDTO());
		return "landing";
	}
	/**
	 * Gestiona la solicitud HTTP GET para mostrar la página de registro.
	 * @param model Modelo que se utiliza para enviar un usuarioDTO vacio a la vista.
	 * @return La vista de registro de usuario (registrar.html).
	 */
	@GetMapping("/auth/registrar")
	public String registrarGet(Model model) {
		model.addAttribute("usuarioDTO", new UsuarioDTO());
		return "registro";
	}

	/**
	 * Procesa la solicitud HTTP POST para registro de un nuevo usuario.
	 * @param  usuarioDTO El objeto UsuarioDTO que recibe en el modelo y contiene los
	 *         datos del nuevo usuario.
	 * @return La vista de inicio de sesión (login.html) si fue exitoso el registro; 
	 * 		   de lo contrario, la vista de registro de usuario (registro.html).
	 */
	@PostMapping("/auth/registrar")
	public String registrarPost(@ModelAttribute UsuarioDTO usuarioDTO, Model model) {

		UsuarioDTO nuevoUsuario = usuarioServicio.registrar(usuarioDTO);
		
		if (nuevoUsuario != null && nuevoUsuario.getDniUsuario() != null) {
			// Si el usuario y el DNI no son null es que el registro se completo correctamente
			model.addAttribute("mensajeRegistroExitoso", "Registro del nuevo usuario OK");
			return "login";
		} else {
			// Se verifica si el DNI ya existe para mostrar error personalizado en la vista
			if (usuarioDTO.getDniUsuario() == null) {
				model.addAttribute("mensajeErrorDni", "Ya existe un usuario con ese DNI");
				return "registro";
			} else {
				model.addAttribute("mensajeErrorMail", "Ya existe un usuario con ese email");
				return "registro";
			}
		}
	}

	

	/**
	 * Gestiona la solicitud HTTP GET para llevar a la página de home una vez logeado con exito.
	 * @return La vista de home.html
	 */
	@GetMapping("/privada/index")
	public String loginCorrecto(Model model, Authentication authentication) {
		Usuario usuario = usuarioServicio.buscarPorEmail(authentication.getName());
		String email = usuario.getEmailUsuario();
		model.addAttribute("nombreUsuario", email);
		System.out.println(authentication.getAuthorities());
		return "index";
	}
	
	@GetMapping("/privada/administracion")
	public String listadoUsuarios(Model model, HttpServletRequest request) {
		List<UsuarioDTO> usuarios = usuarioServicio.buscarTodos();
		System.out.println(usuarios);
		model.addAttribute("usuarios", usuarios);
		if(request.isUserInRole("ROLE_3") || request.isUserInRole("ROLE_4")) {
			return "administracion";	
		} 
		return "index";
	}
	
	@GetMapping("/privada/eliminar/{id}")
	public String eliminarUsuario(@PathVariable Long id, Model model, HttpServletRequest request) {
		Usuario usuario = usuarioServicio.buscarPorId(id);
		List<UsuarioDTO> usuarios = usuarioServicio.buscarTodos();
		System.out.println(usuario);
		if(request.isUserInRole("ROLE_4") && usuario.getRol().equals("ROLE_4")) {
			model.addAttribute("noSePuedeEliminar", "No se puede eliminar a un admin");
			model.addAttribute("usuarios", usuarios);
			return "adminstracion";
		}
		usuarioServicio.eliminar(id);
		return "redirect:/privada/administracion";
		
	}
	
	/**
	 * Gestiona la solicitud HTTP Post para actualizar los roles
	 * @return Cambio de rol
	 */
	
	@PostMapping("/privada/administracion/cambiarRol")
	public String cambiarRol(@RequestParam String emailUsuario, @RequestParam String nuevoRol, Model model) {
	    Usuario usuario = usuarioServicio.buscarPorEmail(emailUsuario);

	    if ("ROLE_4".equals(usuario.getRol())) {
	        // Si el usuario es superadmin, no permitir cambiar el rol
	        model.addAttribute("noSePuedeCambiarRol", "No se puede cambiar el rol del superadmin.");
	    } else {
	        boolean exito = usuarioServicio.cambiarRolPorEmail(emailUsuario, nuevoRol);

	        if (exito) {
	            // Actualiza la lista de usuarios después de cambiar el rol
	            List<UsuarioDTO> usuarios = usuarioServicio.buscarTodos();
	            model.addAttribute("usuarios", usuarios);
	            return "administracion";
	        } else {
	            return "error";
	        }
	    }

	    // Si no se pudo cambiar el rol, vuelve a la página de administración
	    List<UsuarioDTO> usuarios = usuarioServicio.buscarTodos();
	    model.addAttribute("usuarios", usuarios);
	    return "administracion";
	}
}
