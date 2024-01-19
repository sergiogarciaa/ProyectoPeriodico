package proyecto.periodico.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
		String nombreUsuario = usuario.getNombreUsuario() + " " + usuario.getApellidosUsuario();
		model.addAttribute("nombreUsuario", nombreUsuario);
		// Agregar información sobre si el usuario es administrador al modelo
	    model.addAttribute("isAdmin", usuario.getRol());
		return "index";
	}
	
	@GetMapping("/privada/administracion")
	public String listadoUsuarios(Model model, HttpServletRequest request, Authentication authentication) {
		
		Usuario usuario = usuarioServicio.buscarPorEmail(authentication.getName());
		// Sacar nombre del usuario logeado
		String nombreUsuario = usuario.getNombreUsuario();
		model.addAttribute("nombreUsuario", nombreUsuario);
		// Sacar si es administrador
		model.addAttribute("isAdmin", usuario.getRol());
	
		// Sacar lista de usuario
		List<UsuarioDTO> usuarios = usuarioServicio.buscarTodos();
        model.addAttribute("usuarios", usuarios);
		return "administracion";
	}
	
	@GetMapping("/privada/eliminar/{id}")
	public String eliminarUsuario(@PathVariable Long id, Model model, HttpServletRequest request) {
		Usuario usuario = usuarioServicio.buscarPorId(id);
		List<UsuarioDTO> usuarios = usuarioServicio.buscarTodos();
		if(usuario.getRol().equals("3")) {
			model.addAttribute("noSePuedeEliminar", "No se puede eliminar a un admin");
			model.addAttribute("usuarios", usuarios);
			return "administracion";
		}
		usuarioServicio.eliminar(id);
		return "redirect:/privada/administracion";
		
	}
}
