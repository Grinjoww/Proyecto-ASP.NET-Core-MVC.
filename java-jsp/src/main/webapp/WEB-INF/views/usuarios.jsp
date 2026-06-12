<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listado de usuarios</title>
    <style>
        body { font-family: Arial, sans-serif; max-width: 760px;
               margin: 2rem auto; padding: 0 1rem }
        table { width: 100%; border-collapse: collapse; margin-top: 1rem }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left }
        th { background: #f4a000; color: white }
        tr:nth-child(even) { background: #fafafa }
        .btn-del { background: #c0392b; color: white; border: none;
                   padding: 6px 12px; border-radius: 4px; cursor: pointer }
        .btn-del:hover { background: #a5281b }
        .vacio { color: #777; margin-top: 1rem }
        a.top { text-decoration: none; color: #f4a000 }
    </style>
</head>
<body>
<h1>Ejercicio 2: Listado de usuarios (JSP + JSTL)</h1>
<p><a class="top" href="${pageContext.request.contextPath}/app/dashboard">&larr; Volver al dashboard</a></p>

<%-- Si no hay usuarios, mostrar mensaje; si los hay, recorrer con c:forEach --%>
<c:choose>
    <c:when test="${empty usuarios}">
        <p class="vacio">No hay usuarios registrados todavia.</p>
    </c:when>
    <c:otherwise>
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Email</th>
                <th>Edad</th>
                <th>Accion</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="u" items="${usuarios}">
                <tr>
                    <td><c:out value="${u.id}"/></td>
                    <td><c:out value="${u.nombre}"/></td>
                    <td><c:out value="${u.email}"/></td>
                    <td><c:out value="${u.edad}"/></td>
                    <td>
                        <%-- Eliminar mediante POST enviando el ID --%>
                        <form method="POST"
                              action="${pageContext.request.contextPath}/app/usuarios"
                              style="margin:0">
                            <input type="hidden" name="id" value="${u.id}">
                            <button type="submit" class="btn-del">Eliminar</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:otherwise>
</c:choose>
</body>
</html>
