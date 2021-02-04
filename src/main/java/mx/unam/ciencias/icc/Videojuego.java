package mx.unam.ciencias.icc;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Clase para representar videojuegos. Un videojuego tiene nombre, número de
 * year, precio y edad. La clase implementa {@link Registro}, por lo que
 * puede representarse con una línea de texto y definir sus propiedades con una
 * línea de texto; además de determinar si sus campos cazan valores arbitrarios.
 */
public class Videojuego implements Registro<Videojuego, CampoVideojuego> {

    /* Nombre del videojuego. */
    private StringProperty nombre;
    /* Número de year. */
    private IntegerProperty year;
    /* Pormedio del videojuego. */
    private DoubleProperty precio;
    /* Edad del videojuego.*/
    private StringProperty marca;
    private StringProperty saga;

    /**
     * Define el estado inicial de un videojuego.
     * @param nombre el nombre del videojuego.
     * @param year el número de year del videojuego.
     * @param precio el precio del videojuego.
     * @param edad la edad del videojuego.
     */
    public Videojuego(String nombre,
                      int    year,
                      double precio,
                      String marca,
                      String saga) {
        // Aquí va su código.
	this.nombre = new SimpleStringProperty(nombre);
	this.year= new SimpleIntegerProperty(year);
	this.precio=new SimpleDoubleProperty(precio);
	this.marca = new SimpleStringProperty(marca);
	this.saga = new SimpleStringProperty(saga);
    }

    /**
     * Regresa el nombre del videojuego.
     * @return el nombre del videojuego.
     */
    public String getNombre() {
        // Aquí va su código.
	return nombre.get();
    }

    /**
     * Define el nombre del videojuego.
     * @param nombre el nuevo nombre del videojuego.
     */
    public void setNombre(String nombre) {
        // Aquí va su código.
	this.nombre.set(nombre);
    }

    /**
     * Regresa la propiedad del nombre.
     * @return la propiedad del nombre.
     */
    public StringProperty nombreProperty() {
        // Aquí va su código.
	 return this.nombre;
    }

    /**
     * Regresa el número de year del videojuego.
     * @return el número de year del videojuego.
     */
    public int getYear() {
        // Aquí va su código.
	return year.get();
    }

    /**
     * Define el número year del videojuego.
     * @param year el nuevo número de year del videojuego.
     */
    public void setYear(int year) {
        // Aquí va su código.
	this.year.set(year);
    }

    /**
     * Regresa la propiedad del número de year.
     * @return la propiedad del número de year.
     */
    public IntegerProperty yearProperty() {
        // Aquí va su código.
	return this.year;
    }

    /**
     * Regresa el precio del videojuego.
     * @return el precio del videojuego.
     */
    public double getPrecio() {
        // Aquí va su código.
	return precio.get();
    }

    /**
     * Define el precio del videojuego.
     * @param precio el nuevo precio del videojuego.
     */
    public void setPrecio(double precio) {
        // Aquí va su código.
	this.precio.set(precio);
    }

    /**
     * Regresa la propiedad del precio.
     * @return la propiedad del precio.
     */
    public DoubleProperty precioProperty() {
        // Aquí va su código.
	return this.precio;
    }

    /**
     * Regresa la edad del videojuego.
     * @return la edad del videojuego.
     */
    public String getMarca() {
        // Aquí va su código.
	return marca.get();
    }

    /**
     * Define el nombre del videojuego.
     * @param nombre el nuevo nombre del videojuego.
     */
    public void setMarca(String marca) {
        // Aquí va su código.
	this.marca.set(marca);
    }

    /**
     * Regresa la propiedad del nombre.
     * @return la propiedad del nombre.
     */
    public StringProperty marcaProperty() {
        // Aquí va su código.
	 return this.marca;
    }
    
    public String getSaga() {
        // Aquí va su código.
	return saga.get();
    }

    /**
     * Define el nombre del videojuego.
     * @param nombre el nuevo nombre del videojuego.
     */
    public void setSaga(String saga) {
        // Aquí va su código.
	this.saga.set(saga);
    }

    /**
     * Regresa la propiedad del nombre.
     * @return la propiedad del nombre.
     */
    public StringProperty sagaProperty() {
        // Aquí va su código.
	 return this.saga;
    }

    /**
     * Regresa una representación en cadena del videojuego.
     * @return una representación en cadena del videojuego.
     */
    @Override public String toString() {
        // Aquí va su código.
	String cadena = String.format("Nombre   : %s\n" +
                                      "Año  : %04d\n" +
                                      "Precio : %2.2f\n" +
                                      "Marca     : %s\n"+
                                      "Saga       : %s\n",
                                      getNombre(), getYear(), getPrecio(), getMarca(),getSaga());
	return cadena;
    }

    /**
     * Nos dice si el objeto recibido es un videojuego igual al que manda llamar
     * el método.
     * @param objeto el objeto con el que el videojuego se comparará.
     * @return <code>true</code> si el objeto o es un videojuego con las mismas
     *         propiedades que el objeto que manda llamar al método,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (!(objeto instanceof Videojuego))
            return false;
        Videojuego videojuego = (Videojuego)objeto;
        // Aquí va su código.
	return ((this.getNombre().equals(videojuego.getNombre()))&&(this.getYear()==videojuego.getYear())&&(this.getPrecio()==videojuego.getPrecio())
			&&(this.getMarca().equals(videojuego.getMarca()))&&(this.getSaga().equals(videojuego.getSaga())));
    }

    /**
     * Regresa el videojuego serializado en una línea de texto. La línea de
     * texto que este método regresa debe ser aceptada por el método {@link
     * Videojuego#deserializa}.
     * @return la serialización del videojuego en una línea de texto.
     */
    @Override public String serializa() {
        // Aquí va su código.
	String linea = String.format("%s\t%d\t%2.2f\t%s\t%s\n",
                                     getNombre(), getYear(), getPrecio(), getMarca(),getSaga());
	return linea;
    }

    /**
     * Deserializa una línea de texto en las propiedades del videojuego. La
     * serialización producida por el método {@link Videojuego#serializa} debe
     * ser aceptada por este método.
     * @param linea la línea a deserializar.
     * @throws ExcepcionLineaInvalida si la línea recibida es nula, vacía o no
     *         es una serialización válida de un videojuego.
     */
    @Override public void deserializa(String linea) {
        // Aquí va su código.
	ExcepcionLineaInvalida e=new ExcepcionLineaInvalida();
	if (linea==null)
	    throw e;
	linea=linea.trim();
	String[] split=linea.split("\t");
	if (split.length!=5)
	    throw e;
	nombre=new SimpleStringProperty(split[0]);
	try{
	    
	    year=new SimpleIntegerProperty(Integer.parseInt(split[1]));
	    precio=new SimpleDoubleProperty(Double.parseDouble(split[2]));
	    marca=new SimpleStringProperty(split[3]);
	    saga=new SimpleStringProperty(split[4]);
	}
	catch(NumberFormatException nfe){
	    throw e;
	}
    }

    /**
     * Actualiza los valores del videojuego con los del videojuego recibido.
     * @param videojuego el videojuego con el cual actualizar los valores.
     * @throws IllegalArgumentException si el videojuego es <code>null</code>.
     */
    public void actualiza(Videojuego videojuego) {
        // Aquí va su código.
	if (videojuego==null)
	    throw new IllegalArgumentException();
	setNombre(videojuego.getNombre());
        setYear(videojuego.getYear());
        setPrecio(videojuego.getPrecio());
        setMarca(videojuego.getMarca());
        setSaga(videojuego.getSaga());
    }

    /**
     * Nos dice si el videojuego caza el valor dado en el campo especificado.
     * @param campo el campo que hay que cazar.
     * @param valor el valor con el que debe cazar el campo del registro.
     * @return <code>true</code> si:
     *         <ul>
     *           <li><code>campo</code> es {@link CampoVideojuego#NOMBRE} y
     *              <code>valor</code> es instancia de {@link String} y es una
     *              subcadena del nombre del videojuego.</li>
     *           <li><code>campo</code> es {@link CampoVideojuego#YEAR} y
     *              <code>valor</code> es instancia de {@link Integer} y su
     *              valor entero es menor o igual a la year del
     *              videojuego.</li>
     *           <li><code>campo</code> es {@link CampoVideojuego#PRECIO} y
     *              <code>valor</code> es instancia de {@link Double} y su
     *              valor doble es menor o igual al precio del
     *              videojuego.</li>
     *           <li><code>campo</code> es {@link CampoVideojuego#EDAD} y
     *              <code>valor</code> es instancia de {@link Integer} y su
     *              valor entero es menor o igual a la edad del
     *              videojuego.</li>
     *         </ul>
     *         <code>false</code> en otro caso.
     * @throws IllegalArgumentException si el campo es <code>null</code>.
     */
    @Override public boolean caza(CampoVideojuego campo, Object valor) {
        // Aquí va su código.
		if (campo==null)
	    throw new IllegalArgumentException();
	if (campo.equals(CampoVideojuego.NOMBRE) && valor instanceof String && getNombre().contains((String)valor) && (String)valor!=""){
	    return true;
	}
	if (campo.equals(CampoVideojuego.YEAR) && valor instanceof Integer && (int)valor<=getYear())
	    return true;
	if (campo.equals(CampoVideojuego.PRECIO) && valor instanceof Double && (double)valor<=getPrecio())
	    return true;
	if (campo.equals(CampoVideojuego.MARCA) && valor instanceof String && getMarca().contains((String)valor) && (String)valor!="")
	    return true;
	if (campo.equals(CampoVideojuego.SAGA) && valor instanceof String && getSaga().contains((String)valor) && (String)valor!="")
		return true;
	return false;
    }
}
