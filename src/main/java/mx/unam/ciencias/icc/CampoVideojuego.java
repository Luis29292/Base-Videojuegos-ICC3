package mx.unam.ciencias.icc;

/**
 * Enumeración para los campos de un {@link Estudiante}.
 */
public enum CampoVideojuego {

    /** El nombre del estudiante. */
    NOMBRE,
    /** El número de cuenta del estudiante. */
    YEAR,
    /** El promedio del estudiante. */
    PRECIO,
    /** La edad del estudiante. */
    MARCA,
    SAGA;
	

    /**
     * Regresa una representación en cadena del campo para ser usada en
     * interfaces gráficas.
     * @return una representación en cadena del campo.
     */
    @Override public String toString() {
        // Aquí va su código.
	if (this==NOMBRE)
	    return "Nombre";
	if (this==YEAR)
	    return "# Año";
	if (this==PRECIO)
	    return "Precio";
	if (this==MARCA)
	    return "Marca";
	if (this==SAGA)
		return "Saga";
	return null;
    }
}
