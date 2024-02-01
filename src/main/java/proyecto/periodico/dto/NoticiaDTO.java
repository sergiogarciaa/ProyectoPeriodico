package proyecto.periodico.dto;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;

import proyecto.periodico.dao.Categoria;
import proyecto.periodico.dao.Usuario;


public class NoticiaDTO {
	private long idNoticia;
	private String tituloNoticia;
	private String descNoticia;
	private String resumenNoticia;
    private byte[] foto;
	private Boolean estado_suscripcion;
	private Calendar fchaPublicacion;
	private String fchaPublicacionMostrarWeb;
	private Categoria idCategoria;
	private Usuario idUsuario;
	
	/**
	 * @param idNoticia
	 * @param tituloNoticia
	 * @param descNoticia
	 * @param foto
	 * @param estado_suscripcion
	 * @param fchaPublicacion
	 */
	public NoticiaDTO(long idNoticia, String tituloNoticia, String descNoticia, byte[] foto, Boolean estado_suscripcion,
			Calendar fchaPublicacion, String fchaPublicacionMostrarWeb,Categoria idCategoria, Usuario idUsuario, String resumenNoticia) {
		super();
		this.idNoticia = idNoticia;
		this.tituloNoticia = tituloNoticia;
		this.descNoticia = descNoticia;
		this.foto = foto;
		this.estado_suscripcion = estado_suscripcion;
		this.fchaPublicacion = fchaPublicacion;
		this.idCategoria = idCategoria;
		this.idUsuario = idUsuario;
		this.fchaPublicacionMostrarWeb = fchaPublicacionMostrarWeb;
		this.resumenNoticia = resumenNoticia;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(foto);
		result = prime * result
				+ Objects.hash(descNoticia, estado_suscripcion, fchaPublicacion, idNoticia, tituloNoticia, idCategoria, idUsuario);
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
		NoticiaDTO other = (NoticiaDTO) obj;
		return Objects.equals(descNoticia, other.descNoticia) && Objects.equals(resumenNoticia, other.resumenNoticia)
				&& Objects.equals(estado_suscripcion, other.estado_suscripcion)
				&& Objects.equals(fchaPublicacion, other.fchaPublicacion) && Arrays.equals(foto, other.foto)
				&& idNoticia == other.idNoticia && Objects.equals(tituloNoticia, other.tituloNoticia)&& Objects.equals(idCategoria, other.idCategoria)
				&& Objects.equals(fchaPublicacionMostrarWeb, other.fchaPublicacionMostrarWeb) && Objects.equals(idUsuario, other.idUsuario);
	}
	@Override
	public String toString() {
		return "NoticiaDTO [idNoticia=" + idNoticia + ", tituloNoticia=" + tituloNoticia + ", descNoticia="
				+ descNoticia + ", foto=" + Arrays.toString(foto) + ", estado_suscripcion=" + estado_suscripcion
				+ ", fchaPublicacionWeb=" + fchaPublicacionMostrarWeb + ", resumen =" + resumenNoticia + "]";
	}
}
