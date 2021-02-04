package mx.unam.ciencias.icc.fx;

import javafx.collections.ListChangeListener.Change;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import mx.unam.ciencias.icc.Videojuego;
import mx.unam.ciencias.icc.Lista;

/**
 * Clase para el controlador de la tabla para mostrar la base de datos.
 */
public class ControladorTablaVideojuegos {

    /* La tabla. */
    @FXML private TableView<Videojuego> tabla;

    /* La columna del nombre. */
    @FXML private TableColumn<Videojuego, String> columnaNombre;
    /* La columna del número de year. */
    @FXML private TableColumn<Videojuego, Number> columnaYear;
    /* La columna del precio. */
    @FXML private TableColumn<Videojuego, Number> columnaPrecio;
    /* La columna de la edad. */
    @FXML private TableColumn<Videojuego, String> columnaMarca;
    @FXML private TableColumn<Videojuego, String> columnaSaga;

    /* El modelo de la selección. */
    TableView.TableViewSelectionModel<Videojuego> modeloSeleccion;
    /* La selección. */
    private ObservableList<TablePosition> seleccion;
    /* Lista de escuchas de selección. */
    private Lista<EscuchaSeleccion> escuchas;
    /* Los renglones en la tabla. */
    private ObservableList<Videojuego> renglones;

    /* Inicializa el controlador. */
    @FXML private void initialize() {
        renglones = tabla.getItems();
        modeloSeleccion = tabla.getSelectionModel();
        modeloSeleccion.setSelectionMode(SelectionMode.MULTIPLE);
        seleccion = modeloSeleccion.getSelectedCells();
        ListChangeListener<TablePosition> lcl = c -> cambioEnSeleccion();
        seleccion.addListener(lcl);
        columnaNombre.setCellValueFactory(c -> c.getValue().nombreProperty());
        columnaYear.setCellValueFactory(c -> c.getValue().yearProperty());
        columnaPrecio.setCellValueFactory(
            c -> c.getValue().precioProperty());
        columnaMarca.setCellValueFactory(c -> c.getValue().marcaProperty());
        columnaSaga.setCellValueFactory(c -> c.getValue().sagaProperty());
        escuchas = new Lista<EscuchaSeleccion>();
    }

    /* Notifica a los escuchas que hubo un cambio en la selección. */
    private void cambioEnSeleccion() {
        for (EscuchaSeleccion escucha : escuchas)
            escucha.renglonesSeleccionados(seleccion.size());
    }

    /**
     * Limpia la tabla.
     */
    public void limpiaTabla() {
        renglones.clear();
    }

    /**
     * Agrega un renglón a la tabla.
     * @param videojuego el renglón a agregar.
     */
    public void agregaRenglon(Videojuego videojuego) {
        renglones.add(videojuego);
        tabla.sort();
    }

    /**
     * Elimina un renglón de la tabla.
     * @param videojuego el renglón a eliminar.
     */
    public void eliminaRenglon(Videojuego videojuego) {
        renglones.remove(videojuego);
        tabla.sort();
    }

    /**
     * Selecciona renglones de la tabla.
     * @param videojuegos los renglones a seleccionar.
     */
    public void seleccionaRenglones(Lista<Videojuego> videojuegos) {
        modeloSeleccion.clearSelection();
        for (Videojuego videojuego : videojuegos)
            modeloSeleccion.select(videojuego);
    }

    /**
     * Regresa una lista con los registros seleccionados en la tabla.
     * @return una lista con los registros seleccionados en la tabla.
     */
    public Lista<Videojuego> getSeleccion() {
        Lista<Videojuego> seleccionados = new Lista<Videojuego>();
        for (TablePosition tp : seleccion) {
            int r = tp.getRow();
            seleccionados.agregaFinal(renglones.get(r));
        }
        return seleccionados;
    }

    /**
     * Regresa el videojuego seleccionado en la tabla.
     * @return el videojuego seleccionado en la tabla.
     */
    public Videojuego getRenglonSeleccionado() {
        int r = seleccion.get(0).getRow();
        return renglones.get(r);
    }

    /**
     * Agrega un escucha de selección.
     * @param escucha el escucha a agregar.
     */
    public void agregaEscuchaSeleccion(EscuchaSeleccion escucha) {
        escuchas.agregaFinal(escucha);
    }

    /**
     * Fuerza un reordenamiento de la tabla.
     */
    public void reordena() {
        tabla.sort();
    }

    /**
     * Enfoca la tabla.
     */
    public void enfocaTabla() {
        tabla.requestFocus();
    }
}
