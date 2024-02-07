package proyecto.periodico.dto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import jakarta.servlet.http.HttpServletRequest;
import proyecto.periodico.dao.Comentarios;


/**
 * Clase DTO (Data Transfer Object) para pasar información entre capas 
 * para la gestión de usuarios
 */

//Rol: 1 = Usuario, 2 = Periodista, 3 = Administrador, 4 = SuperAdmin
public class UsuarioDTO {
	//ATRIBUTOS
	private long id;
	private String nombreUsuario;
	private String apellidosUsuario;
	private String dniUsuario;
	private String tlfUsuario;
	private String emailUsuario;
	private String claveUsuario;
	private String token;
	private Boolean estado_suscripcion;
	private boolean cuentaConfirmada;
	private String password;
	private String password2;
	private Calendar expiracionToken;
	private String rol = "ROLE_1";
	private List<NoticiaDTO> misNoticias = new ArrayList<>();
	private List<Comentarios> usuarioComentario;

	//CONSTRUCTORES
	public UsuarioDTO() {
	}

	public UsuarioDTO(String dniUsuario, String nombreUsuario, String apellidosUsuario, String tlfUsuario,
			String emailUsuario, String claveUsuario, Boolean estado_suscripcion, String rol) {
		this.dniUsuario = dniUsuario;
		this.nombreUsuario = nombreUsuario;
		this.apellidosUsuario = apellidosUsuario;
		this.tlfUsuario = tlfUsuario;
		this.emailUsuario = emailUsuario;
		this.claveUsuario = claveUsuario;
		this.estado_suscripcion = estado_suscripcion;
		this.rol = rol;
	}
	//GETTERS Y SETTERS
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDniUsuario() {
		return dniUsuario;
	}

	public void setDniUsuario(String dniUsuario) {
		this.dniUsuario = dniUsuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getApellidosUsuario() {
		return apellidosUsuario;
	}

	public void setApellidosUsuario(String apellidosUsuario) {
		this.apellidosUsuario = apellidosUsuario;
	}

	public String getTlfUsuario() {
		return tlfUsuario;
	}

	public void setTlfUsuario(String tlfUsuario) {
		this.tlfUsuario = tlfUsuario;
	}

	public String getEmailUsuario() {
		return emailUsuario;
	}

	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}

	public String getClaveUsuario() {
		return claveUsuario;
	}

	public void setClaveUsuario(String claveUsuario) {
		this.claveUsuario = claveUsuario;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public boolean isCuentaConfirmada() {
		return cuentaConfirmada;
	}

	public void setCuentaConfirmada(boolean cuentaConfirmada) {
		this.cuentaConfirmada = cuentaConfirmada;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	
	public Calendar getExpiracionToken() {
		return expiracionToken;
	}

	public void setExpiracionToken(Calendar expiracionToken) {
		this.expiracionToken = expiracionToken;
	}

	public String getRol() {
		return rol;
	}
	
	public void setRol(String rol) {
		this.rol = rol;
	}
	
	public Boolean getEstado_suscripcion() {
		return estado_suscripcion;
	}


	public void setEstado_suscripcion(Boolean estado_suscripcion) {
		this.estado_suscripcion = estado_suscripcion;
	}
	
	public List<NoticiaDTO> getNoticiasUsuario() {
		return misNoticias;
	}

	public void setNoticiasUsuario(List <NoticiaDTO> misNoticias) {
		this.misNoticias = misNoticias;
	}

	public List<Comentarios> getUsuarioComentario() {
		return usuarioComentario;
	}

	public void setUsuarioComentario(List<Comentarios> usuarioComentario) {
		this.usuarioComentario = usuarioComentario;
	}

	//METODOS
	@Override
	public int hashCode() {
		return Objects.hash(apellidosUsuario, claveUsuario, dniUsuario, emailUsuario, expiracionToken, nombreUsuario,
				password, password2, tlfUsuario, token, misNoticias);
	}
	
	public boolean isAdmin() {
		return "ROLE_3".equals(getRol());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioDTO other = (UsuarioDTO) obj;
		return Objects.equals(apellidosUsuario, other.apellidosUsuario)
				&& Objects.equals(claveUsuario, other.claveUsuario) && Objects.equals(dniUsuario, other.dniUsuario)
				&& Objects.equals(emailUsuario, other.emailUsuario)
				&& Objects.equals(expiracionToken, other.expiracionToken)
				&& Objects.equals(nombreUsuario, other.nombreUsuario)
				&& Objects.equals(misNoticias, other.misNoticias) && Objects.equals(password, other.password)
				&& Objects.equals(password2, other.password2) && Objects.equals(tlfUsuario, other.tlfUsuario)
				&& Objects.equals(token, other.token) && Objects.equals(estado_suscripcion, other.estado_suscripcion)
				&& cuentaConfirmada == other.cuentaConfirmada;
	}

	@Override
	public String toString() {
		return "UsuarioDTO [nombreUsuario=" + nombreUsuario + ", apellidosUsuario=" + apellidosUsuario + ", dniUsuario="
				+ dniUsuario + ", tlfUsuario=" + tlfUsuario + ", emailUsuario=" + emailUsuario + ", claveUsuario="
				+ claveUsuario + ", token=" + token + ", cuentaConfirmada=" + cuentaConfirmada + ", estadoSuscripcion=" + estado_suscripcion + ", password=" + password + ", password2=" + password2
				+ ", expiracionToken=" + expiracionToken + "]";
	}
    
	

}
