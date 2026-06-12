<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro de usuario</title>
    <style>
        body { font-family: Arial, sans-serif; max-width: 520px;
               margin: 2rem auto; padding: 0 1rem }
        .error { color: #c0392b; background: #fdecea; padding: 10px 14px;
                 border-left: 4px solid #c0392b; margin: 12px 0; border-radius: 4px }
        .form-group { margin-bottom: 1rem }
        label { display: block; font-weight: bold; margin-bottom: 4px }
        input { width: 100%; padding: 8px; border: 1px solid #ccc;
                border-radius: 4px; box-sizing: border-box }
        button { width: 100%; padding: 10px; background: #f4a000;
                 border: none; border-radius: 4px; font-size: 1rem;
                 color: white; cursor: pointer; margin-top: 0.5rem }
        button:hover { background: #d08800 }
    </style>
</head>
<body>
<h1>Crear cuenta</h1>

<%-- Mostrar errores si los hay (usando JSTL, no scriptlet) --%>
<c:if test="${not empty errores}">
    <div class="error">
        <strong>Corrija los siguientes errores:</strong><br>
        <c:out value="${errores}"/>
    </div>
</c:if>

<form method="POST" action="${pageContext.request.contextPath}/registro">

    <div class="form-group">
        <label for="nombre">Nombre completo *</label>
        <%-- c:out escapa HTML: previene XSS en el value del input --%>
        <input type="text" id="nombre" name="nombre"
               value="<c:out value='${nombre}'/>" required
               maxlength="100" placeholder="Ej: Maria Garcia">
    </div>

    <div class="form-group">
        <label for="email">Correo electronico *</label>
        <input type="email" id="email" name="email"
               value="<c:out value='${email}'/>" required
               placeholder="usuario@dominio.com">
    </div>

    <div class="form-group">
        <label for="edad">Edad *</label>
        <input type="number" id="edad" name="edad"
               value="<c:out value='${edad}'/>"
               min="1" max="120" required>
    </div>

    <div class="form-group">
        <label for="clave">Contrasena (min. 8 caracteres) *</label>
        <input type="password" id="clave" name="clave" required minlength="8">
    </div>

    <div class="form-group">
        <label for="clave2">Confirmar contrasena *</label>
        <input type="password" id="clave2" name="clave2" required>
    </div>

    <button type="submit">Crear cuenta</button>
</form>

<p style="margin-top: 1rem">
    Ya tienes cuenta?
    <a href="${pageContext.request.contextPath}/login">Inicia sesion</a>
</p>
</body>
</html>
