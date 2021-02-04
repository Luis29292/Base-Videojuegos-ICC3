Introducción a Ciencias de la Computación
=========================================

Práctica 10: Hilos de ejecución y enchufes
------------------------------------------

### Fecha de entrega: martes 26 de enero, 2021

Deben completar las clases del paquete
[red](https://aztlan.fciencias.unam.mx/gitlab/2021-1-icc/practica10/blob/master/src/main/java/mx/unam/ciencias/icc/red/).

Una vez que hayan terminado sus clases, deben compilar al hacer:

```
$ mvn compile
```

También deben pasar todas sus pruebas unitarias al hacer:

```
$ mvn test
```

También se debe ejecutar correctamente el programa escrito en la clase
[ServidorPractica10](https://aztlan.fciencias.unam.mx/gitlab/2021-1-icc/practica10/blob/master/src/main/java/mx/unam/ciencias/icc/ServidorPractica10.java)
al hacer:

```
$ mvn install
...
$ mvn -q exec:java \
      -f pom.xml   \
      -Dexec.mainClass=mx.unam.ciencias.icc.ServidorPractica10 -Dexec.args="1234 estudiantes.bd"
```

Los parámetros `1234` y `estudiantes.bd` son el puerto y el archivo de entrada
(opcional) para el servidor; se incluye también un *script* para hacerlo más
sencillo:

```
$ ./bin/servidor-practica10 1234 estudiantes.bd
```

Por último, se debe ejecutar correctamente el programa escrito en la clase
[ClientePractica10](https://aztlan.fciencias.unam.mx/gitlab/2021-1-icc/practica10/blob/master/src/main/java/mx/unam/ciencias/icc/ClientePractica10.java)
al hacer:

```
$ mvn -q exec:java \
      -f pom.xml   \
      -Dexec.mainClass=mx.unam.ciencias.icc.ClientePractica10
```

Y también se incluye un *script* para hacerlo más sencillo:

```
$ ./bin/cliente-practica10
```

Los únicos archivos que deben modificar son:

* `Arreglos.java`,
* `BaseDeDatos.java`,
* `BaseDeDatosEstudiantes.java`,
* `CampoEstudiante.java`,
* `Estudiante.java`,
* `Lista.java`,
* `red/Conexion.java`,
* `red/EscuchaConexion.java`,
* `red/EscuchaServidor.java`,
* `red/Mensaje.java`,
* `red/ServidorBaseDeDatosEstudiantes.java` y
* `red/ServidorBaseDeDatos.java`.

*No deben modificar de ninguna manera ninguno de los otros archivos de la práctica*.

### Repositorio

Pueden clonar la práctica con el siguiente comando:

```
$ git clone https://aztlan.fciencias.unam.mx/gitlab/2021-1-icc/practica10.git
```

### Documentación

La documentación generada por JavaDoc la pueden consultar aquí:

[Documentación generada por JavaDoc para la práctica
10](https://aztlan.fciencias.unam.mx/~canek/2021-1-icc/practica10/apidocs/index.html)
