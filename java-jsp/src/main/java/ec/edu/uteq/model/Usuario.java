package ec.edu.uteq.model;

/**
 * Modelo de dominio Usuario (POJO).
 * Representa un usuario del sistema. Se usa tanto en la lista en memoria
 * para el listado JSP como en el DAO con SQLite.
 */
public class Usuario {

    private int id;
    private String nombre;
    private String email;
    private String passwordHash;
    private int edad;

    public Usuario() {
    }

    public Usuario(int id, String nombre, String email, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.edad = edad;
    }

    public Usuario(int id, String nombre, String email, String passwordHash, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.passwordHash = passwordHash;
        this.edad = edad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
