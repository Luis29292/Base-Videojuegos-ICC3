package mx.unam.ciencias.icc;

/**
 * Clase para bases de datos de estudiantes.
 */
public class BaseDeDatosVideojuegos
    extends BaseDeDatos<Videojuego, CampoVideojuego> {

    /**
     * Crea un estudiante en blanco.
     * @return un estudiante en blanco.
     */
    @Override public Videojuego creaRegistro() {
        // Aquí va su código.
	Videojuego a=new Videojuego(null,0,0.0,null,null);
	return a;
    }
}
