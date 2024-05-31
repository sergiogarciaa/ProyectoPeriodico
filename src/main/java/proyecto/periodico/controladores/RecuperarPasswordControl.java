package proyecto.periodico.controladores;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import proyecto.periodico.dto.UsuarioDTO;
import proyecto.periodico.servicios.InterfazUsuario;

/**
 * Clase que ejerce de controlador de la vista de iniciarRecuperacion/recuperar 
 * y que gestiona las solicitudes relacionadas con dichas vistas.
 */
@Controller
@RequestMapping("/auth")
public class RecuperarPasswordControl {

	@Autowired
	private InterfazUsuario usuarioServicio;
	

	

}