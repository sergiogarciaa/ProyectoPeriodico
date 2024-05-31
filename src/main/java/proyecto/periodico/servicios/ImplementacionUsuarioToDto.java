package proyecto.periodico.servicios;

import java.util.ArrayList;

import java.util.List;

import org.springframework.stereotype.Service;

import proyecto.periodico.dao.Usuario;
import proyecto.periodico.dto.UsuarioDTO;

@Service
public class ImplementacionUsuarioToDto implements InterfazUsuarioToDTO {
	@Override
	public UsuarioDTO usuarioToDto(Usuario u) {
		
		try {
			UsuarioDTO dto = new UsuarioDTO();
			dto.setNombreUsuario(u.getNombreUsuario());
			dto.setApellidosUsuario(u.getApellidosUsuario());
			dto.setDniUsuario(u.getDniUsuario());
			dto.setTlfUsuario(u.getTlfUsuario());
			dto.setEmailUsuario(u.getEmailUsuario());
			dto.setClaveUsuario(u.getClaveUsuario());
			dto.setToken(u.getToken());
			dto.setRol(u.getRol());
			dto.setExpiracionToken(u.getExpiracionToken());
			dto.setId(u.getIdUsuario());
			dto.setCuentaConfirmada(u.isCuentaConfirmada());
			return dto;
		} catch (Exception e) {
			System.out.println(
					"\n[ERROR UsuarioToDtoImpl - usuarioToDto()] - Error al convertir usuario DAO a usuarioDTO (return null): "
							+ e);
			return null;
		}
	}
	
	@Override
	public List<UsuarioDTO> listaUsuarioToDto(List<Usuario> listaUsuario){
		try {
				
			List<UsuarioDTO> listaDto = new ArrayList<>();
			for (Usuario u : listaUsuario) {
				listaDto.add(this.usuarioToDto(u));
			}
			return listaDto;

		} catch (Exception e) {
			System.out.println(
					"\n[ERROR UsuarioToDtoImpl - listauUsuarioToDto()] - Error al convertir lista de usuario DAO a lista de usuarioDTO (return null): "
							+ e);
		}
		return null;
	}

}
