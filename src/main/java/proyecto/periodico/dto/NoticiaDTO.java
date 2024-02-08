package proyecto.periodico.dto;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import proyecto.periodico.dao.Categoria;
import proyecto.periodico.dao.Comentarios;
import proyecto.periodico.dao.Usuario;


public class NoticiaDTO {
	private long idNoticia;
	private String tituloNoticia;
	private String descNoticia;
	private String resumenNoticia;
	private String resumenNoticia2;
    private	String foto;
	private Boolean estado_suscripcion;
	private String fchaPublicacionMostrarWeb;
	private Categoria idCategoria;
	private Usuario idUsuario;
	private List<Comentarios> comentarios;

	/**
	 * @param idNoticia
	 * @param tituloNoticia
	 * @param descNoticia
	 * @param foto
	 * @param estado_suscripcion
	 * @param fchaPublicacion
	 */
	public NoticiaDTO(long idNoticia, String tituloNoticia, String descNoticia, String foto, Boolean estado_suscripcion,
			Calendar fchaPublicacion, String fchaPublicacionMostrarWeb,Categoria idCategoria, Usuario idUsuario, String resumenNoticia, String resumenNoticia2, List<Comentarios> comentarios) {
		super();
		this.idNoticia = idNoticia;
		this.tituloNoticia = tituloNoticia;
		this.descNoticia = descNoticia;
		this.foto = foto;
		this.estado_suscripcion = estado_suscripcion;
		this.idCategoria = idCategoria;
		this.idUsuario = idUsuario;
		this.fchaPublicacionMostrarWeb = fchaPublicacionMostrarWeb;
		this.resumenNoticia = resumenNoticia;
		this.resumenNoticia2 = resumenNoticia2;
		this.comentarios = comentarios;	
	}
	/**
	 * 
	 */
	public NoticiaDTO() {
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
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public Boolean getEstado_suscripcion() {
		return estado_suscripcion;
	}
	public void setEstado_suscripcion(Boolean estado_suscripcion) {
		this.estado_suscripcion = estado_suscripcion;
	}
	public Categoria getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(Categoria categoria) {
		this.idCategoria = categoria;
	}
	public Usuario getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Usuario idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getFchaPublicacionMostrarWeb() {
		return fchaPublicacionMostrarWeb;
	}
	public void setFchaPublicacionMostrarWeb(String fchaPublicacionMostrarWeb) {
		this.fchaPublicacionMostrarWeb = fchaPublicacionMostrarWeb;
	}
	public String getResumenNoticia() {
		return resumenNoticia;
	}
	public void setResumenNoticia(String resumenNoticia) {
		this.resumenNoticia = resumenNoticia;
	}
	public String getResumenNoticia2() {
		return resumenNoticia2;
	}
	public void setResumenNoticia2(String resumenNoticia2) {
		this.resumenNoticia2 = resumenNoticia2;
	}
	public List<Comentarios> getComentarios() {
		return comentarios;
	}
	public void setComentarios(List<Comentarios> comentarios) {
		this.comentarios = comentarios;
	}
	@Override
	public int hashCode() {
		return Objects.hash(comentarios, descNoticia, estado_suscripcion, fchaPublicacionMostrarWeb, foto, idCategoria,
				idNoticia, idUsuario, resumenNoticia, tituloNoticia, resumenNoticia2);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NoticiaDTO other = (NoticiaDTO) obj;
		return Objects.equals(comentarios, other.comentarios) && Objects.equals(descNoticia, other.descNoticia)
				&& Objects.equals(estado_suscripcion, other.estado_suscripcion)
				&& Objects.equals(fchaPublicacionMostrarWeb, other.fchaPublicacionMostrarWeb)
				&& Objects.equals(foto, other.foto) && Objects.equals(idCategoria, other.idCategoria)
				&& idNoticia == other.idNoticia && Objects.equals(idUsuario, other.idUsuario)
				&& Objects.equals(resumenNoticia, other.resumenNoticia) && Objects.equals(resumenNoticia2, other.resumenNoticia2)
				&& Objects.equals(tituloNoticia, other.tituloNoticia);
	}
	@Override
	public String toString() {
		return "NoticiaDTO [idNoticia=" + idNoticia + ", tituloNoticia=" + tituloNoticia + ", descNoticia="
				+ descNoticia + ", resumenNoticia=" + resumenNoticia + ", foto=" + foto + ", estado_suscripcion="
				+ estado_suscripcion + ", fchaPublicacionMostrarWeb=" + fchaPublicacionMostrarWeb + ", idCategoria="
				+ idCategoria + ", idUsuario=" + idUsuario + ", comentarios=" + comentarios + "]";
	}

}
