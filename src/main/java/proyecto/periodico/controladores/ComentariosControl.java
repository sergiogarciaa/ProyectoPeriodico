package proyecto.periodico.controladores;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import proyecto.periodico.dao.Comentarios;
import proyecto.periodico.dao.Noticia;
import proyecto.periodico.dao.Usuario;
import proyecto.periodico.dto.ComentariosDTO;
import proyecto.periodico.repositorios.comentariosRepositorio;
import proyecto.periodico.repositorios.noticiaRepositorio;
import proyecto.periodico.servicios.InterfazComentario;
import proyecto.periodico.servicios.InterfazComentarioToDao;
import proyecto.periodico.servicios.InterfazNoticia;
import proyecto.periodico.servicios.InterfazNoticiaToDTO;
import proyecto.periodico.servicios.InterfazUsuario;

@Controller

public class ComentariosControl {
	
    @Autowired
    private InterfazNoticia noticiaServicio;
    
    @Autowired
    private InterfazComentario comentarioServicio;
    
    @Autowired
    private InterfazNoticiaToDTO noticiaToDto;
    
	@Autowired
    private InterfazUsuario usuarioServicio;
	
	@Autowired
	private comentariosRepositorio Crepositorio;

    @Autowired
    private InterfazComentarioToDao comentarioToDao;
    
    @PostMapping("/auth/{idCategoria}/{idNoticia}/comentario")
    public String agregarComentario(@PathVariable long idCategoria, @PathVariable long idNoticia, @ModelAttribute ComentariosDTO comentarioDTO, Authentication authentication, Model model) {
        // Recuperar la noticia
        Noticia noticia = noticiaServicio.buscarNoticiaPorID(idNoticia);
        Usuario usuario = usuarioServicio.buscarPorEmail(authentication.getName());
        System.out.println(comentarioDTO.getDescComentario());
        // Obtener la fecha y hora actual
        Calendar fechaActual = Calendar.getInstance();
        // Formatear la fecha y hora actual como una cadena
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        comentarioDTO.setFchaPublicacionComentario(formatoFecha.format(fechaActual.getTime()));
        // Crear el comentario
        Comentarios comentario = comentarioToDao.comentarioToDao(comentarioDTO);
        
        
        // Agregar la noticia al comentario
        comentario.getNoticiaComentario().add(noticia);
        // Agregar el comentario a la noticia
        noticia.getNoticiaComentario().add(comentario);
        // Agregar el comentario a la lista de comentarios de la noticia
        comentario.getUsuarioComentario().add(usuario);
        // Agregar el usuario al comentario
        usuario.getUsuarioComentario().add(comentario);
        // Guardar el comentario
        Crepositorio.save(comentario);
        
        // Redirigir a la página de visualización de la noticia
        return "redirect:/auth/" + idCategoria + "/" + idNoticia;
    }
    
    @PostMapping("/auth/{idCategoria}/{idNoticia}/comentario/{idComentario}/eliminar")
    public String eliminarComentario(@PathVariable long idCategoria, @PathVariable long idNoticia, @PathVariable long idComentario, Authentication authentication, Model model) {
        // Buscar el comentario por su ID
        Comentarios comentario = comentarioServicio.buscarComentarioPorID(idComentario);
        Noticia noticia = noticiaServicio.buscarNoticiaPorID(idNoticia);
        
        // Verificar si el comentario existe
        if (comentario != null) {
            // Obtener el usuario autenticado
            Usuario usuarioAutenticado = usuarioServicio.buscarPorEmail(authentication.getName());
            
            // Verificar si el usuario autenticado es el que creó el comentario
            for (Usuario usuario : comentario.getUsuarioComentario()) {
            	  if (usuario.getIdUsuario() == usuarioAutenticado.getIdUsuario() 
                  		|| usuarioAutenticado.getRol().equals("ROLE_3")
                  		|| usuarioAutenticado.getRol().equals("ROLE_4")) {
                	comentario.getNoticiaComentario().remove(noticia);
                    // Si coincide, eliminar el comentario
                	noticia.getNoticiaComentario().remove(comentario);
                	 // Agregar el comentario a la lista de comentarios de la noticia
                    comentario.getUsuarioComentario().remove(usuario);
                	// Agregar el usuario al comentario
                    usuario.getUsuarioComentario().remove(comentario); 
                    
                    Crepositorio.delete(comentario);
                    break; // Terminar la iteración una vez que se haya encontrado el usuario
                }
            }
        }

        // Redirigir a la página de visualización de la noticia
        return "redirect:/auth/" + idCategoria + "/" + idNoticia;
    }


}
