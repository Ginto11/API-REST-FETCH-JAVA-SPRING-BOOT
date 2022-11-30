// LISTA EN LA QUE AGREGAN LAS FILAS DE ACUERDO A CADA USUARIO
let lista = "";

// TABLA EN LA QUE SE AGREGAN LOS DATOS
const $tBodyTable = document.querySelector("table tbody");

// PERMITE ACTUALIZAR UN REGISTRO SOLO CUANDO ES TRUE
let isEditable = false;

// ES EL ESTUDIANTE QUE SE ENVIA CUANDO SE QUIERE ACTUALIZAR
let student = null;

// ID DE LA UNIVERSIDAD
let idUniversidad = 0;

document.addEventListener("DOMContentLoaded", () =>{

    //  AGREGA LOS ESTUDIANTES A LA TABLA
    ajax("http://localhost:8080/api/universidad/2",  {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        },
        success: (json) => listarEstudiantes(json, $tBodyTable),
        error: (error) => console.log(error)
    });
    
    // LLENA LOS SELECT CON LAS UNIVERSIDADES
    ajax("http://localhost:8080/api/universidades", {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization": localStorage.token
        },
        success: (json) => {
            llenarSelect(json);
            llenarSelectBuscador(json);
        },
        error: (error) => console.log(error)
    });

});

document.querySelector("form input[value='Enviar']").addEventListener("click", (e) =>  addEstudiante(e));

document.querySelector("form input[value='Actualizar']").addEventListener("click", (e) => actualizarEstudiante(e));

document.getElementById("btn-buscar").addEventListener("click",  () => buscarEstudiante());

document.querySelector("input[name='registrar']").addEventListener("click", (e) => crearUniversidad());

// CADA VEZ QUE HAY UN CAMBIO EN EL SELECT, TRAE LA LISTA DE ESTUDIANTES DE ACUERDO A LA UNIVERSIDAD
document.getElementById("selectBuscador").addEventListener("change", (e) =>{
    const selectBuscadorIdUniversidad = document.getElementById("selectBuscador").value;
    ajax(`http://localhost:8080/api/universidad/${selectBuscadorIdUniversidad}`,  {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization": localStorage.token
        },
        success: (json) => listarEstudiantes(json, $tBodyTable),
        error: (error) => console.log(error)
    });
});


//FUNCION QUE AGREGA UN ESTUDIANTE
async function addEstudiante(e){
    e.preventDefault();
    const selectID = document.getElementById("select").value;
    if(selectID === ""){
        alert("No ha seleccionado ninguna universidad.");
        return;
    }
    ajax(`http://localhost:8080/api/universidad/${selectID}/estudiantes`, {
        method: "POST",
        headers:{
            "Content-Type": "application/json",
            "Authorization": localStorage.token
        },
        body: JSON.stringify({
            nombre: document.getElementById("nombre").value,
            apellido: document.getElementById("apellido").value,
            edad: document.getElementById("edad").value
        }),
        success: (res) => location.reload(),
        error: (err) => console.log(err)
        });

}


//FUNCION QUE LLENA LA TABLA
function listarEstudiantes(json, $tBodyTable){
    lista = "";
    json.forEach(element => {
        lista += `
        <tr>
            <td> ${element.id} </td>
            <td> ${element.nombre} </td>
            <td> ${element.apellido} </td>
            <td> ${element.edad} </td>
            <td> 
                <button onclick="eliminarEstudiante( ${element.id} )"> Eliminar </button>
            </td>
        </tr>`;
    });

    $tBodyTable.innerHTML = lista;
}




//FUNCION QUE ELIMINA UN ESTUDIANTE
function eliminarEstudiante(id){
    let indice = document.getElementById("selectBuscador").selectedIndex;
    let opcion = document.getElementById("selectBuscador").options[indice];
    idUniversidad = opcion.value;
    fetch(`http://localhost:8080/api/universidad/${idUniversidad}/estudiante/${id}`, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
            "Authorization": localStorage.token
        }
    })
    .then(() =>{
        alert("Estudiante asociado al id " + id + " eliminado");
        location.reload();
    });
};

// LLENA EL SELECT DEL FORM
function llenarSelect(json){
    json.forEach(element => {
        const option = document.createElement("option");
        option.innerHTML = element.nombre;
        option.value = element.idUniversidad;
        
        document.getElementById("select").appendChild(option);
    });
}

// LLENA EL SELECTBUSCADOR
function llenarSelectBuscador(json){
    json.forEach(element => {
        const option = document.createElement("option");
        option.innerHTML = element.nombre;
        option.value = element.idUniversidad;
        
        document.getElementById("selectBuscador").appendChild(option);
    });
}

// FUNCION QUE BUSCA UN ESTUDIANTE
function buscarEstudiante(){
    if(document.getElementById("idEstudiante").value === "") return alert("Para buscar debes colocar un id");
    const idUniversidad = document.getElementById("selectBuscador").value;
    
    const idEstudiante = document.getElementById("idEstudiante").value;
    
    ajax(`http://localhost:8080/api/universidad/${idUniversidad}/estudiante/${idEstudiante}`, {
        method: "GET",
        headers: {
            "Content-type": "application/json",
            "Authorization": localStorage.token
        },
        success: (estudiante) =>  {
            if(estudiante.status === 500){
                alert(estudiante.message);
                return;
            }
            else {
                parsearEstudiante(estudiante);
                llenarForm(estudiante);
            }
            
        },
        error: (e) => alert(e)
    });
};

// FUNCION QUE PARSEA UN ESTUDIANTE EN LA TABLA
function parsearEstudiante(estudiante){
        lista = "";
        lista += `
            <tr>
                <td> ${estudiante.id} </td>
                <td> ${estudiante.nombre} </td>
                <td> ${estudiante.apellido} </td>
                <td> ${estudiante.edad} </td>
                <td> 
                    <button onclick="eliminarEstudiante( ${estudiante.id} )"> Eliminar </button>
                </td>
            </tr>
        `;

    $tBodyTable.innerHTML = lista;
}

// FUNCION QUE LLENA EL FORM PARA ACTUALIZAR ESTUDIANTE
function llenarForm(estudiante){
    isEditable= true;
    student = estudiante;
    idUniversidad = document.getElementById("selectBuscador").selectedIndex + 1;
    document.getElementById("select").disabled = "true";
    document.getElementById("titulo").innerHTML = "Actualizar Estudiante";
    document.getElementById("nombre").value = estudiante.nombre;
    document.getElementById("apellido").value = estudiante.apellido;
    document.getElementById("edad").value = estudiante.edad;
}

// FUNCION QUE ACTUALIZA EN FORM
function actualizarEstudiante(e){
    let indice = document.getElementById("selectBuscador").selectedIndex;
    let opcion = document.getElementById("selectBuscador").options[indice];
    idUniversidad = opcion.value;
    e.preventDefault();
    if(!isEditable){
        alert("Busque un registro para poder actualizar.");
        return;
    }
    ajax(`http://localhost:8080/api/universidad/${idUniversidad}/estudiante/${student.id}`, {
        method: "PUT",
        headers: {
            "Content-type": "application/json",
            "Authorization": localStorage.token
        },
        body: JSON.stringify({
            nombre:  document.getElementById("nombre").value,
            apellido:  document.getElementById("apellido").value,
            edad:  document.getElementById("edad").value
        }),
        success: (json) => {
            parsearEstudiante(json);
            limpiarCampos();
            
        },
        error: (err) => console.log(err)
    });
}

// FUNCION QUE CREA UNA UNIVERSIDAD
function crearUniversidad(){
    ajax("http://localhost:8080/api/universidades", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": localStorage.token
        },
        body: JSON.stringify({
            nombre: document.querySelector("input[name='universidad']").value
        }),
        success: (json) => {
            alert("Universidad registrada exitosamente");
            location.reload();
        },
        error: (er) => console.log(er)
    });
};

// FUNCION QUE LIMPIA LOS CAMPOS
function limpiarCampos(){
    document.getElementById("nombre").value  = "";
    document.getElementById("apellido").value  = "";
    document.getElementById("edad").value = "";
    document.getElementById("idEstudiante").value = "";
    document.getElementById("titulo").innerHTML = "Agregar Estudiante";
    document.getElementById("select").disabled = "";
}

// FUNCION QUE GUARDA EL TOKEN AUTENTICACION EL LOCALSTORAGE
function guardarToken(token){
    localStorage.token = token;
}


//FUNCION PERSONALIZADA ASYNC CON FETCH 
async function ajax(url, object){

    const { success, error } = object;

    try {

        const response = await fetch(url, object);

        const js = await response.json();
        
        await success(js);

    } catch (err) {
        error(err);
    }
}




