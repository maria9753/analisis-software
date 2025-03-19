public class Aplicacion{
    private Map<String,Ciudadano> ciudadanos;
    private Map<Ciudadano,Asociacion> asociaciones;
    private Map<String,Fundacion> fundaciones;

    public Aplicacion( ){
        /**Constructor vacío */
    }

    public void registrarCiudadano(Ciudadano ciudadano){
        if(ciudadanos.containsKey(ciudadano.getNif())){
            throw new IllegalArgumentException("NIF ya registrado");
        }
        ciudadanos.put(getNif(),ciudadano);
    }

    public void registrarFundacion(Fundacion fundacion){
        if(fundaciones.containsKey(fundacion.getCif())){
            throw new IllegalArgumentException("CIF ya registrado");
        }
        fundaciones.put(getCif(),fundacion);
    }

    public void verificarRepresentante(Asociacion asociacion){
        
    }


}