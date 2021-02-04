package mx.unam.ciencias.icc.fx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import mx.unam.ciencias.icc.CampoVideojuego;

/**
 * Clase para el controlador del contenido del diálogo para buscar videojuegos.
 */
public class ControladorFormaBusquedaVideojuegos extends ControladorForma {

    /* El combo del campo. */
    @FXML private ComboBox<CampoVideojuego> opcionesCampo;
    /* El campo de texto para el valor. */
    @FXML private EntradaVerificable entradaValor;

    /* Inicializa el estado de la forma. */
    @FXML private void initialize() {
        entradaValor.setVerificador(s -> verificaValor(s));
        entradaValor.textProperty().addListener(
            (o, v, n) -> revisaValor(null));
    }

    /* Revisa el valor después de un cambio. */
    @FXML private void revisaValor(ActionEvent evento) {
        Tooltip.install(entradaValor, getTooltip());
        String s = entradaValor.getText();
        botonAceptar.setDisable(!entradaValor.esValida());
    }

    /* Manejador para cuando se activa el botón aceptar. */
    @FXML private void aceptar(ActionEvent evento) {
        aceptado = true;
        escenario.close();
    }

    /* Obtiene la pista. */
    private Tooltip getTooltip() {
        String m = "";
        switch (opcionesCampo.getValue()) {
        case NOMBRE:
            m = "Buscar por nombre necesita al menos un carácter";
            break;
        case YEAR:
            m = "Buscar por año necesita un número entre " +
                "1900 y 9999";
            break;
        case PRECIO:
            m = "Buscar por precio necesita un número mayor a 0.0";
            break;
        case MARCA:
            m = "Buscar por marca necesita al menos un caracter";
            break;
        case SAGA:
            m = "Buscar por saga necesita al menos un carácter";
            break;
        }
        return new Tooltip(m);
    }

    /* Verifica el valor. */
    private boolean verificaValor(String s) {
        switch (opcionesCampo.getValue()) {
        case NOMBRE:   return verificaNombre(s);
        case YEAR:   return verificaYear(s);
        case PRECIO: return verificaPrecio(s);
        case MARCA:     return verificaMarca(s);
        case SAGA:		return verificaSaga(s);
        default:       return false;
        }
    }

    /* Verifica que el nombre a buscar sea válido. */
    private boolean verificaNombre(String n) {
        return n != null && !n.isEmpty();
    }

    /* Verifica que el número de year a buscar sea válido. */
    private boolean verificaYear(String c) {
        if (c == null || c.isEmpty())
            return false;
        int year = -1;
        try {
            year = Integer.parseInt(c);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return year >= 1900 && year < 9999;
    }

    /* Verifica que el precio a buscar sea válido. */
    private boolean verificaPrecio(String p) {
        if (p == null || p.isEmpty())
            return false;
        double precio = -1.0;
        try {
            precio = Double.parseDouble(p);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return precio >= 0.0;
    }

    /* Verifica que la edad a buscar sea válida. */
    private boolean verificaMarca(String n) {
        return n != null && !n.isEmpty();
    }
    
    private boolean verificaSaga(String n) {
        return n != null && !n.isEmpty();
    }

    /**
     * Regresa el campo seleccionado.
     * @return el campo seleccionado.
     */
    public CampoVideojuego getCampo() {
        return opcionesCampo.getValue();
    }

    /**
     * Regresa el valor ingresado.
     * @return el valor ingresado.
     */
    public Object getValor() {
        switch (opcionesCampo.getValue()) {
        case NOMBRE:   return entradaValor.getText();
        case YEAR:   return Integer.parseInt(entradaValor.getText());
        case PRECIO: return Double.parseDouble(entradaValor.getText());
        case MARCA:   return entradaValor.getText();
        case SAGA:   return entradaValor.getText();
        default:       return entradaValor.getText(); // No debería ocurrir.
        }
    }

    /**
     * Define el foco incial del diálogo.
     */
    @Override public void defineFoco() {
        entradaValor.requestFocus();
    }
}
