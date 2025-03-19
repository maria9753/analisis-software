public class Aplicacion{
    private Map<String,Ciudadano> ciudadanos;
    private List<Asociacion> asociaciones;
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
        for(Asociacion a: asociacion.getAsociaciones()){
            if(!a.getRepresentante().equals(asociacion.getRepresentante())){
                throw Exception;
            }
        }
    }

    public void validarNif(String nif){
        if(nif.size()!=9){
            throw Exception;
        }
       for(int i=0; i<8; i++){
            if(!Character.isDigit(nif.digitAt(i))){
                return false;
            }
       }
       if(!Character.isLetter(nif.charAt(i+1)));
    }

    public void validarCif(String cif){
        if(nif.size()!=8){
            throw Exception;
        }
       for(int i=0; i<7; i++){
            if(!Character.isDigit(cif.digitAt(i))){
                return false;
            }
       }
       if(!Character.isLetter(cif.charAt(i+1)));
    }

}