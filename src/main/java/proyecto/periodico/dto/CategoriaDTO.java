package proyecto.periodico.dto;

import java.util.List;
import java.util.Objects;

public class CategoriaDTO {
	// ATRIBUTOS
			private long idCategoria;
			private String tipoCategoria;
			private String descCategoria;
			/**
			 * @param idCategoria
			 * @param tipoCategoria
			 * @param descCategoria
			 */
			public CategoriaDTO(long idCategoria, String tipoCategoria, String descCategoria){
				super();
				this.idCategoria = idCategoria;
				this.tipoCategoria = tipoCategoria;
				this.descCategoria = descCategoria;
			}

			/**
			 * 
			 */
			public CategoriaDTO() {
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
				CategoriaDTO other = (CategoriaDTO) obj;
				return Objects.equals(descCategoria, other.descCategoria) && idCategoria == other.idCategoria
						&& Objects.equals(tipoCategoria, other.tipoCategoria);
			}

			@Override
			public String toString() {
				return "Categoria [idCategoria=" + idCategoria + ", tipoCategoria=" + tipoCategoria + ", descCategoria="
						+ descCategoria + "]";
			}
			
}
