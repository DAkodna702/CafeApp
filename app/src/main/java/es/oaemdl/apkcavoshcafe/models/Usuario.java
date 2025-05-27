package es.oaemdl.apkcavoshcafe.models;



public class Usuario  {
    private int id;
    private String Nombres;
    private String Correo;
    private String Passwordd;


    public Usuario(int id, String nombres, String correo, String passwordd) {
        this.id = id;
        Nombres = nombres;
        Correo = correo;
        Passwordd = passwordd;
    }

    public Usuario() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String nombres) {
        Nombres = nombres;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getPasswordd() {
        return Passwordd;
    }

    public void setPasswordd(String passwordd) {
        Passwordd = passwordd;
    }
}
