public class Ciudadano extends Usuario{
    private String nif;
    private Set<Asociacion> asociaciones;

    public Ciudadano(String nombre, String contrasena, String nif){
        super(nombre, contrasena);
        this.nif=nif;
        this.asociaciones=new HashSet<>();
    }

    public List<Asociacion> getAsociaciones(){
        return this.asociaciones;
    }

    public void registarAsociacion(Asociacion asociacion){
        this.asociaciones.add(asociacion);
    }

    public String getNif(){
        return this.nif;
    }
}