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
import proyecto.periodico.dto.UsuarioDTO;
import proyecto.periodico.repositorios.noticiaRepositorio;
import proyecto.periodico.servicios.ImplementacionNoticia;
import proyecto.periodico.servicios.ImplementacionNoticiaToDTO;
import proyecto.periodico.servicios.ImplementacionUsuario;
import proyecto.periodico.servicios.InterfazCategoria;
import proyecto.periodico.servicios.InterfazNoticia;
import proyecto.periodico.servicios.InterfazNoticiaToDTO;
import proyecto.periodico.servicios.InterfazUsuario;

@Controller
public class loginRegistroControl {

	@Autowired
    private InterfazUsuario usuarioServicio;
    
    @Autowired
    private InterfazCategoria categoriaServicio;
    
    @Autowired
    private InterfazNoticia noticiaServicio;

	
	
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
	public String index1() {
		return "landing";
	}

	@GetMapping("/auth/landing")
	public String index() {
		Categoria categoria = categoriaServicio.crearCategoria();
		Usuario usuario = usuarioServicio.buscarPorEmail("admin@admin.com");
		System.out.println("CONTROLADOR ---- "+ categoria);
		Noticia noticia = noticiaServicio.noticiaCategoriaN(usuario, categoria);
		System.out.println(usuario.getNoticias());
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
		model.addAttribute("usuarioDTO", new UsuarioDTO());
		return "registro";
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

		if (nuevoUsuario != null && nuevoUsuario.getDniUsuario() != null) {
			// Si el usuario y el DNI no son null es que el registro se completo
			// correctamente
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
	 * Gestiona la solicitud HTTP GET para llevar a la página de home una vez
	 * logeado con exito.
	 * 
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
}
