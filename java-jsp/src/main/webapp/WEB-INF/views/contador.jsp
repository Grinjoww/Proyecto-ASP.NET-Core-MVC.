<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contador de visitas</title>
    <style>
        body { font-family: Arial, sans-serif; max-width: 480px;
               margin: 3rem auto; text-align: center; padding: 0 1rem }
        .contador { font-size: 4rem; color: #f4a000; font-weight: bold }
        .nota { color: #555; font-size: 0.95rem }
    </style>
</head>
<body>
<h1>Ejercicio 1: Ciclo de vida del Servlet</h1>
<p>Numero de visitas a este Servlet (compartido por todos los navegadores):</p>

<%-- El valor se obtiene del atributo del request mediante EL --%>
<div class="contador">${visitas}</div>

<p class="nota">
    Recarga la pagina o abrela en otro navegador: el contador sigue subiendo
    porque vive en el ServletContext (init() lo creo una sola vez).
</p>
</body>
</html>
