package proyecto.periodico.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import proyecto.periodico.dao.Usuario;


public interface usuarioRepositorio extends JpaRepository<Usuario, Long>{
	
	/**
	 * Busca al primer usuario que tiene la dirección de correo electrónico especificada
	 * @param email La dirección de correo electrónico del usuario a buscar.
	 * @return El primer usuario encontrado con la dirección de correo electrónico
	 *         especificada o null en caso contrario.
	 */
	public Usuario findFirstByEmailUsuario(String email);
	
	/**
	 * Busca si un usuario tiene el DNI especificado.
	 * @param dni El DNI del usuario a buscar.
	 * @return true si el usuario tiene el DNI especificado, false en caso contrario.
	 */
	public boolean existsByDniUsuario(String dni);
	
	
	/**
	 * Busca un usuario por su token de recuperación.
	 * @param token de recuperacion del usuario que se le estableció cuando se inicio el proceso de recuperacion
	 * @return el usuario buscado por el token
	 */
	public Usuario findByToken(String token);

}
