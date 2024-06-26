package proyecto.periodico.controladores;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import proyecto.periodico.servicios.InterfazUsuario;
import proyecto.periodico.servicios.InterfazUsuarioToDTO;
import proyecto.periodico.servicios.InterfazUsuarioToDao;

import org.springframework.security.core.Authentication;

import proyecto.periodico.dao.Comentarios;
import proyecto.periodico.dao.Noticia;
import proyecto.periodico.dao.Usuario;
import proyecto.periodico.dto.UsuarioDTO;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Controller
public class panelUsuarioControl {
	
	@Autowired
	private InterfazUsuario usuarioServicio;
	
    @Autowired
    private InterfazUsuarioToDTO usuarioToDTO;
    
    @Autowired
    private InterfazUsuarioToDao usuarioToDAO;
    @Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/privada/panelUsuario")
	public String mostrarPanelUsuario(Authentication authentication, Model model, @RequestParam(required = false) Boolean edicion) {
	    Usuario usuario = usuarioServicio.buscarPorEmail(authentication.getName());
	    UsuarioDTO usuarioDTO = usuarioToDTO.usuarioToDto(usuario);
	    usuarioDTO.setRol(usuario.getRol());
	    if (edicion != null && edicion) {
			model.addAttribute("edicion", "Los datos han sido actualizados");

	    }
	    List<Comentarios> comentarios = usuario.getUsuarioComentario();
	    List<Noticia> noticiasPublicadas = usuario.getNoticias();
	    List<String> titulosNoticias = new ArrayList<>();

	    // Iterar sobre las noticias publicadas y extraer solo los títulos
	    for (Noticia noticia : noticiasPublicadas) {
	        titulosNoticias.add(noticia.getTituloNoticia());
	    }

	    // Añadir el ArrayList de títulos al modelo
	    model.addAttribute("titulosNoticiasPublicadas", titulosNoticias);
	    model.addAttribute("comentarios", comentarios);
	    model.addAttribute("usuario", usuarioDTO);
	    return "panelUsuario";
	}
	
	@PostMapping("/privada/guardarDatosUsuario")
	public String guardarDatosUsuario(@ModelAttribute UsuarioDTO usuarioDTO, Authentication authentication, Model model) {
	    Usuario usuarioExistente = usuarioServicio.buscarPorEmail(authentication.getName());

	    // Verificar si el nuevo email ya está en uso por otro usuario
	    if (!usuarioExistente.getEmailUsuario().equals(usuarioDTO.getEmailUsuario())) {
	        Usuario usuarioConEmail = usuarioServicio.buscarPorEmail(usuarioDTO.getEmailUsuario());
	        if (usuarioConEmail != null) {
	            model.addAttribute("error", "El email ya está en uso.");
	            return "panelUsuario";
	        }
	    }

	    // Mantener la contraseña existente si no se proporciona una nueva contraseña
	    if (StringUtils.isEmpty(usuarioDTO.getClaveUsuario())) {
	        usuarioDTO.setClaveUsuario(usuarioExistente.getClaveUsuario());
	    } else {
	        usuarioDTO.setClaveUsuario(passwordEncoder.encode(usuarioDTO.getClaveUsuario()));
	    }

	    // Mantener el estado de cuenta confirmada
	    usuarioDTO.setCuentaConfirmada(usuarioExistente.isCuentaConfirmada());

	    // Mantener valores existentes para campos que no están en el formulario
	    usuarioDTO.setId(usuarioExistente.getIdUsuario());
	    usuarioDTO.setEmailUsuario(usuarioExistente.getEmailUsuario()); // Email no editable
	    usuarioDTO.setRol(usuarioExistente.getRol());
	    usuarioDTO.setUsuarioComentario(usuarioExistente.getUsuarioComentario());

	    // Actualizar los campos del usuario con los datos del DTO
	    usuarioServicio.actualizarUsuario(usuarioDTO);
	    
	    return "redirect:/privada/panelUsuario?edicion=true"; // Redirigir de vuelta al panel de usuario
	}

}
