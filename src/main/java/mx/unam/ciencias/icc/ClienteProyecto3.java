package mx.unam.ciencias.icc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import mx.unam.ciencias.icc.fx.ControladorInterfazVideojuegos;
import mx.unam.ciencias.icc.fx.ControladorTablaVideojuegos;

/**
 * ClientePractica10: Parte del cliente para práctica 10: Hilos de ejecución y
 * enchufes.
 */
public class ClienteProyecto3 extends Application {

    /* Vista de la interfaz videojuegos. */
    private static final String INTERFAZ_VIDEOJUEGOS_FXML =
        "fxml/interfaz-videojuegos.fxml";
    /* Vista de la tabla de videojuegos. */
    private static final String TABLA_VIDEOJUEGOS_FXML =
        "fxml/tabla-videojuegos.fxml";
    /* Ícono de la Facultad de Ciencias. */
    private static final String ICONO_CIENCIAS =
        "icons/ciencias.png";

    /**
     * Inicia la aplicación.
     * @param escenario la ventana principal de la aplicación.
     * @throws Exception si algo sale mal. Literalmente así está definido.
     */
    @Override public void start(Stage escenario) throws Exception {
        ClassLoader cl = getClass().getClassLoader();
        String url = cl.getResource(ICONO_CIENCIAS).toString();
        escenario.getIcons().add(new Image(url));
        escenario.setTitle("Administrador de Videojuegos");

        FXMLLoader cargador =
            new FXMLLoader(cl.getResource(TABLA_VIDEOJUEGOS_FXML));
        GridPane tablaVideojuego = (GridPane)cargador.load();
        ControladorTablaVideojuegos controladorTablaVideojuegos =
            cargador.getController();

        cargador = new FXMLLoader(cl.getResource(INTERFAZ_VIDEOJUEGOS_FXML));
        BorderPane interfazVideojuegos = (BorderPane)cargador.load();
        interfazVideojuegos.setCenter(tablaVideojuego);
        ControladorInterfazVideojuegos controladorInterfazVideojuegos =
            cargador.getController();
        controladorInterfazVideojuegos.setEscenario(escenario);
        controladorInterfazVideojuegos.setControladorTablaVideojuegos(
            controladorTablaVideojuegos);

        Scene escena = new Scene(interfazVideojuegos);
        escenario.setScene(escena);
        escenario.setOnCloseRequest(e -> controladorInterfazVideojuegos.salir(null));
        escenario.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
