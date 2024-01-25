package proyecto.periodico.servicios;

import java.util.Calendar;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import proyecto.periodico.dao.Usuario;
import proyecto.periodico.dto.UsuarioDTO;
import proyecto.periodico.repositorios.usuarioRepositorio;

@Service
@Transactional
public class ImplementacionUsuario implements InterfazUsuario {

	@Autowired
	private usuarioRepositorio repositorio;

	@Autowired
	private InterfazUsuarioToDao toDao;

	@Autowired
	private InterfazUsuarioToDTO toDto;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public UsuarioDTO registrar(UsuarioDTO userDto) {

		try {
			// Comprueba si ya existe un usuario con el email que quiere registrar
			Usuario usuarioDaoByEmail = repositorio.findFirstByEmailUsuario(userDto.getEmailUsuario());

			if (usuarioDaoByEmail != null) {
				return null; // Si no es null es que ya está registrado
			}

			// Ahora se comprueba si hay un usuario por el DNI que quiere registrar
			boolean yaExisteElDNI = repositorio.existsByDniUsuario(userDto.getDniUsuario());

			if (yaExisteElDNI) {
				// Si es que ya hay un usuario con ese dni se setea a null para controlar el
				// error en controlador
				userDto.setDniUsuario(null);
				return userDto;
			}

			// Si llega a esta línea es que no existe el usuario con el email y el dni a
			// registrar
			userDto.setClaveUsuario(passwordEncoder.encode(userDto.getClaveUsuario()));
			Usuario usuarioDao = toDao.usuarioToDao(userDto);
			usuarioDao.setFchAltaUsuario(Calendar.getInstance());
			repositorio.save(usuarioDao);

			return userDto;
		} catch (IllegalArgumentException iae) {
			System.out.println("[IMPL-Usu][registrar] " + iae.getMessage());
		} catch (Exception e) {
			System.out.println("[IMPL-Usu][registrar] " + e.getMessage());
		}
		return null;
	}

	/*
	 * @Override public boolean iniciarResetPassConEmail(String emailUsuario) { try
	 * { Usuario usuarioExistente =
	 * repositorio.findFirstByEmailUsuario(emailUsuario);
	 * 
	 * if (usuarioExistente != null) { // Generar el token y establece la fecha de
	 * expiración String token =
	 * passwordEncoder.encode(RandomStringUtils.random(30)); Calendar
	 * fechaExpiracion = Calendar.getInstance();
	 * fechaExpiracion.add(Calendar.MINUTE, 10); // Actualizar el usuario con el
	 * nuevo token y la fecha de expiración usuarioExistente.setToken(token);
	 * usuarioExistente.setExpiracionToken(fechaExpiracion);
	 * 
	 * //Actualizar el usuario en la base de datos
	 * repositorio.save(usuarioExistente);
	 * 
	 * //Enviar el correo de recuperación String nombreUsuario =
	 * usuarioExistente.getNombreUsuario()+" "+usuarioExistente.getApellidosUsuario(
	 * ); //emailServicio.enviarEmailRecuperacion(emailUsuario, nombreUsuario,
	 * token);
	 * 
	 * return true;
	 * 
	 * } else { System.out.
	 * println("[Error iniciarRecuperacionConEmail() ]El usuario con email "+
	 * emailUsuario +" no existe"); return false; } } catch
	 * (IllegalArgumentException iae) {
	 * System.out.println("[Error UsuarioServicioImpl - registrar() ]" +
	 * iae.getMessage()); return false; } catch (Exception e) { System.out.
	 * println("[Error UsuarioServicioImpl - iniciarRecuperacionConEmail()]"+
	 * e.getMessage()); return false; } }
	 */

	/**
	 * Método para modificar la contraseña del usuario utilizando un token.
	 * 
	 * @param usuario Objeto UsuarioDTO que contiene el token y la nueva contraseña.
	 * @return true si la modificación de contraseña fue exitosa, false si el token
	 *         no se encontró.
	 */

	@Override
	public boolean modificarContraseñaConToken(UsuarioDTO usuario) {
		try {
			Usuario usuarioExistente = repositorio.findByToken(usuario.getToken());

			if (usuarioExistente != null) {
				String nuevaContraseña = passwordEncoder.encode(usuario.getPassword());
				usuarioExistente.setClaveUsuario(nuevaContraseña);
				usuarioExistente.setToken(null); // Se setea a null para invalidar el token ya consumido al cambiar de
													// password
				repositorio.save(usuarioExistente);

				return true;
			}

			return false;
		} catch (Exception e) {
			System.out.println("[IMPL-Usu][modificarContraseñaConToken] " + e.getMessage());
			return false;
		}
	}

	/**
	 * Método para obtener un usuario por su token.
	 * 
	 * @param token Token del usuario que se desea obtener.
	 * @return UsuarioDTO si se encuentra el usuario, null si no existe un usuario
	 *         con el token proporcionado.
	 */

	@Override
	public UsuarioDTO obtenerUsuarioPorToken(String token) {
		try {
			Usuario usuarioExistente = repositorio.findByToken(token);

			if (usuarioExistente != null) {
				UsuarioDTO usuario = toDto.usuarioToDto(usuarioExistente);
				return usuario;
			} else {
				System.out.println("No existe el usuario con el token " + token);
				return null;
			}
		} catch (Exception e) {
			System.out.println("[IMPL-Usu][obtenerUsuarioPorToken] " + e.getMessage());
			return null;
		}

	}

	/**
	 * Método para buscar un usuario por su dirección de correo electrónico.
	 * 
	 * @param email Dirección de correo electrónico del usuario que se desea buscar.
	 * @return Usuario si se encuentra el usuario, null si no existe un usuario con
	 *         el email proporcionado.
	 */

	@Override
	public Usuario buscarPorEmail(String email) {
		try {
			return repositorio.findFirstByEmailUsuario(email);
		} catch (Exception e) {
			System.out.println("[IMPL-Usu][buscarPorEmail] " + e.getMessage());
			return null;
		}
	}

	/**
	 * Método que ejecuta la creación de un usuario administrador con su rol de
	 * administrador.
	 */
	private void inicializarUsuarioAdmin() {
		try {
			// Comprueba si ya existe un usuario admin
			if (!repositorio.existsByNombreUsuario("admin")) {
				// Si no existe, crea un nuevo usuario con rol de administrador
				Usuario admin = new Usuario();
				admin.setNombreUsuario("admin");
				admin.setClaveUsuario(passwordEncoder.encode("admin"));
				admin.setDniUsuario("-");
				admin.setEmailUsuario("admin@admin.com");
				admin.setRol("ROLE_4");
				repositorio.save(admin);
				toDto.usuarioToDto(admin);
			}
		} catch (Exception e) {
			System.out.println("[IMPL-Usu][inicializarUsuarioAdmin] " + e.getMessage());
		}
	}

	/**
	 * Método que automatiza la creación de un usuario administrador que se ejecuta
	 * la primera vez que se despliega la aplicación.
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady() {
		try {
			inicializarUsuarioAdmin();
		} catch (Exception e) {
			System.out.println("[IMPL-Usu][onApplicationReady] " + e.getMessage());
		}
	}

	/**
	 * Método para eliminar un usuario por su ID.
	 * 
	 * @param id ID del usuario que se desea eliminar.
	 * @return Usuario que fue eliminado, null si no se encontró un usuario con el
	 *         ID proporcionado.
	 */
	@Override
	public Usuario eliminar(long id) {
		try {
			Usuario usuario = repositorio.findById(id).orElse(null);
			if (usuario != null) {
				repositorio.delete(usuario);
				System.out.println("Borrado con exito");
			}
			return usuario;
		} catch (Exception e) {
			System.out.println("[IMPL-Usu][eliminar] " + e.getMessage());
			return null;
		}

	}

	/**
	 * Método para editar un usuario a traves de su ID.
	 * 
	 * @param Recibe el objeto usuario que fue modificado a traves del formulario
	 *               (edicionAdmin.html)
	 * @return Usuario editado
	 */
	@Override
	public void actualizarUsuario(UsuarioDTO usuarioDTO) {
		try {
			// Convierte el DTO a entidad DAO
			Usuario usuarioDao = toDao.usuarioToDao(usuarioDTO);
			// Actualiza la información en la base de datos
			Usuario usuarioActualizado = repositorio.save(usuarioDao);
			// Actualiza el DTO con la información de la base de datos
			UsuarioDTO usuarioDtoActualizado = toDto.usuarioToDto(usuarioActualizado);

		} catch (IllegalArgumentException iae) {
			System.out.println("[IMPL-Usu][actualizarUsuario] " + iae.getMessage());
		} catch (Exception e) {
			System.out.println("[IMPL-Usu][actualizarUsuario] " + e.getMessage());
		}
	}

	/**
	 * Método para buscar un usuarioDTO a traves de su ID.
	 * 
	 * @param Recibe el id
	 * @return Objeto Usuario
	 */
	@Override
	public UsuarioDTO buscarDtoPorId(Long id) {
		try {
			Usuario usuarioDao = repositorio.findById(id).orElse(null);
			if (usuarioDao != null) {
				return toDto.usuarioToDto(usuarioDao);
			}
		} catch (Exception e) {
			System.out.println("[IMPL-Usu][buscarDtoPorId] " + e.getMessage());
		}
		return null;
	}

	@Override
	public List<UsuarioDTO> buscarTodos() {
		return toDto.listaUsuarioToDto(repositorio.findAll());
	}

	@Override
	public boolean buscarPorDni(String dni) {
		return repositorio.existsByDniUsuario(dni);
	}

	@Override
	public Usuario buscarPorId(long id) {
		return repositorio.findById(id).orElse(null);

	}

	@Override
	public boolean iniciarResetPassConEmail(String emailUsuario) {
		// TODO Auto-generated method stub
		return false;
	}

}
