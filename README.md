# utez-2e-biblioteca-javafx-equipo10

# Integradora Programación Estructurada U4
Descripción : Esta es una aplicación que consta de dos ventanas fxml,
las cuales trabajan en conjunto para poder almacenar libros y ejecutar funciones sobre estos,
usando un sistema de CRUD basico. 

Ejecución y funcionamiento : Por medio de este sistema podemos añadir un libro, validar que 
todos sus campos sean correctos, mostrar todos los libros en un table view, poder seleccionarlos
desde la tabla para poder ver su información o poder editarlos, e incluso realizar una exportación en 
base al documento books.csv para su facil almacenaje fuera de la aplicación.

Persistencia : Los libros que el usuario ingrese se guardaran en books.csv, que por medio del uso de
observableLists, arreglos basicos, listas sin un numero definido de slots y un complejo sistema de
carga de datos podran ser procesados para su modificación y actualización, asi como su muestreo en la 
table view.

Reporte : Para exportar el archivo books.csv, se ejecuta un complejo sistema que realiza un
analisis, crea si es necesario y clona el archivo con su correspondiente nombre. Para comenzar, se presiona el boton Exportar en el main view, 
el cual ejecutara la funcion exportar(), esta comprobara que la carpeta se encuentre donde debe estar, y si no la encuentra, la crea. Mas adelante
y una vez pasado este filtro, procede a clone con .copy el archivo books.csv desde la carpeta data, y finalmente muestra si ocurrio un error de ejecucion
por si algosucedio en el proceso.

Pasos para la ejecución : Para ejecutar este codigo se dbee acceder a la carpeta integradora, en donde se encontraran
dos clases .java (HelloAplication y Launcher) que podran ejecutar la pantalla principal (Main-view con su MainController).
