package proyecto.periodico.dao;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
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
		
		@Column(name = "titulo_noticia", nullable = false, length = 70)
		private String tituloNoticia;

		@Column(name = "desc_noticia", nullable = true, length = 100)
		private String descNoticia;
		
		@Lob
		@Column(name = "imagen_noticia", nullable = true)
	    private byte[] foto;
		
		@Column(name = "requiere_suscripcion")
		private Boolean estado_suscripcion;
		
		@Column(name = "fch_publicacion", nullable = true, updatable = false)
		private Calendar fchaPublicacion;
		
		@ManyToOne // FK de Categoria
	    @JoinColumn(name = "id_categoria", nullable = false)
	    private Categoria categoria;
		
		@ManyToOne // FK de Usuario
	    @JoinColumn(name = "id_usuario", nullable = false)
	    private Usuario usuario;

		/**
		 * @param idNoticia
		 * @param tituloNoticia
		 * @param descNoticia
		 * @param foto
		 * @param estado_suscripcion
		 * @param fchaPublicacion
		 * @param categoria
		 * @param usuario
		 */
		public Noticia(long idNoticia, String tituloNoticia, String descNoticia, byte[] foto,
				Boolean estado_suscripcion, Calendar fchaPublicacion, Categoria categoria, Usuario usuario) {
			super();
			this.idNoticia = idNoticia;
			this.tituloNoticia = tituloNoticia;
			this.descNoticia = descNoticia;
			this.foto = foto;
			this.estado_suscripcion = estado_suscripcion;
			this.fchaPublicacion = fchaPublicacion;
			this.categoria = categoria;
			this.usuario = usuario;
		}

		public Noticia() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		// GETTER Y SETTER

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

		public Calendar getFchaPublicacion() {
			return fchaPublicacion;
		}

		public void setFchaPublicacion(Calendar fchaPublicacion) {
			this.fchaPublicacion = fchaPublicacion;
		}

		public Categoria getCategoria() {
			return categoria;
		}

		public void setCategoria(Categoria categoria) {
			this.categoria = categoria;
		}

		public Usuario getUsuario() {
			return usuario;
		}

		public void setUsuario(Usuario usuario) {
			this.usuario = usuario;
		}

		@Override
		public String toString() {
			return "Noticia [idNoticia=" + idNoticia + ", tituloNoticia=" + tituloNoticia + ", descNoticia="
					+ descNoticia + ", foto=" + Arrays.toString(foto) + ", estado_suscripcion=" + estado_suscripcion
					+ ", fchaPublicacion=" + fchaPublicacion + ", categoria=" + categoria + ", usuario=" + usuario
					+ "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Arrays.hashCode(foto);
			result = prime * result + Objects.hash(categoria, descNoticia, estado_suscripcion, fchaPublicacion,
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
			return Objects.equals(categoria, other.categoria) && Objects.equals(descNoticia, other.descNoticia)
					&& Objects.equals(estado_suscripcion, other.estado_suscripcion)
					&& Objects.equals(fchaPublicacion, other.fchaPublicacion) && Arrays.equals(foto, other.foto)
					&& idNoticia == other.idNoticia && Objects.equals(tituloNoticia, other.tituloNoticia)
					&& Objects.equals(usuario, other.usuario);
		}
}