package mx.unam.ciencias.icc.fx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import mx.unam.ciencias.icc.Videojuego;

/**
 * Clase para el controlador del contenido del diálogo para editar y crear
 * videojuegos.
 */
public class ControladorFormaVideojuego extends ControladorForma {

    /* La entrada verificable para el nombre. */
    @FXML private EntradaVerificable entradaNombre;
    /* La entrada verificable para el número de year. */
    @FXML private EntradaVerificable entradaYear;
    /* La entrada verificable para el precio. */
    @FXML private EntradaVerificable entradaPrecio;
    /* La entrada verificable para la edad. */
    @FXML private EntradaVerificable entradaMarca;
    @FXML private EntradaVerificable entradaSaga;

    /* El valor del nombre. */
    private String nombre;
    /* El valor del número de year. */
    private int year;
    /* El valor del precio. */
    private double precio;
    /* El valor de la edad. */
    private String marca;
    private String saga;

    /* El videojuego creado o editado. */
    private Videojuego videojuego;

    /* Inicializa el estado de la forma. */
    @FXML private void initialize() {
        entradaNombre.setVerificador(n -> verificaNombre(n));
        entradaYear.setVerificador(c -> verificaYear(c));
        entradaPrecio.setVerificador(p -> verificaPrecio(p));
        entradaMarca.setVerificador(m -> verificaMarca(m));
        entradaSaga.setVerificador(s -> verificaSaga(s));

        entradaNombre.textProperty().addListener(
            (o, v, n) -> verificaVideojuego());
        entradaYear.textProperty().addListener(
            (o, v, n) -> verificaVideojuego());
        entradaPrecio.textProperty().addListener(
            (o, v, n) -> verificaVideojuego());
        entradaMarca.textProperty().addListener(
            (o, v, n) -> verificaVideojuego());
        entradaSaga.textProperty().addListener(
                (o, v, n) -> verificaVideojuego());
    }

    /* Manejador para cuando se activa el botón aceptar. */
    @FXML private void aceptar(ActionEvent evento) {
        actualizaVideojuego();
        aceptado = true;
        escenario.close();
    }

    /**
     * Define el videojuego del diálogo.
     * @param videojuego el nuevo videojuego del diálogo.
     */
    public void setVideojuego(Videojuego videojuego) {
        this.videojuego = videojuego;
        if (videojuego == null)
            return;
        this.videojuego = new Videojuego(null, 0, 0, null,null);
        this.videojuego.actualiza(videojuego);
        entradaNombre.setText(videojuego.getNombre());
        String c = String.format("%09d", videojuego.getYear());
        entradaYear.setText(c);
        String p = String.format("%2.2f", videojuego.getPrecio());
        entradaPrecio.setText(p);
        entradaMarca.setText(videojuego.getMarca());
        entradaSaga.setText(videojuego.getSaga());
    }

    /* Verifica que los cuatro campos sean válidos. */
    private void verificaVideojuego() {
        boolean n = entradaNombre.esValida();
        boolean c = entradaYear.esValida();
        boolean p = entradaPrecio.esValida();
        boolean e = entradaMarca.esValida();
        boolean s = entradaSaga.esValida();
        botonAceptar.setDisable(!n || !c || !p || !e);
    }

    /* Verifica que el nombre sea válido. */
    private boolean verificaNombre(String n) {
        if (n == null || n.isEmpty())
            return false;
        nombre = n;
        return true;
    }

    /* Verifica que el número de year sea válido. */
    private boolean verificaYear(String c) {
        if (c == null || c.isEmpty())
            return false;
        try {
            year = Integer.parseInt(c);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return year >= 1900 && year < 9999;
    }

    /* Verifica que el precio sea válido. */
    private boolean verificaPrecio(String p) {
        if (p == null || p.isEmpty())
            return false;
        try {
            precio = Double.parseDouble(p);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return precio >= 0.0;
    }

    /* Verifica que la edad sea válida. */
    private boolean verificaMarca(String n) {
        if (n == null || n.isEmpty())
            return false;
        marca = n;
        return true;
    }
    
    private boolean verificaSaga(String n) {
        if (n == null || n.isEmpty())
            return false;
        saga = n;
        return true;
    }

    /* Actualiza al videojuego, o lo crea si no existe. */
    private void actualizaVideojuego() {
        if (videojuego != null) {
            videojuego.setNombre(nombre);
            videojuego.setYear(year);
            videojuego.setPrecio(precio);
            videojuego.setMarca(marca);
            videojuego.setSaga(saga);
        } else {
            videojuego = new Videojuego(nombre, year, precio, marca,saga);
        }
    }

    /**
     * Regresa el videojuego del diálogo.
     * @return el videojuego del diálogo.
     */
    public Videojuego getVideojuego() {
        return videojuego;
    }

    /**
     * Define el verbo del botón de aceptar.
     * @param verbo el nuevo verbo del botón de aceptar.
     */
    public void setVerbo(String verbo) {
        botonAceptar.setText(verbo);
    }

    /**
     * Define el foco incial del diálogo.
     */
    @Override public void defineFoco() {
        entradaNombre.requestFocus();
    }
}
