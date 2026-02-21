DOCUMENTACIÓN – SISTEMA DE EMERGENCIAS
Funcionalidad

Detección automática de emergencias con cuenta atrás y registro manual de incidencias.

Descripción general

El sistema de emergencias permite al usuario:

Registrar emergencias de forma manual.

Detectar situaciones de riesgo automáticamente mediante un detector de caídas (FallDetector) que se ejecuta en segundo plano.

La aplicación está desarrollada en Java y utiliza control de versiones con Git, siguiendo una metodología basada en ramas (main, developer, feature_*).

Arquitectura básica del sistema
Clases principales
Clase	Función
Main	Controla el flujo principal del programa y el menú de usuario.
EmergencyManager	Centraliza la lógica de creación y almacenamiento de emergencias.
FallDetector	Hilo independiente que simula la detección automática de caídas.
EmergencyRecord	Modelo de datos que representa una emergencia.
EmergencyHistoryManager	Gestiona la persistencia del historial en formato JSON.
UserAccount / UserData	Gestionan la información del usuario autenticado.
Location	Obtiene la ubicación de usuario mediante API y localiza centros de salud cercanos.
HealthCenterReader	Carga los centros de salud desde el archivo JSON/GeoJSON.
Persistencia de datos

El historial de emergencias se almacena en formato JSON.

Se utiliza la clase EmergencyHistoryManager para guardar y recuperar los registros del historial.

Los centros de salud se cargan desde el archivo src/resources/ca_centros_salud_20260105.geojson usando HealthCenterReader.

La ubicación del usuario se obtiene mediante la API ip-api.com y se procesa con Gson para localizar el centro de salud más cercano.

Detección automática de emergencias

La clase FallDetector se ejecuta en segundo plano como hilo independiente.

Cada cierto intervalo simula una posible caída.

Si se detecta una caída:

Se muestra un aviso en consola.

Se inicia una cuenta atrás de 10 segundos.

Si no hay intervención, la emergencia se envía automáticamente y se registra en el historial.

Cuenta atrás de confirmación

Permite al usuario reaccionar antes de que la alerta sea definitiva.

Emergencias registradas automáticamente se marcan con el tipo: "Emergencia detectada automáticamente".

Consideraciones técnicas y limitaciones

Hilos independientes y entrada por consola (Scanner) pueden provocar comportamientos no deterministas en la visualización de la cuenta atrás.

La lógica automática está separada del flujo interactivo del menú para garantizar estabilidad.

El sistema es educativo y simula concurrencia y persistencia de forma sencilla y comprensible.

Control de versiones (Git)

Se sigue una metodología basada en ramas:

Rama	Uso
main	Versión estable del proyecto.
developer	Rama de integración de nuevas funcionalidades.
feature_*	Implementaciones concretas de funcionalidades o mejoras (ej. feature_documentacion-json).

Solo se integran en developer las funcionalidades consideradas estables.

Conclusión

El proyecto demuestra:

Programación orientada a objetos.

Uso de hilos (Thread) para tareas concurrentes.

Persistencia de datos en JSON.

Control de versiones profesional con Git y desarrollo incremental mediante ramas de funcionalidades.

El sistema constituye una base sólida y funcional para una aplicación de gestión de emergencias, con margen para futuras ampliaciones o mejoras.