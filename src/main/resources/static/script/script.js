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
        confirmButtonText: 'OK',
        customClass: {
            confirmButton: 'btn btn-primary'  
        }
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
        title: 'Será redirigido',
        text: 'Deseas continuar?.',
        icon: 'info',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
       	cancelButtonText: 'No.',
        confirmButtonText: 'Sí.'
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

function confirmarEdicion(event) {
    const idUsuario = event.currentTarget.getAttribute("data-id");
    const rol = event.currentTarget.getAttribute("get-rol");
    confirmar().then(function (confirmado) {
   		if (idUsuario == "1")
   		{
		return Swal.fire({
        title: 'No se puede editar al superAdministrador',
        icon: 'error',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Aceptar'
    }).then((result) => {
        return result.isConfirmed;
    });
		   }
        if (confirmado && rol != "ROLE_4") {
            window.location.href = 'http://localhost:8080/privada/editar/' + idUsuario;
        }
    });
}

function confirmarRedireccion(event) {
	// Añadir comprobacion de rol para ver si esta logeado y poner SwalFire.
    const idNoticia = parseInt(event.currentTarget.getAttribute("data-idNoticia"), 10);
	const idCategoria = parseInt(event.currentTarget.getAttribute("data-idCategoria"), 10);
	const idUsuario = event.currentTarget.getAttribute("get-id");
confirmar().then(function (confirmado) {
   		if (idUsuario == null)
   		{
		return Swal.fire({
        title: 'Necesita iniciar sesión',
        icon: 'error',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Aceptar'
    }).then((result) => {
        return result.isConfirmed;
    });
		   }
        if (confirmado) {
            window.location.href = 'http://localhost:8080/auth/' + idCategoria + '/' + idNoticia;
        }
    });

    
}
function confirmarRedireccionCategoria(event) {
	// Añadir comprobacion de rol para ver si esta logeado y poner SwalFire.
	const idCategoria = parseInt(event.currentTarget.getAttribute("data-idCategoria"), 10);
	const idUsuario = event.currentTarget.getAttribute("get-id");
confirmar().then(function (confirmado) {
   		if (idUsuario == null)
   		{
		return Swal.fire({
        title: 'Necesita iniciar sesión',
        icon: 'error',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Aceptar'
    }).then((result) => {
        return result.isConfirmed;
    });
		   }
        if (confirmado) {
            window.location.href = 'http://localhost:8080/privada/ver/' + idCategoria;
        }
    });

    
}

function RedireccionCategoriaSinInicio(event) {
	// Añadir comprobacion de rol para ver si esta logeado y poner SwalFire.
	const idCategoria = parseInt(event.currentTarget.getAttribute("data-idCategoria"), 10);
	window.location.href = 'http://localhost:8080/privada/ver/' + idCategoria;  
}

function RedireccionNoticiaSinInicio(event) {
    const idNoticia = parseInt(event.currentTarget.getAttribute("data-idNoticia"), 10);
	const idCategoria = parseInt(event.currentTarget.getAttribute("data-idCategoria"), 10);
	window.location.href = 'http://localhost:8080/auth/' + idCategoria + '/' + idNoticia;
}

document.addEventListener('DOMContentLoaded', function () {
    const deleteButtons = document.querySelectorAll('.confirm-delete-btn');

    deleteButtons.forEach(button => {
        button.addEventListener('click', function (event) {
            event.preventDefault(); // Evitar que el enlace redirija inmediatamente

            const deleteUrl = this.getAttribute('data-href');
            confirmarBorrarTexto(deleteUrl);
        });
    });
});

function confirmarBorrarTexto(deleteUrl) {
    Swal.fire({
        title: '¿Estás seguro?',
        text: "Escriba 'borrar' para confirmar",
        input: 'text',
        inputPlaceholder: 'Escriba "borrar" aquí',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Eliminar',
        cancelButtonText: 'Cancelar',
        preConfirm: (inputValue) => {
            if (inputValue.toLowerCase() === 'borrar') {
                return true;
            } else {
                Swal.showValidationMessage('Debe escribir "borrar" para confirmar');
                return false;
            }
        }
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href = deleteUrl; // Redirigir a la URL de eliminación
        }
    });
}

