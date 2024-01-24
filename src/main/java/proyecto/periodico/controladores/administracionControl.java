package proyecto.periodico.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import proyecto.periodico.dao.Usuario;
import proyecto.periodico.dto.UsuarioDTO;
import proyecto.periodico.servicios.InterfazUsuario;


@Controller
public class administracionControl {

	@Autowired
	private InterfazUsuario usuarioServicio;

	
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
		return "index";
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
		usuarioServicio.eliminar(id);
		return "redirect:/privada/administracion";

	}

	/**
	 * Gestiona la solicitud HTTP Post para actualizar los roles
	 * 
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
