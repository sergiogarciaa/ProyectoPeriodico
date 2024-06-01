package proyecto.periodico.servicios;

import java.util.ArrayList;

import java.util.List;

import org.springframework.stereotype.Service;

import proyecto.periodico.dao.Usuario;
import proyecto.periodico.dto.UsuarioDTO;

@Service
public class ImplementacionUsuarioToDao implements InterfazUsuarioToDao {

	@Override
	public Usuario usuarioToDao(UsuarioDTO usuarioDTO) {

		Usuario usuarioDao = new Usuario();

		try {
			usuarioDao.setIdUsuario(usuarioDTO.getId());
			usuarioDao.setNombreUsuario(usuarioDTO.getNombreUsuario());
			usuarioDao.setApellidosUsuario(usuarioDTO.getApellidosUsuario());
			usuarioDao.setEmailUsuario(usuarioDTO.getEmailUsuario());
			usuarioDao.setClaveUsuario(usuarioDTO.getClaveUsuario());
			usuarioDao.setTlfUsuario(usuarioDTO.getTlfUsuario());
			usuarioDao.setDniUsuario(usuarioDTO.getDniUsuario());
			usuarioDao.setRol(usuarioDTO.getRol());
			usuarioDao.setCuentaConfirmada(usuarioDTO.isCuentaConfirmada());
			usuarioDao.setUsuarioComentario(usuarioDTO.getUsuarioComentario());
			
			return usuarioDao;

		} catch (Exception e) {
			System.out.println(
					"\n[ERROR UsuarioToDaoImpl - toUsuarioDao()] - Al convertir usuarioDTO a usuarioDAO (return null): "
							+ e);
			return null;
		}

	}

	@Override
	public List<Usuario> listUsuarioToDao(List<UsuarioDTO> listaUsuarioDTO) {

		List<Usuario> listaUsuarioDao = new ArrayList<>();

		try {
			for (UsuarioDTO usuarioDTO : listaUsuarioDTO) {
				listaUsuarioDao.add(usuarioToDao(usuarioDTO));
			}

			return listaUsuarioDao;

		} catch (Exception e) {
			System.out.println(
					"\n[ERROR UsuarioToDaoImpl - toListUsuarioDao()] - Al convertir lista de usuarioDTO a lista de usuarioDAO (return null): "
							+ e);
		}
		return null;
	}

}
