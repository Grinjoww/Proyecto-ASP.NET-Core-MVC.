<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Practica 7 - Equipo H</title>
    <style>
        body { font-family: Arial, sans-serif; max-width: 620px;
               margin: 2rem auto; padding: 0 1rem }
        h1 { color: #f4a000 }
        ul { line-height: 2 }
        a { color: #1565c0; text-decoration: none }
        a:hover { text-decoration: underline }
        .eq { color: #555; font-size: 0.9rem }
    </style>
</head>
<body>
<h1>Practica 7: Java y JSP</h1>
<p class="eq">Aplicaciones Web - Ingenieria de Software - UTEQ - Equipo H</p>

<h2>Funcionalidades</h2>
<ul>
    <li><a href="registro">Registro de usuario</a> (Servlet + BCrypt)</li>
    <li><a href="login">Iniciar sesion</a> (CSRF + sesion)</li>
    <li><a href="contador">Contador de visitas</a> (Ejercicio 1: ciclo de vida)</li>
    <li><a href="xss?nombre=&lt;script&gt;alert('xss')&lt;/script&gt;">Demo XSS</a> (Ejercicio 4)</li>
    <li><a href="app/usuarios">Listado de usuarios</a> (requiere login - Ejercicio 2)</li>
</ul>

<p class="eq">
    CARVAJAL LOOR JOHAN STALIN &middot;
    FAJARDO MONTES MICHAEL XAVIER &middot;
    MARISCAL CABRERA JAIME JOSUE
</p>
</body>
</html>
