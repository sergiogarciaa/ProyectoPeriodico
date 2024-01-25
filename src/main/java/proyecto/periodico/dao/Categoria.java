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
			@OneToMany(mappedBy = "categoria")
		    private List<Noticia> noticias;

			/**
			 * @param idCategoria
			 * @param tipoCategoria
			 * @param descCategoria
			 */
			public Categoria(long idCategoria, String tipoCategoria, String descCategoria) {
				super();
				this.idCategoria = idCategoria;
				this.tipoCategoria = tipoCategoria;
				this.descCategoria = descCategoria;
			}

			public Categoria() {
				super();
				// TODO Auto-generated constructor stub
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
			 public List<Noticia> getNoticias() {
		        return noticias;
		    }

		    public void setNoticias(List<Noticia> noticias) {
		        this.noticias = noticias;
		    }
			
			@Override
			public String toString() {
				return "Categoria [idCategoria=" + idCategoria + ", tipoCategoria=" + tipoCategoria + ", descCategoria="
						+ descCategoria + "]";
			}

			@Override
			public int hashCode() {
				return Objects.hash(descCategoria, idCategoria, noticias, tipoCategoria);
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
						&& Objects.equals(noticias, other.noticias)
						&& Objects.equals(tipoCategoria, other.tipoCategoria);
			}
}
