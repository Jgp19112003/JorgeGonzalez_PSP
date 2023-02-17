package model;

public class Usuario {

    private String nombre,password;

    public Usuario(String nombre, String password){

        this.nombre = nombre;
        this.password = password;
    }

    public String mostrarDatos(){

        return "Nombre: "+nombre+ " Password: "+password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
