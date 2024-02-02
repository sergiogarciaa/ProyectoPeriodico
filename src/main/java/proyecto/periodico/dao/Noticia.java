package proyecto.periodico.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "noticias", schema = "prdc_schema")
public class Noticia {
	
	// ATRIBUTOS
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "id_noticia", nullable = false)
		private long idNoticia;
		
		@Column(name = "titulo_noticia", nullable = false, length = 200)
		private String tituloNoticia;

		@Column(name = "desc_noticia", nullable = true, length = 4000)
		private String descNoticia;
		
		@Lob
		@Column(name = "imagen_noticia", nullable = true)
	    private byte[] foto;
		
		@Column(name = "requiere_suscripcion")
		private Boolean estado_suscripcion;
		
		@Column(name = "fch_publicacion", nullable = true, updatable = false)
		private String fchaPublicacion;
		
		@ManyToOne // FK de Categoria
	    @JoinColumn(name = "id_categoria_noticia", nullable = false)
	    private Categoria noticiaCategoria;

		
		@ManyToOne // FK de Usuario
	    @JoinColumn(name = "id_usuario_noticia", nullable = false)
	    private Usuario usuario;

		
		/**
		 * @param idNoticia
		 * @param tituloNoticia
		 * @param descNoticia
		 * @param resumenNoticia
		 * @param foto
		 * @param estado_suscripcion
		 * @param fchaPublicacion
		 * @param noticiaCategoria
		 * @param usuario
		 */
		public Noticia(long idNoticia, String tituloNoticia, String descNoticia, byte[] foto,
				Boolean estado_suscripcion, String fchaPublicacion, Categoria noticiaCategoria, Usuario usuario) {
			super();
			this.idNoticia = idNoticia;
			this.tituloNoticia = tituloNoticia;
			this.descNoticia = descNoticia;
			this.foto = foto;
			this.estado_suscripcion = estado_suscripcion;
			this.fchaPublicacion = fchaPublicacion;
			this.noticiaCategoria = noticiaCategoria;
			this.usuario = usuario;
		}

		// GETTER Y SETTER

		/**
		 * 
		 */
		public Noticia() {
			super();
			// TODO Auto-generated constructor stub
		}

		public long getIdNoticia() {
			return idNoticia;
		}

		public void setIdNoticia(long idNoticia) {
			this.idNoticia = idNoticia;
		}

		public String getTituloNoticia() {
			return tituloNoticia;
		}

		public void setTituloNoticia(String tituloNoticia) {
			this.tituloNoticia = tituloNoticia;
		}

		public String getDescNoticia() {
			return descNoticia;
		}

		public void setDescNoticia(String descNoticia) {
			this.descNoticia = descNoticia;
		}

		public byte[] getFoto() {
			return foto;
		}

		public void setFoto(byte[] foto) {
			this.foto = foto;
		}

		public Boolean getEstado_suscripcion() {
			return estado_suscripcion;
		}

		public void setEstado_suscripcion(Boolean estado_suscripcion) {
			this.estado_suscripcion = estado_suscripcion;
		}

		public String getFchaPublicacion() {
			return fchaPublicacion;
		}

		public void setFchaPublicacion(String string) {
			this.fchaPublicacion = string;
		}


		public Usuario getUsuario() {
			return usuario;
		}

		public void setUsuario(Usuario usuario) {
			this.usuario = usuario;
		}


		public Categoria getNoticiaCategoria() {
			return noticiaCategoria;
		}

		public void setNoticiaCategoria(Categoria noticiaCategoria) {
			this.noticiaCategoria = noticiaCategoria;
		}

		@Override
		public String toString() {
			return "Noticia [idNoticia=" + idNoticia + ", tituloNoticia=" + tituloNoticia + ", descNoticia="
					+ descNoticia + ", foto=" + Arrays.toString(foto) + ", estado_suscripcion=" + estado_suscripcion
					+ ", fchaPublicacion=" + fchaPublicacion + ", usuario=" + usuario
					+ "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Arrays.hashCode(foto);
			result = prime * result + Objects.hash(descNoticia, estado_suscripcion, fchaPublicacion,
					idNoticia, tituloNoticia, usuario);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Noticia other = (Noticia) obj;
			return  Objects.equals(descNoticia, other.descNoticia)
					&& Objects.equals(estado_suscripcion, other.estado_suscripcion)
					&& Objects.equals(fchaPublicacion, other.fchaPublicacion) && Arrays.equals(foto, other.foto)
					&& idNoticia == other.idNoticia && Objects.equals(tituloNoticia, other.tituloNoticia)
					&& Objects.equals(usuario, other.usuario);
		}
}
