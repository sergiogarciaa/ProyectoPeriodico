package proyecto.periodico.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Clase DAO (Data Access Object) que representa la tabla usuarios de la BBDD,
 * mapea con esta 1:1 y ejerce como modelo virtual de la tabla en la aplicación.
 */

// Rol: 1 = Usuario, 2 = Periodista, 3 = Administrador, 4 = SuperAdmin

@Entity
@Table(name = "Usuarios", schema = "prdc_schema_usuarios")
public class Usuario {

	// ATRIBUTOS
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario", nullable = false)
	private long idUsuario;

	@Column(name = "dni_usuario", nullable = false, unique = true, length = 9)
	private String dniUsuario;

	@Column(name = "nombre_usuario", nullable = false, length = 70)
	private String nombreUsuario;

	@Column(name = "apellidos_usuario", nullable = true, length = 100)
	private String apellidosUsuario;

	@Column(name = "tlf_usuario", nullable = true, length = 9)
	private String tlfUsuario;

	@Column(name = "email_usuario", nullable = false, unique = true, length = 100)
	private String emailUsuario;

	@Column(name = "clave_usuario", nullable = false, length = 100)
	private String claveUsuario;

	@Column(name = "estado_suscripcion")
	private Boolean estado_suscripcion;
	
	@Column(name = "cuenta_confirmada", nullable = false, columnDefinition = "boolean default false")
	private boolean cuentaConfirmada;
	
	@Column(name = "fch_alta_usuario", nullable = true, updatable = false)
	private Calendar fchAltaUsuario ;

	@Column(name = "fch_baja_usuario", nullable = true, updatable = false)
	private Calendar fchBajaUsuario;

	@Column(name = "token_recuperacion", nullable = true, length = 100)
	private String token;

	@Column(name = "expiracion_token", nullable = true, length = 100)
	private Calendar expiracionToken;
	
	@Column(name = "rol", nullable = true)
	private String rol = "ROLE_1";
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Noticia> noticiaDeUsuario;
	
	@ManyToMany
	@JoinTable(name = "usuario_comentarios", schema = "prdc_schema",
			   joinColumns = @JoinColumn(name = "id_usuario"), 
			   inverseJoinColumns = @JoinColumn(name = "id_comentario"))
	private List<Comentarios> usuarioComentario = new ArrayList<>();

	// CONSTRUCTORES

	public Usuario(Long idUsuario ,String dni_usuario, String nombre_usuario, String apellidos_usuario, String tlf_usuario,
			String email_usuario, String clave_usuario, Boolean estado_suscripcion, String rol) {
		super();
		this.idUsuario = idUsuario;
		this.dniUsuario = dni_usuario;
		this.nombreUsuario = nombre_usuario;
		this.apellidosUsuario = apellidos_usuario;
		this.tlfUsuario = tlf_usuario;
		this.emailUsuario = email_usuario;
		this.claveUsuario = clave_usuario;
		this.estado_suscripcion = estado_suscripcion;
		this.rol = rol;
	}


	public Usuario() {
		super();
	}

	// GETTERS Y SETTERS
	public long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
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

	public Calendar getFchAltaUsuario() {
		return fchAltaUsuario;
	}

	public void setFchAltaUsuario(Calendar fchAltaUsuario) {
		this.fchAltaUsuario = fchAltaUsuario;
	}

	public Calendar getFchBajaUsuario() {
		return fchBajaUsuario;
	}

	public void setFchBajaUsuario(Calendar fchBajaUsuario) {
		this.fchBajaUsuario = fchBajaUsuario;
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
	
	public List<Noticia> getNoticias() {
        return noticiaDeUsuario;
    }

    public void setNoticias(List<Noticia> noticiaDeUsuario) {
        this.noticiaDeUsuario = noticiaDeUsuario;
    }
    
	public List<Comentarios> getUsuarioComentario() {
		return usuarioComentario;
	}


	public void setUsuarioComentario(List<Comentarios> usuarioComentario) {
		this.usuarioComentario = usuarioComentario;
	}


	// METODOS
//	@Override
//	public int hashCode() {
//		return Objects.hash(apellidosUsuario, claveUsuario, dniUsuario, emailUsuario, expiracionToken, fchAltaUsuario,
//				fchBajaUsuario, idUsuario, nombreUsuario, tlfUsuario, token, rol);
//	}
	
	public boolean isAdmin() {
		if(getRol() == "ROLE_3")
			return true;
		else
			return false;
	}
	

	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", dniUsuario=" + dniUsuario + ", nombreUsuario=" + nombreUsuario
				+ ", apellidosUsuario=" + apellidosUsuario + ", tlfUsuario=" + tlfUsuario + ", emailUsuario="
				+ emailUsuario + ", claveUsuario=" + claveUsuario + ", fchAltaUsuario=" + fchAltaUsuario
				+ ", fchBajaUsuario=" + fchBajaUsuario + ", cuentaConfirmada=" + cuentaConfirmada + ", estadoSuscripcion=" + estado_suscripcion + ", token=" + token + ", expiracionToken=" + expiracionToken
				+ ", rol=" + rol +"]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(cuentaConfirmada, apellidosUsuario, claveUsuario, dniUsuario, emailUsuario, estado_suscripcion,
				expiracionToken, fchAltaUsuario, fchBajaUsuario, idUsuario, nombreUsuario, noticiaDeUsuario, rol, tlfUsuario,
				token, usuarioComentario);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return cuentaConfirmada == other.cuentaConfirmada && Objects.equals(apellidosUsuario, other.apellidosUsuario)
				&& Objects.equals(claveUsuario, other.claveUsuario) && Objects.equals(dniUsuario, other.dniUsuario)
				&& Objects.equals(emailUsuario, other.emailUsuario)
				&& Objects.equals(estado_suscripcion, other.estado_suscripcion)
				&& Objects.equals(expiracionToken, other.expiracionToken)
				&& Objects.equals(fchAltaUsuario, other.fchAltaUsuario)
				&& Objects.equals(fchBajaUsuario, other.fchBajaUsuario) && idUsuario == other.idUsuario
				&& Objects.equals(nombreUsuario, other.nombreUsuario) && Objects.equals(noticiaDeUsuario, other.noticiaDeUsuario)
				&& Objects.equals(rol, other.rol) && Objects.equals(tlfUsuario, other.tlfUsuario)
				&& Objects.equals(token, other.token) && Objects.equals(usuarioComentario, other.usuarioComentario);
	}


}