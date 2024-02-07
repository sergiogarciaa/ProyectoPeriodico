package proyecto.periodico.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import proyecto.periodico.dao.Categoria;
import proyecto.periodico.dao.Noticia;
import proyecto.periodico.dto.CategoriaDTO;
import proyecto.periodico.dto.NoticiaDTO;
import proyecto.periodico.servicios.InterfazCategoria;
import proyecto.periodico.servicios.InterfazCategoriasToDTO;
import proyecto.periodico.servicios.InterfazNoticia;
import proyecto.periodico.servicios.InterfazNoticiaToDAO;
import proyecto.periodico.servicios.InterfazNoticiaToDTO;
import proyecto.periodico.servicios.InterfazUsuario;

@Controller
public class NoticiasyCategoriasControl {

	@Autowired
    private InterfazUsuario usuarioServicio;
    
    @Autowired
    private InterfazCategoria categoriaServicio;
    
    @Autowired
    private InterfazNoticia noticiaServicio;
    
    @Autowired
    private InterfazNoticiaToDTO noticiaToDto;

    @Autowired
    private InterfazCategoriasToDTO categoriaToDto;
	
	
	@GetMapping("/auth/{idCategoria}/{idNoticia}")
    public String verNoticia(@PathVariable long idCategoria, @PathVariable long idNoticia, Model model, Authentication authentication) {
        // Aquí deberías cargar la noticia y la categoría correspondiente usando los ID proporcionados
        // por ejemplo, llamando a tu servicio NoticiaService
		 if (authentication == null || !authentication.isAuthenticated()) {
		        // El usuario no está autenticado, muestra la notificación y redirige al inicio de sesión
		        model.addAttribute("mensaje", "Debes estar logeado para acceder a esta página.");
		        return "login";
		    }
		 else {
		        Noticia noticia = noticiaServicio.buscarNoticiaPorID(idNoticia);
		        NoticiaDTO noticiaDTO = noticiaToDto.noticiaToDto(noticia);
		        Categoria categoria = noticia.getNoticiaCategoria();
		        List<CategoriaDTO> categoriasDTO = categoriaServicio.buscarTodas();
		        System.out.println(noticia);
		        // Puedes pasar la noticia y la categoría al modelo para que estén disponibles en la página
		        model.addAttribute("noticia", noticiaDTO);
		        model.addAttribute("categoria", categoria);
		        model.addAttribute("categorias", categoriasDTO);
		        return "verNoticia";
		 }
    }
	
	@GetMapping("/privada/ver/{idCategoria}")
		public String verCategoria(@PathVariable long idCategoria, Model model, Authentication authentication) {
		 if (authentication == null || !authentication.isAuthenticated()) {
		        // El usuario no está autenticado, muestra la notificación y redirige al inicio de sesión
		        model.addAttribute("mensaje", "Debes estar logeado para acceder a esta página.");
		        return "login";
		    }
		 else {
			 	CategoriaDTO categoriaDTO = categoriaToDto.categoriaToDTO(categoriaServicio.buscarPorId(idCategoria));
			 	 // Obtener noticias por categoría
	            List<NoticiaDTO> noticiasPorCategoria = noticiaServicio.buscarPorCategoria(idCategoria);
		       	// Este buscar todas para el header.
		        List<CategoriaDTO> categoriaTodasDTO = categoriaServicio.buscarTodas();
		        
		        for (NoticiaDTO noticiaDTO : noticiasPorCategoria) {
	       			 noticiaDTO.setResumenNoticia(noticiaServicio.resumirNoticia2(noticiaDTO.getDescNoticia()) + "...");
	       		}
		        // Puedes pasar la noticia y la categoría al modelo para que estén disponibles en la página
		        model.addAttribute("noticiasPorCategoria", noticiasPorCategoria);
		        model.addAttribute("categoria", categoriaDTO);
		        model.addAttribute("categoriaTodasDTO", categoriaTodasDTO);
		        return "verCategoria";
		 }
		}
}