package ec.edu.uteq.dao;

import ec.edu.uteq.model.Usuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO de Usuario usando SQLite (base de datos embebida en un archivo, sin servidor).
 *
 * SOLID-S (Responsabilidad Unica): esta clase SOLO accede a la base de datos.
 *
 * Demuestra la mitigacion de SQL Injection (OWASP A03):
 *   - buscarVulnerable(): metodo INSEGURO con concatenacion (solo para la demo del ataque).
 *   - existeEmail() / insertar() / listarTodos(): metodos SEGUROS con PreparedStatement.
 */
public class UsuarioDAO {

    // Ruta absoluta y escribible para el archivo de la BD (carpeta temporal del sistema).
    // Usar ruta absoluta evita problemas con el directorio de trabajo de Tomcat.
    private static final String DB_PATH =
            System.getProperty("java.io.tmpdir") + java.io.File.separator + "practica7.db";
    private static final String URL = "jdbc:sqlite:" + DB_PATH;

    // Cargar explicitamente el driver de SQLite al cargar la clase.
    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("No se encontro el driver de SQLite (sqlite-jdbc)", e);
        }
    }

    /**
     * Crea la tabla 'usuarios' si no existe e inserta datos de ejemplo.
     * Se llama una vez al iniciar la aplicacion.
     */
    public static void inicializar() {
        String ddl = "CREATE TABLE IF NOT EXISTS usuarios ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nombre TEXT NOT NULL, "
                + "email TEXT NOT NULL UNIQUE, "
                + "password_hash TEXT NOT NULL, "
                + "edad INTEGER NOT NULL)";
        try (Connection conn = getConexion();
             Statement st = conn.createStatement()) {
            st.execute(ddl);
        } catch (SQLException e) {
            throw new RuntimeException("Error al inicializar la BD", e);
        }
    }

    /* ============================================================
     * VULNERABLE: concatenacion directa de cadenas (NUNCA usar)
     * Se incluye SOLO para demostrar el ataque de SQL Injection.
     * ============================================================ */
    public static List<Usuario> buscarVulnerable(String email) throws SQLException {
        List<Usuario> resultado = new ArrayList<>();
        // PELIGRO: el valor del usuario se concatena directo en el SQL.
        String sql = "SELECT * FROM usuarios WHERE email = '" + email + "'";
        // Si email = ' OR '1'='1   -> devuelve TODOS los usuarios
        // Si email = '; DROP TABLE usuarios; --  -> destruye la tabla
        try (Connection conn = getConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) { // NUNCA hacer esto
            while (rs.next()) {
                resultado.add(mapear(rs));
            }
        }
        return resultado;
    }

    /* ============================================================
     * SEGURO: PreparedStatement (el ? nunca se interpreta como SQL)
     * ============================================================ */
    public static boolean existeEmail(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE email = ?";
        try (Connection conn = getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email); // Indice base 1
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    /** SEGURO: busqueda por email usando PreparedStatement. */
    public static Usuario buscarPorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE email = ?";
        try (Connection conn = getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
                return null;
            }
        }
    }

    /** SEGURO: insertar usuario usando PreparedStatement. */
    public static void insertar(String nombre, String email,
                                String hashClave, int edad) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre, email, password_hash, edad) "
                + "VALUES (?, ?, ?, ?)";
        try (Connection conn = getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, email);
            ps.setString(3, hashClave);
            ps.setInt(4, edad);
            ps.executeUpdate();
        }
    }

    /** SEGURO: listar todos los usuarios. */
    public static List<Usuario> listarTodos() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios ORDER BY id";
        try (Connection conn = getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    /** SEGURO: eliminar usuario por ID usando PreparedStatement. */
    public static void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection conn = getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    /** Mapea una fila del ResultSet a un objeto Usuario. */
    private static Usuario mapear(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("email"),
                rs.getString("password_hash"),
                rs.getInt("edad"));
    }

    private static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}