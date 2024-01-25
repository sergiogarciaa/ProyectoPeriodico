package proyecto.periodico.dao;

import java.util.Calendar;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Clase DAO (Data Access Object) que representa la tabla usuarios de la BBDD,
 * mapea con esta 1:1 y ejerce como modelo virtual de la tabla en la aplicación.
 */

// Rol: 1 = Usuario, 2 = Periodista, 3 = Administrador, 4 = SuperAdmin

@Entity
@Table(name = "usuarios", schema = "prdc_schema")
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


	// METODOS
	@Override
	public int hashCode() {
		return Objects.hash(apellidosUsuario, claveUsuario, dniUsuario, emailUsuario, expiracionToken, fchAltaUsuario,
				fchBajaUsuario, idUsuario, nombreUsuario, tlfUsuario, token, rol);
	}
	
	public boolean isAdmin() {
		if(getRol() == "ROLE_3")
			return true;
		else
			return false;
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
		return Objects.equals(apellidosUsuario, other.apellidosUsuario)
				&& Objects.equals(claveUsuario, other.claveUsuario) && Objects.equals(dniUsuario, other.dniUsuario)
				&& Objects.equals(emailUsuario, other.emailUsuario)
				&& Objects.equals(expiracionToken, other.expiracionToken)
				&& Objects.equals(fchAltaUsuario, other.fchAltaUsuario)
				&& Objects.equals(fchBajaUsuario, other.fchBajaUsuario) && idUsuario == other.idUsuario
				&& Objects.equals(nombreUsuario, other.nombreUsuario) && Objects.equals(tlfUsuario, other.tlfUsuario)
				&& Objects.equals(token, other.token) && Objects.equals(rol, other.rol) && Objects.equals(estado_suscripcion, other.estado_suscripcion);
	}

	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", dniUsuario=" + dniUsuario + ", nombreUsuario=" + nombreUsuario
				+ ", apellidosUsuario=" + apellidosUsuario + ", tlfUsuario=" + tlfUsuario + ", emailUsuario="
				+ emailUsuario + ", claveUsuario=" + claveUsuario + ", fchAltaUsuario=" + fchAltaUsuario
				+ ", fchBajaUsuario=" + fchBajaUsuario + ", estadoSuscripcion=" + estado_suscripcion + ", token=" + token + ", expiracionToken=" + expiracionToken
				+ ", rol=" + rol +"]";
	}

}
