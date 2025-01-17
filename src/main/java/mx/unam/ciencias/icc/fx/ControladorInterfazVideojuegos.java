package mx.unam.ciencias.icc.fx;

import java.io.IOException;
import java.net.Socket;
import java.util.Optional;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.unam.ciencias.icc.BaseDeDatosVideojuegos;
import mx.unam.ciencias.icc.Videojuego;
import mx.unam.ciencias.icc.EventoBaseDeDatos;
import mx.unam.ciencias.icc.Lista;
import mx.unam.ciencias.icc.red.Conexion;
import mx.unam.ciencias.icc.red.Mensaje;

/**
 * Clase para el controlador de la ventana principal de la aplicación.
 */
public class ControladorInterfazVideojuegos {

    /* Vista de la forma para conectarse. */
    private static final String CONECTAR_FXML =
        "fxml/forma-conectar.fxml";
    /* Vista de la forma para realizar búsquedas de videojuegos. */
    private static final String BUSQUEDA_VIDEOJUEGOS_FXML =
        "fxml/forma-busqueda-videojuegos.fxml";
    /* Vista de la forma para agregar/editar videojuegos. */
    private static final String VIDEOJUEGO_FXML =
        "fxml/forma-videojuego.fxml";

    /* Opción de menu para conectar. */
    @FXML private MenuItem menuConectar;
    /* Opción de menu para desconectar. */
    @FXML private MenuItem menuDesconectar;
    /* Opción de menu para agregar. */
    @FXML private MenuItem menuAgregar;
    /* Opción de menu para editar. */
    @FXML private MenuItem menuEditar;
    /* Opción de menu para eliminar. */
    @FXML private MenuItem menuEliminar;
    /* Opción de menu para buscar. */
    @FXML private MenuItem menuBuscar;
    /* El botón de agregar. */
    @FXML private Button botonAgregar;
    /* El botón de editar. */
    @FXML private Button botonEditar;
    /* El botón de eliminar. */
    @FXML private Button botonEliminar;
    /* El botón de buscar. */
    @FXML private Button botonBuscar;

    /* La ventana. */
    private Stage escenario;
    /* El controlador de tabla. */
    private ControladorTablaVideojuegos controladorTablaVideojuegos;
    /* La base de datos. */
    private BaseDeDatosVideojuegos bdd;
    /* La conexión del cliente. */
    private Conexion<Videojuego> conexion;
    /* Si hay o no conexión. */
    private boolean conectado;
    /* Número de videojuegos seleccionados. */
    private int seleccionados;

    /* Inicializa el controlador. */
    @FXML private void initialize() {
        setSeleccionados(0);
        setConectado(false);
        bdd = new BaseDeDatosVideojuegos();
        bdd.agregaEscucha((e, r1, r2) -> manejaEventoBaseDeDatos(e, r1, r2));
    }

    /* Conecta el cliente con el servidor. */
    @FXML private void conectar(ActionEvent evento) {
        if (conectado)
            return;

        String servidor = null;
        int puerto = -1;

        try {
            FXMLLoader cargador = new FXMLLoader();
            ClassLoader cl = getClass().getClassLoader();
            cargador.setLocation(cl.getResource(CONECTAR_FXML));
            AnchorPane pagina = (AnchorPane)cargador.load();

            Stage escenario = new Stage();
            escenario.initOwner(this.escenario);
            escenario.initModality(Modality.WINDOW_MODAL);
            escenario.setTitle("Conectar a servidor");
            Scene escena = new Scene(pagina);
            escenario.setScene(escena);

            ControladorFormaConectar controlador = cargador.getController();
            controlador.setEscenario(escenario);

            escenario.setOnShown(w -> controlador.defineFoco());
            escenario.setResizable(false);
            escenario.showAndWait();
            controladorTablaVideojuegos.enfocaTabla();
            if (!controlador.isAceptado())
                return;

            servidor = controlador.getServidor();
            puerto = controlador.getPuerto();
        } catch (IOException | IllegalStateException e) {
            e.printStackTrace();
            String mensaje =
                String.format("Ocurrió un error al tratar de " +
                              "cargar el diálogo '%s'.", CONECTAR_FXML);
            dialogoError("Error al cargar interfaz", mensaje);
            return;
        }

        try {
            Socket enchufe = new Socket(servidor, puerto);
            conexion = new Conexion<Videojuego>(bdd, enchufe);
            new Thread(() -> conexion.recibeMensajes()).start();
            conexion.agregaEscucha((c, m) -> mensajeRecibido(c, m));
            conexion.enviaMensaje(Mensaje.BASE_DE_DATOS);
        } catch (IOException ioe) {
            conexion = null;
            String mensaje =
                String.format("Ocurrió un error al tratar de " +
                              "conectarnos a %s:%d.\n", servidor, puerto);
            dialogoError("Error al establecer conexión", mensaje);
            return;
        }
        setConectado(true);
    }

    /* Desconecta el cliente del servidor. */
    @FXML private void desconectar(ActionEvent evento) {
        if (!conectado)
            return;
        setConectado(false);
        conexion.desconecta();
        conexion = null;
        bdd.limpia();
    }

    /* Cambia la interfaz gráfica dependiendo de hay o no conexión. */
    public void setConectado(boolean conectado) {
        this.conectado = conectado;
        menuConectar.setDisable(conectado);
        menuDesconectar.setDisable(!conectado);
        menuAgregar.setDisable(!conectado);
        menuBuscar.setDisable(!conectado);
        botonAgregar.setDisable(!conectado);
        botonBuscar.setDisable(!conectado);
    }

    /**
     * Termina el programa.
     * @param evento el evento que generó la acción.
     */
    @FXML public void salir(ActionEvent evento) {
        desconectar(evento);
        Platform.exit();
    }

    /* Agrega un nuevo videojuego. */
    @FXML private void agregaVideojuego(ActionEvent evento) {
        ControladorFormaVideojuego controlador =
            construyeDialogoVideojuego("Agregar videojuego", null);
        if (controlador == null)
            return;
        controlador.setVerbo("Agregar");
        controlador.getEscenario().showAndWait();
        controladorTablaVideojuegos.enfocaTabla();
        if (!controlador.isAceptado())
            return;
        bdd.agregaRegistro(controlador.getVideojuego());
        try {
            conexion.enviaMensaje(Mensaje.REGISTRO_AGREGADO);
            conexion.enviaRegistro(controlador.getVideojuego());
        } catch (IOException ioe) {
            dialogoError("Error con el servidor",
                         "No se pudo enviar un videojuego a agregar.");
        }
    }

    /* Edita un videojuego. */
    @FXML private void editaVideojuego(ActionEvent evento) {
        Videojuego videojuego =
            controladorTablaVideojuegos.getRenglonSeleccionado();
        ControladorFormaVideojuego controlador =
            construyeDialogoVideojuego("Editar videojuego", videojuego);
        if (controlador == null)
            return;
        controlador.setVerbo("Actualizar");
        controlador.getEscenario().showAndWait();
        controladorTablaVideojuegos.enfocaTabla();
        if (!controlador.isAceptado())
            return;
        try {
            conexion.enviaMensaje(Mensaje.REGISTRO_MODIFICADO);
            conexion.enviaRegistro(videojuego);
            conexion.enviaRegistro(controlador.getVideojuego());
        } catch (IOException ioe) {
            dialogoError("Error con el servidor",
                         "No se pudieron enviar videojuegos a modificar.");
        }
        bdd.modificaRegistro(videojuego, controlador.getVideojuego());
    }

    /* Elimina uno o varios videojuegos. */
    @FXML private void eliminaVideojuegos(ActionEvent evento) {
        String sujeto = (seleccionados == 1) ? "videojuego" : "videojuegos";
        String titulo = "Eliminar " + sujeto;
        String mensaje = (seleccionados == 1) ?
            "Esto eliminará al videojuego seleccionado" :
            "Esto eliminará a los videojuegos seleccionados";
        if (!dialogoDeConfirmacion(titulo, mensaje, "¿Está seguro?",
                                   "Eliminar " + sujeto,
                                   "Conservar " + sujeto))
            return;
        for (Videojuego videojuego :
                 controladorTablaVideojuegos.getSeleccion()) {
            bdd.eliminaRegistro(videojuego);
            try {
                conexion.enviaMensaje(Mensaje.REGISTRO_ELIMINADO);
                conexion.enviaRegistro(videojuego);
            } catch (IOException ioe) {
                dialogoError("Error con el servidor",
                             "No se pudo enviar un videojuego a eliminar.");
            }
        }
    }

    /* Busca videojuegos. */
    @FXML private void buscaVideojuegos(ActionEvent evento) {
        try {
            ClassLoader cl = getClass().getClassLoader();
            FXMLLoader cargador =
                new FXMLLoader(cl.getResource(BUSQUEDA_VIDEOJUEGOS_FXML));
            AnchorPane pagina = (AnchorPane)cargador.load();

            Stage escenario = new Stage();
            escenario.setTitle("Buscar videojuegos");
            escenario.initOwner(this.escenario);
            escenario.initModality(Modality.WINDOW_MODAL);
            Scene escena = new Scene(pagina);
            escenario.setScene(escena);

            ControladorFormaBusquedaVideojuegos controlador;
            controlador = cargador.getController();
            controlador.setEscenario(escenario);

            escenario.setOnShown(w -> controlador.defineFoco());
            escenario.setResizable(false);
            escenario.showAndWait();
            controladorTablaVideojuegos.enfocaTabla();
            if (!controlador.isAceptado())
                return;

            Lista<Videojuego> resultados =
                bdd.buscaRegistros(controlador.getCampo(),
                                   controlador.getValor());

            controladorTablaVideojuegos.seleccionaRenglones(resultados);
        } catch (IOException | IllegalStateException e) {
            String mensaje =
                String.format("Ocurrió un error al tratar de " +
                              "cargar el diálogo '%s'.",
                              BUSQUEDA_VIDEOJUEGOS_FXML);
            dialogoError("Error al cargar interfaz", mensaje);
        }
    }

    /* Muestra un diálogo con información del programa. */
    @FXML private void acercaDe(ActionEvent evento) {
        Alert dialogo = new Alert(AlertType.INFORMATION);
        dialogo.initOwner(escenario);
        dialogo.initModality(Modality.WINDOW_MODAL);
        dialogo.setTitle("Acerca de Administrador de Videojuegos.");
        dialogo.setHeaderText(null);
        dialogo.setContentText("Aplicación para administrar videojuegos.\n"  +
                               "Copyright © 2018 Facultad de Ciencias, UNAM.");
        dialogo.showAndWait();
        controladorTablaVideojuegos.enfocaTabla();
    }

    /**
     * Define el controlador de tabla.
     * @param controladorTablaVideojuegos el controlador de tabla.
     */
    public void setControladorTablaVideojuegos(ControladorTablaVideojuegos
                                               controladorTablaVideojuegos) {
        this.controladorTablaVideojuegos = controladorTablaVideojuegos;
        controladorTablaVideojuegos.agregaEscuchaSeleccion(
            n -> setSeleccionados(n));
    }

    /**
     * Define el escenario.
     * @param escenario el escenario.
     */
    public void setEscenario(Stage escenario) {
        this.escenario = escenario;
    }

    /* Maneja un evento de cambio en la base de datos. */
    private void manejaEventoBaseDeDatos(EventoBaseDeDatos evento,
                                         Videojuego videojuego1,
                                         Videojuego videojuego2) {
        switch (evento) {
        case BASE_LIMPIADA:
            Platform.runLater(() -> limpiaTabla());
            break;
        case REGISTRO_AGREGADO:
            Platform.runLater(() -> agregaVideojuego(videojuego1));
            break;
        case REGISTRO_ELIMINADO:
            Platform.runLater(() -> eliminaVideojuego(videojuego1));
            break;
        case REGISTRO_MODIFICADO:
            Platform.runLater(() -> reordenaTabla());
            break;
        }
    }

    /* Actualiza la interfaz dependiendo del número de renglones
     * seleccionados. */
    private void setSeleccionados(int seleccionados) {
        this.seleccionados = seleccionados;
        menuEliminar.setDisable(seleccionados == 0);
        menuEditar.setDisable(seleccionados != 1);
        botonEliminar.setDisable(seleccionados == 0);
        botonEditar.setDisable(seleccionados != 1);
    }

    /* Crea un diálogo con una pregunta que hay que confirmar. */
    private boolean dialogoDeConfirmacion(String titulo,
                                          String mensaje, String pregunta,
                                          String aceptar, String cancelar) {
        Alert dialogo = new Alert(AlertType.CONFIRMATION);
        dialogo.setTitle(titulo);
        dialogo.setHeaderText(mensaje);
        dialogo.setContentText(pregunta);

        ButtonType si = new ButtonType(aceptar);
        ButtonType no = new ButtonType(cancelar, ButtonData.CANCEL_CLOSE);
        dialogo.getButtonTypes().setAll(si, no);

        Optional<ButtonType> resultado = dialogo.showAndWait();
        controladorTablaVideojuegos.enfocaTabla();
        return resultado.get() == si;
    }

    /* Recibe los mensajes de la conexión. */
    private void mensajeRecibido(Conexion<Videojuego> conexion, Mensaje mensaje) {
        switch (mensaje) {
        case BASE_DE_DATOS:
            manejaBaseDeDatos(conexion);
            break;
        case REGISTRO_AGREGADO:
            manejaRegistroAlterado(conexion, mensaje);
            break;
        case REGISTRO_ELIMINADO:
            manejaRegistroAlterado(conexion, mensaje);
            break;
        case REGISTRO_MODIFICADO:
            manejaRegistroModificado(conexion);
            break;
        case DESCONECTAR:
            Platform.runLater(() -> desconectar(null));
            break;
        case DETENER_SERVICIO:
            // Se ignora.
            break;
        case ECO:
            // Se ignora.
            break;
        case INVALIDO:
            Platform.runLater(() -> dialogoError("Error con el servidor",
                                                 "Mensaje inválido recibido. " +
                                                 "Se finalizará la conexión."));
            break;
        }
    }

    /* Maneja el mensaje BASE_DE_DATOS. */
    private void manejaBaseDeDatos(Conexion<Videojuego> conexion) {
        try {
            conexion.recibeBaseDeDatos();
        } catch (IOException ioe) {
            String m = "No se pudo recibir la base de datos. " +
                "Se finalizará la conexión.";
            Platform.runLater(() -> dialogoError("Error con el servidor", m));
            return;
        }
    }

    /* Maneja los mensajes REGISTRO_AGREGADO y REGISTRO_MODIFICADO. */
    private void manejaRegistroAlterado(Conexion<Videojuego> conexion,
                                        Mensaje mensaje) {
        Videojuego e;
        try {
            e = conexion.recibeRegistro();
        } catch (IOException ioe) {
            String m = "No se pudo recibir un registro. " +
                "Se finalizará la conexión.";
            Platform.runLater(() -> dialogoError("Error con el servidor", m));
            return;
        }
        if (mensaje == Mensaje.REGISTRO_AGREGADO)
            bdd.agregaRegistro(e);
        else
            bdd.eliminaRegistro(e);
    }

    /* Maneja el mensaje REGISTRO_MODIFICADO. */
    private void manejaRegistroModificado(Conexion<Videojuego> conexion) {
        Videojuego e1, e2;
        try {
            e1 = conexion.recibeRegistro();
            e2 = conexion.recibeRegistro();
        } catch (IOException ioe) {
            String m = "No se pudieron recibir registros." +
                "Se finalizará la conexión.";
            Platform.runLater(() -> dialogoError("Error con el servidor", m));
            return;
        }
        bdd.modificaRegistro(e1, e2);
    }

    /* Construye un diálogo para crear o editar un videojuego. */
    private ControladorFormaVideojuego
    construyeDialogoVideojuego(String titulo,
                               Videojuego videojuego) {
        try {
            ClassLoader cl = getClass().getClassLoader();
            FXMLLoader cargador =
                new FXMLLoader(cl.getResource(VIDEOJUEGO_FXML));
            AnchorPane pagina = (AnchorPane)cargador.load();

            Stage escenario = new Stage();
            escenario.setTitle(titulo);
            escenario.initOwner(this.escenario);
            escenario.initModality(Modality.WINDOW_MODAL);
            Scene escena = new Scene(pagina);
            escenario.setScene(escena);

            ControladorFormaVideojuego controlador = cargador.getController();
            controlador.setEscenario(escenario);
            controlador.setVideojuego(videojuego);

            escenario.setOnShown(w -> controlador.defineFoco());
            escenario.setResizable(false);
            return controlador;
        } catch (IOException | IllegalStateException e) {
            String mensaje =
                String.format("Ocurrió un error al tratar de cargar " +
                              "el diálogo '%s'.", VIDEOJUEGO_FXML);
            dialogoError("Error al cargar interfaz", mensaje);
            return null;
        }
    }

    /* Muestra un diálogo de advertencia. */
    private void dialogoAdvertencia(String titulo,
                                    String mensaje,
                                    boolean limpia) {
        Alert dialogo = new Alert(AlertType.WARNING);
        dialogo.setTitle(titulo);
        dialogo.setHeaderText(null);
        dialogo.setContentText(mensaje);
        if (limpia)
            dialogo.setOnCloseRequest(e -> limpiaTabla());
        dialogo.show();
        controladorTablaVideojuegos.enfocaTabla();
    }

    /* Muestra un diálogo de error. */
    private void dialogoError(String titulo, String mensaje) {
        if (conectado)
            desconectar(null);
        Alert dialogo = new Alert(AlertType.ERROR);
        dialogo.setTitle(titulo);
        dialogo.setHeaderText(null);
        dialogo.setContentText(mensaje);
        dialogo.setOnCloseRequest(e -> limpiaTabla());
        dialogo.show();
        controladorTablaVideojuegos.enfocaTabla();
    }

    /* Agrega un videojuego a la tabla. */
    private void agregaVideojuego(Videojuego videojuego) {
        controladorTablaVideojuegos.agregaRenglon(videojuego);
    }

    /* Elimina un videojuego de la tabla. */
    private void eliminaVideojuego(Videojuego videojuego) {
        controladorTablaVideojuegos.eliminaRenglon(videojuego);
    }

    /* Reordena la tabla. */
    private void reordenaTabla() {
        controladorTablaVideojuegos.reordena();
    }

    /* Limpia la tabla. */
    private void limpiaTabla() {
        controladorTablaVideojuegos.limpiaTabla();
    }
}
