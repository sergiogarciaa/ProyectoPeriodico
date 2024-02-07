package proyecto.periodico.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import proyecto.periodico.dao.Comentarios;
import proyecto.periodico.dao.Noticia;
import proyecto.periodico.dao.Usuario;
import proyecto.periodico.dto.ComentariosDTO;
import proyecto.periodico.repositorios.comentariosRepositorio;
import proyecto.periodico.repositorios.noticiaRepositorio;
import proyecto.periodico.servicios.InterfazComentarioToDao;
import proyecto.periodico.servicios.InterfazNoticia;
import proyecto.periodico.servicios.InterfazNoticiaToDTO;
import proyecto.periodico.servicios.InterfazUsuario;

@Controller

public class ComentariosControl {
	
    @Autowired
    private InterfazNoticia noticiaServicio;
    
    @Autowired
    private InterfazNoticiaToDTO noticiaToDto;
    
	@Autowired
    private InterfazUsuario usuarioServicio;
	
	@Autowired
	private comentariosRepositorio Crepositorio;

    @Autowired
    private InterfazComentarioToDao comentarioToDao;
    
    @PostMapping("/auth/{idCategoria}/{idNoticia}/comentario")
    public String agregarComentario(@PathVariable long idCategoria, @PathVariable long idNoticia, @ModelAttribute ComentariosDTO comentarioDTO, Authentication authentication) {
        // Recuperar la noticia
        Noticia noticia = noticiaServicio.buscarNoticiaPorID(idNoticia);
        Usuario usuario = usuarioServicio.buscarPorEmail(authentication.getName());
        System.out.println(comentarioDTO.getDescComentario());
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


}
