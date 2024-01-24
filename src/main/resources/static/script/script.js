function revisarContraseña() {
	var contraseña = document.getElementById('claveUsuario').value;
    var confContraseña = document.getElementById('confirmarClaveUsuario').value;
    var mensajeContraseña = document.getElementById('mensajeContrasenya');

	if (contraseña === "" && confContraseña === "") {
		mensajeContraseña.textContent = 'No puede dejar campos vacíos';
		document.getElementById("btnRegistro").disabled = true;//deshabilita el boton		
	} else if (contraseña === confContraseña) {
        mensajeContraseña.textContent = 'Las contraseñas coinciden';
        mensajeContraseña.style.color = 'green';
        document.getElementById("btnRegistro").disabled = false;//habilita el boton
    } else {
        mensajeContraseña.textContent = 'Las contraseñas no coinciden';
        mensajeContraseña.style.color = 'red';
        document.getElementById("btnRegistro").disabled = true;//deshabilita el boton
    }
}

function mostrarNotificacion(titulo, mensaje, tipo) {
    Swal.fire({
        title: titulo,
        text: mensaje,
        icon: tipo,
        confirmButtonText: 'OK'
    });
}

function error(){
	Swal.fire({
	  icon: "error",
	  title: "Oops...",
	  text: "Something went wrong!",
	  footer: '<a href="#">Why do I have this issue?</a>'
	});
}


function confirmarLogout() {
    Swal.fire({
        title: '¿Estás seguro de que deseas cerrar sesión?',
        text: 'Serás redirigido a la página de bienvenida.',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, cerrar sesión'
    }).then((result) => {
        if (result.isConfirmed) {
            document.getElementById('logoutForm').submit();
        } else {
            console.log('Logout cancelado');
        }
    });
}
function confirmar() {
    return Swal.fire({
        title: '¿Estás seguro de que deseas eliminar?',
        text: 'Esta acción es irreversible.',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, eliminar.'
    }).then((result) => {
        return result.isConfirmed;
    });
}

function confirmarEliminar(event) {
    const idUsuario = event.currentTarget.getAttribute("data-id");
    const rol = event.currentTarget.getAttribute("get-rol");
    confirmar().then(function (confirmado) {
   		if (idUsuario == "1")
   		{
		return Swal.fire({
        title: 'No se puede borrar al administrador',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Aceptar'
    }).then((result) => {
        return result.isConfirmed;
    });
		   }
        if (confirmado && rol != "ROLE_3") {
            window.location.href = 'http://localhost:8080/privada/eliminar/' + idUsuario;
        }
    });
}