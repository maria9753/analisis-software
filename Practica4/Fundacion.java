public class Fundacion extends Usuario {
    private String cif;

    public Fundacion (String nombre, String contrasena, String cif) {
        super(nombre, contrasena);
        this.cif = cif;
    }

    public String getCif() {
        return cif;
    }
}