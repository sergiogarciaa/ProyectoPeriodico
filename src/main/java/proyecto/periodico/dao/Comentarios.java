package proyecto.periodico.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import jakarta.persistence.Table;


@Entity
@Table(name = "comentarios", schema = "prdc_schema")
public class Comentarios {
	
	// ATRIBUTOS
			@Id
			@GeneratedValue(strategy = GenerationType.IDENTITY)
			@Column(name = "id_comentario", nullable = false)
			private long idComentario;
			
			@ManyToMany(mappedBy = "noticiaComentario")
		    public List<Noticia> noticiaComentario = new ArrayList<>();
			
		    @ManyToMany(mappedBy = "usuarioComentario")
		    public List<Usuario> usuarioComentario = new ArrayList<>();
			
			@Column(name = "desc_comentario", nullable = true, length = 1000)
			private String descComentario;
			
			@Column(name = "fch_publicacion_comentario", nullable = true, updatable = false)
			private String fchaPublicacionComentario;

			/**
			 * @param idComentario
			 * @param noticias
			 * @param descComentario
			 * @param fchaPublicacionComentario
			 */
			public Comentarios(long idComentario, String descComentario,
					String fchaPublicacionComentario) {
				super();
				this.idComentario = idComentario;
				this.descComentario = descComentario;
				this.fchaPublicacionComentario = fchaPublicacionComentario;
				this.noticiaComentario = new ArrayList<>();
				this.usuarioComentario = new ArrayList<>();
			}

			/**
			 * 
			 */
			public Comentarios() {
				super();
				this.usuarioComentario = new ArrayList<>();
				this.noticiaComentario = new ArrayList<>();
			}

			// GETTER Y SETTER 
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

			// Hash
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
				Comentarios other = (Comentarios) obj;
				return Objects.equals(descComentario, other.descComentario)
						&& Objects.equals(fchaPublicacionComentario, other.fchaPublicacionComentario)
						&& Objects.equals(noticiaComentario, other.noticiaComentario) && Objects.equals(usuarioComentario, other.usuarioComentario)
						&& idComentario == other.idComentario;
			}


			// ToString
			@Override
			public String toString() {
				return "Comentarios [idComentario=" + idComentario + ", descComentario="
						+ descComentario + ", fchaPublicacionComentario=" + fchaPublicacionComentario + "]";
			}
	
	
}
