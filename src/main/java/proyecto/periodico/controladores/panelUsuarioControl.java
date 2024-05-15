package proyecto.periodico.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import proyecto.periodico.servicios.InterfazUsuario;
import proyecto.periodico.servicios.InterfazUsuarioToDTO;

import org.springframework.security.core.Authentication;

import proyecto.periodico.dao.Comentarios;
import proyecto.periodico.dao.Noticia;
import proyecto.periodico.dao.Usuario;
import proyecto.periodico.dto.UsuarioDTO;

@Controller
public class panelUsuarioControl {
	
	@Autowired
	private InterfazUsuario usuarioServicio;
	
    @Autowired
    private InterfazUsuarioToDTO usuarioToDTO;
	
	@GetMapping("/privada/panelUsuario")
	public String mostrarPanelUsuario(Authentication authentication, Model model) {
	    Usuario usuario = usuarioServicio.buscarPorEmail(authentication.getName());
	    List<Comentarios> comentarios = usuario.getUsuarioComentario();
	    List<Noticia> noticiasPublicadas = usuario.getNoticias();
	    System.out.println(noticiasPublicadas);
	    model.addAttribute("noticiasPublicadas", noticiasPublicadas);
	    model.addAttribute("comentarios", comentarios);
	    model.addAttribute("usuario", usuario);
	    return "panelUsuario";
	}
}
