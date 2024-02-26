package proyecto.periodico.controladores;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import proyecto.periodico.servicios.ImplementacionUsuarioToDto;
import proyecto.periodico.servicios.InterfazUsuario;
import proyecto.periodico.servicios.InterfazUsuarioToDTO;
import proyecto.periodico.servicios.InterfazUsuarioToDao;


@Controller
public class administracionControl {

	@Autowired
	private InterfazUsuario usuarioServicio;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	
	/**
	 * Maneja la solicitud HTTP GET para mostrar la página de administración de usuarios.
	 * Si el usuario autenticado tiene el rol "ROLE_3" o "ROLE_4", se muestra la lista de usuarios;
	 * de lo contrario, se redirige a la página de inicio.
	 * @param model Modelo utilizado para enviar la lista de usuarios a la vista.
	 * @param request HttpServletRequest para obtener información sobre la autenticación del usuario.
	 * @return La vista correspondiente ("administracion" o "index").
	 */
	
	@GetMapping("/privada/administracion")
	public String listadoUsuarios(Model model, HttpServletRequest request) {
		List<UsuarioDTO> usuarios = usuarioServicio.buscarTodos();
		System.out.println(usuarios);
		model.addAttribute("usuarios", usuarios);
		
		if (request.isUserInRole("ROLE_3") || request.isUserInRole("ROLE_4")) {
			return "administracion";
		}
		return "redirect:/privada/index";
	}
	
	/**
	 * Maneja la solicitud HTTP GET para eliminar a un usuario por su ID.
	 * Si el usuario autenticado tiene el rol "ROLE_4" y el usuario a eliminar también tiene ese rol,
	 * se muestra un mensaje indicando que no se puede eliminar a un administrador.
	 * En cualquier otro caso, se elimina al usuario por su ID y se redirige a la página de administración.
	 * @param id ID del usuario a eliminar.
	 * @param model Modelo utilizado para enviar mensajes y la lista actualizada de usuarios a la vista.
	 * @param request HttpServletRequest para obtener información sobre la autenticación del usuario.
	 * @return La vista correspondiente ("administracion" o redirección a "/privada/administracion").
	 */
	
	@GetMapping("/privada/eliminar/{id}")
	public String eliminarUsuario(@PathVariable Long id, Model model, HttpServletRequest request) {
		Usuario usuario = usuarioServicio.buscarPorId(id);
		List<UsuarioDTO> usuarios = usuarioServicio.buscarTodos();
		System.out.println(usuario);
		if (request.isUserInRole("ROLE_4") && usuario.getRol().equals("ROLE_4")) {
			model.addAttribute("noSePuedeEliminar", "No se puede eliminar a un admin");
			model.addAttribute("usuarios", usuarios);
			return "adminstracion";
		}

		if (request.isUserInRole("ROLE_1")) {
			return "redirect:/privada/index";	
		}
		else if (request.isUserInRole("ROLE_3") || request.isUserInRole("ROLE_4")) {
			usuarioServicio.eliminar(id);				
		}
		
		return "redirect:/privada/administracion";

	}

	/**
	 * Gestiona la solicitud HTTP Post para editar Usuario y actualizar los roles
	 * 
	 * @return Edicon de usuario y cambio de rol
	 */
	 @GetMapping("/privada/editar/{id}")
	    public String editarUsuario(@PathVariable Long id, Model model, HttpServletRequest request) {
		 	Usuario usuarioDAO = usuarioServicio.buscarPorId(id);
		 	InterfazUsuarioToDTO it = new ImplementacionUsuarioToDto();
		 	UsuarioDTO usuarioDTO = it.usuarioToDto(usuarioDAO);
		 	System.out.println(usuarioDTO.isCuentaConfirmada());
		 	// Comprobar si el usuario es superAdmin
		 	if (request.isUserInRole("ROLE_1")) {
				return "redirect:/privada/index";	
			}
		 	if ("ROLE_4".equals(usuarioDAO.getRol())) {
				// Si el usuario es superadmin, no permitir cambiar el rol
				model.addAttribute("noSePuedeCambiarRol", "No se puede cambiar el rol del superadmin.");
				return "administracion";
		 	} else {
	            model.addAttribute("usuarioDTO", usuarioDTO);
	            return "edicionAdmin";
	        }
	    }

	 @PostMapping("/guardarEdicion")
	 public String guardarEdicion(@ModelAttribute("usuarioDTO") UsuarioDTO usuarioDTO) {
		 
		 // Obtener el usuario existente de la base de datoS
	
	     Usuario usuarioExistente = usuarioServicio.buscarPorId(usuarioDTO.getId());

	     // Mantener la contraseña existente si no se proporciona una nueva contraseña
	     if (StringUtils.isEmpty(usuarioDTO.getClaveUsuario())) {
	         usuarioDTO.setClaveUsuario(usuarioExistente.getClaveUsuario());
	     } else {
	         // La contraseña se ha proporcionado en texto plano, cifrarla antes de actualizarla
	         usuarioDTO.setClaveUsuario(passwordEncoder.encode(usuarioDTO.getClaveUsuario()));
	     }
	     // Mantener el boolean de cuenta confirmada
	     if (!usuarioDTO.isCuentaConfirmada()) {
	    	    usuarioDTO.setCuentaConfirmada(usuarioExistente.isCuentaConfirmada());
	    	}
	  
	     // Guardar el usuario actualizado en la base de datos
	     usuarioServicio.actualizarUsuario(usuarioDTO);

	     return "redirect:/privada/index";
	 }

}
