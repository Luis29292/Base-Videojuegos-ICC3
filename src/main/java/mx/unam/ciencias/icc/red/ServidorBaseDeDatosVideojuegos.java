package mx.unam.ciencias.icc.red;

import java.io.IOException;
import mx.unam.ciencias.icc.BaseDeDatos;
import mx.unam.ciencias.icc.BaseDeDatosVideojuegos;
import mx.unam.ciencias.icc.CampoVideojuego;
import mx.unam.ciencias.icc.Videojuego;

/**
 * Clase para servidores de bases de datos de videojuegos.
 */
public class ServidorBaseDeDatosVideojuegos
    extends ServidorBaseDeDatos<Videojuego> {

    /**
     * Construye un servidor de base de datos de videojuegos.
     * @param puerto el puerto dónde escuchar por conexiones.
     * @param archivo el archivo en el disco del cual cargar/guardar la base de
     *                datos.
     * @throws IOException si ocurre un error de entrada o salida.
     */
    public ServidorBaseDeDatosVideojuegos(int puerto, String archivo)
        throws IOException {
        super(puerto, archivo);
    }

    /**
     * Crea una base de datos de videojuegos.
     * @return una base de datos de videojuegos.
     */
    @Override public
    BaseDeDatos<Videojuego, CampoVideojuego> creaBaseDeDatos() {
        // Aquí va su código.
	BaseDeDatosVideojuegos bdd = new BaseDeDatosVideojuegos();
	return bdd;
    }
}
