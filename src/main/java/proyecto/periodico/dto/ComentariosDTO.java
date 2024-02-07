package proyecto.periodico.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import proyecto.periodico.dao.Noticia;
import proyecto.periodico.dao.Usuario;

public class ComentariosDTO {
    
    private long idComentario;
    private String descComentario;
    private String fchaPublicacionComentario;
    private List<Noticia> noticiaComentario;
    private List<Usuario> usuarioComentario;

    // Constructor
    public ComentariosDTO(long idComentario, String descComentario, String fchaPublicacionComentario) {
        this.idComentario = idComentario;
        this.descComentario = descComentario;
        this.fchaPublicacionComentario = fchaPublicacionComentario;
        this.noticiaComentario = new ArrayList<>();
        this.usuarioComentario = new ArrayList<>();
    }

    // Getters y Setters

    /**
	 * 
	 */
	public ComentariosDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(long idComentario) {
        this.idComentario = idComentario;
    }

    public String getDescComentario() {
        return descComentario;
    }

    public void setDescComentario(String descComentario) {
        this.descComentario = descComentario;
    }

    public String getFchaPublicacionComentario() {
        return fchaPublicacionComentario;
    }

    public void setFchaPublicacionComentario(String fchaPublicacionComentario) {
        this.fchaPublicacionComentario = fchaPublicacionComentario;
    }

    public List<Noticia> getNoticiaComentario() {
        return noticiaComentario;
    }

    public void setNoticiaComentario(List<Noticia> noticiaComentario) {
        this.noticiaComentario = noticiaComentario;
    }

    public List<Usuario> getUsuarioComentario() {
        return usuarioComentario;
    }

    public void setUsuarioComentario(List<Usuario> usuarioComentario) {
        this.usuarioComentario = usuarioComentario;
    }

    // HashCode, Equals y ToString

    @Override
    public int hashCode() {
        return Objects.hash(descComentario, fchaPublicacionComentario, idComentario, noticiaComentario, usuarioComentario);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ComentariosDTO other = (ComentariosDTO) obj;
        return Objects.equals(descComentario, other.descComentario)
                && Objects.equals(fchaPublicacionComentario, other.fchaPublicacionComentario)
                && Objects.equals(noticiaComentario, other.noticiaComentario)
                && Objects.equals(usuarioComentario, other.usuarioComentario) && idComentario == other.idComentario;
    }

    @Override
    public String toString() {
        return "ComentariosDTO [idComentario=" + idComentario + ", descComentario=" + descComentario
                + ", fchaPublicacionComentario=" + fchaPublicacionComentario + "]";
    }
}
