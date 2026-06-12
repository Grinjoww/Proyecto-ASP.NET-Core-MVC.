<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
    <style>
        body { font-family: Arial, sans-serif; max-width: 600px;
               margin: 2rem auto; padding: 0 1rem }
        .card { border: 1px solid #ddd; border-radius: 8px; padding: 1.5rem;
                box-shadow: 0 2px 6px rgba(0,0,0,0.06) }
        .saludo { font-size: 1.2rem }
        nav a { display: inline-block; margin-right: 1rem; margin-top: 1rem;
                color: #f4a000; text-decoration: none; font-weight: bold }
        .logout { color: #c0392b }
    </style>
</head>
<body>
<div class="card">
    <h1>Dashboard protegido</h1>
    <%-- El nombre viene de la sesion (solo accesible si AuthFilter dejo pasar) --%>
    <p class="saludo">
        Bienvenido, <strong><c:out value="${sessionScope.usuarioNombre}"/></strong>
    </p>
    <p>Tu email: <c:out value="${sessionScope.usuarioEmail}"/></p>
    <p>Esta ruta esta bajo <code>/app/*</code> y solo es accesible con sesion activa.</p>

    <nav>
        <a href="${pageContext.request.contextPath}/app/usuarios">Ver usuarios</a>
        <a class="logout" href="${pageContext.request.contextPath}/app/logout">Cerrar sesion</a>
    </nav>
</div>
</body>
</html>
