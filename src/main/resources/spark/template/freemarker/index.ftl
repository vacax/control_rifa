<!DOCTYPE html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <title>Control Rifa</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <style>
     .tamano{
         padding: 15px 32px;
         text-align: center;
         text-decoration: none;
         display: inline-block;
         font-size: 25px;
         margin-bottom: 25px;
     }
     .separacion{
         padding: 10px;
         margin-bottom: 10px;
     }
    </style>
</head>
<body>

<div class="container-fluid">
    <div class="jumbotron">
        <h1 class="display-4">Control de Rifa PUCMM</h1>
    </div>
    <div class="row">
        <button id="boton" type="button" class="btn btn-lg btn-block btn-primary tamano">Buscar Ganador</button>
        <div id="seleccionar" class="container-fluid btn-group-vertical d-none separacion">
            <button id="aprobado" type="button" class="btn btn-lg btn-block btn-success tamano">Aprobado</button>
            <button id="noPresente" type="button" class="btn btn-lg btn-block btn-danger tamano">No Presente</button>
            <button id="cancelar" type="button" class="btn btn-lg btn-block btn-warning tamano">Cancelar</button>
        </div>
    </div>


</div>

<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<#--<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>-->
<script>
    //abriendo el objeto para el websocket
    var webSocket;
    var tiempoReconectar = 5000;
    $(document).ready(function(){
        console.info("Iniciando Jquery -  Ejemplo WebServices");
        //

        //
        conectar();
        //
        $("#boton").click(function(){
            webSocket.send("inicio");
        });

        $("#aprobado").click(function(){
            webSocket.send("aprobado");
        });

        $("#noPresente").click(function(){
            webSocket.send("noPresente");
        });

        $("#cancelar").click(function(){
            webSocket.send("cancelar");
        });
    });
    /**
     *
     * @param mensaje
     */
    function recibirInformacionServidor(mensaje){
        console.log("Recibiendo del servidor: "+mensaje.data)
        //$("#mensajeServidor").append(mensaje.data);
        if(mensaje.data.trim() === "inicio"){
            $("#boton").prop('disabled', true);
        } else if(mensaje.data.trim() === "pantalla_ganadores"){
            $("#boton").prop('disabled', false);
            $("#boton").addClass('d-none');
            $("#seleccionar").removeClass('d-none');
        }else if(mensaje.data === "completado"){
            $("#boton").removeClass('d-none');
            $("#seleccionar").addClass('d-none');
        }
    }

    function conectar() {
        webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/ws");
        //indicando los eventos:
        webSocket.onmessage = function(data){recibirInformacionServidor(data);};
        webSocket.onopen  = function(e){ console.log("Conectado - status "+this.readyState); };
        webSocket.onclose = function(e){
            console.log("Desconectado - status "+this.readyState);
        };
    }
    function verificarConexion(){
        if(!webSocket || webSocket.readyState == 3){
            conectar();
        }
    }
    setInterval(verificarConexion, tiempoReconectar); //para reconectar.
</script>
</body>
</html>