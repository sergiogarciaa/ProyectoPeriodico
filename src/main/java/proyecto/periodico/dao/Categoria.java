package proyecto.periodico.dao;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "categorias", schema = "prdc_schema")
public class Categoria {


	// ATRIBUTOS
			@Id
			@GeneratedValue(strategy = GenerationType.IDENTITY)
			@Column(name = "id_categoria", nullable = false)
			private long idCategoria;


			@Column(name = "tipo_categoria", nullable = false)
			private String tipoCategoria;
			
			@Column(name = "desc_categoria", nullable = true)
			private String descCategoria;
			@OneToMany(mappedBy = "noticiaCategoria")
		    private List<Noticia> noticiaCategoria;

			public Categoria() {
				super();
				// TODO Auto-generated constructor stub
			}
			
			/**
			 * @param idCategoria
			 * @param tipoCategoria
			 * @param descCategoria
			 * @param noticias
			 */
			public Categoria(long idCategoria, String tipoCategoria, String descCategoria) {
				super();
				this.idCategoria = idCategoria;
				this.tipoCategoria = tipoCategoria;
				this.descCategoria = descCategoria;
			}

			
			public long getIdCategoria() {
				return idCategoria;
			}

			public void setIdCategoria(long idCategoria) {
				this.idCategoria = idCategoria;
			}

			public String getTipoCategoria() {
				return tipoCategoria;
			}

			public void setTipoCategoria(String tipoCategoria) {
				this.tipoCategoria = tipoCategoria;
			}

			public String getDescCategoria() {
				return descCategoria;
			}

			public void setDescCategoria(String descCategoria) {
				this.descCategoria = descCategoria;
			}

			public List<Noticia> getNoticiaCategoria() {
				return noticiaCategoria;
			}

			public void setNoticiaCategoria(List<Noticia> noticiaCategoria) {
				this.noticiaCategoria = noticiaCategoria;
			}

			@Override
			public int hashCode() {
				return Objects.hash(descCategoria, idCategoria, tipoCategoria);
			}

			@Override
			public boolean equals(Object obj) {
				if (this == obj)
					return true;
				if (obj == null)
					return false;
				if (getClass() != obj.getClass())
					return false;
				Categoria other = (Categoria) obj;
				return Objects.equals(descCategoria, other.descCategoria) && idCategoria == other.idCategoria
						&& Objects.equals(tipoCategoria, other.tipoCategoria);
			}

			@Override
			public String toString() {
				return "Categoria [idCategoria=" + idCategoria + ", tipoCategoria=" + tipoCategoria + ", descCategoria="
						+ descCategoria + ", noticias=" + "]";
			}

}
