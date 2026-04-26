# Proyecto Gestión de Emergencias

## Resumen Ejecutivo para Presentación (Formato NotebookLM)

Este documento detalla la arquitectura y el flujo de ejecución de la aplicación "Gestión de Emergencias". Está optimizado para ser procesado por herramientas de IA como NotebookLM para generar presentaciones y resúmenes.

La aplicación tiene como objetivo principal gestionar la detección, recopilación de datos y envío de alertas ante emergencias médicas. Está desarrollada en Java y utiliza JavaFX para su interfaz gráfica, además de integrarse con sistemas de mapeo (Google Maps) y procesamiento de datos geolocalizados (GeoJSON).

---

## Arquitectura de la Aplicación: Patrón MVC

La aplicación sigue el patrón de diseño Modelo-Vista-Controlador (MVC), lo que permite una clara separación de responsabilidades:

1.  **Modelo (Model):** Representa los datos (Usuarios, Ubicaciones, Eventos).
2.  **Vista (View):** La interfaz de usuario (JavaFX / FXML).
3.  **Controlador (Controller):** La lógica de negocio que conecta la Vista con el Modelo.

---

## 1. El Paquete `com.emergencias.model` (El Modelo)

Este paquete contiene las clases que definen las estructuras de datos (POJOs - Plain Old Java Objects). No contienen lógica compleja, solo guardan información.

*   **`UserData`**: Almacena todos los datos personales y médicos de un usuario (paciente o persona que alerta). Contiene variables como `nombre`, `apellidos`, `dni`, `datosMedicos`, etc.
*   **`EmergencyEvent`**: Representa el suceso en sí. Guarda el nivel de gravedad, los datos del usuario que llama (`callerData`), los datos del herido (`injuredData`), la ubicación y la marca de tiempo (timestamp). Es el paquete de información completo que se envía a emergencias.
*   **`LocationData`**: Guarda la ubicación específica (latitud, longitud, ciudad, país) obtenida en el momento de la alerta.
*   **Subpaquete `centros` (`Feature`, `Geometry`, `Properties`, `GeoJsonData`)**: Estas clases actúan como "moldes" estrictos para que la librería Gson pueda convertir (parsear) el archivo `ca_centros_salud_20260105.geojson` en objetos Java manipulables.

---

## 2. El Paquete `com.emergencias.controller` (El Controlador)

Aquí reside el "cerebro" de la aplicación. Estas clases ejecutan las operaciones, toman decisiones y coordinan el flujo de datos.

*   **`EmergencyManager`**: Es el director de orquesta. Es la clase principal que es llamada desde la interfaz gráfica. Su método principal `procesarEmergencia()` coordina todo el flujo:
    1.  Recibe la gravedad y los datos básicos de la UI.
    2.  Valida y busca el DNI en la base de datos (usando `RetrieveData`).
    3.  Obtiene la ubicación actual (usando `Location`).
    4.  Crea el objeto `EmergencyEvent`.
    5.  Envía la alerta (usando `AlertSender`).
*   **`Location`**: Su función es simular (o realizar) la obtención de las coordenadas actuales del usuario, devolviéndolas en formato JSON para que sean procesadas y convertidas en un objeto `LocationData`.
*   **`RetrieveData`**: Contiene la lógica para conectarse a la base de datos simulada (el archivo `pacientes.json`). Su método `retrieveInjuredData(dni)` lee el archivo JSON, lo convierte en una lista de objetos y busca al paciente específico para devolver todos sus datos médicos y de contacto.
*   **`HealthCenterReader`**: Se encarga específicamente de leer el archivo `ca_centros_salud_20260105.geojson` y extraer la lista completa de centros médicos disponibles.
*   **`HealthCenterLocator`**: Realiza el cálculo matemático (Fórmula de Haversine) para comparar la ubicación actual del usuario con la lista de centros de salud y devolver el centro más cercano.
*   **`ValidaEntrada`**: Contiene métodos estáticos con expresiones regulares (Regex) para asegurar que los datos introducidos por el usuario (como el DNI o el teléfono) tienen el formato correcto antes de procesarlos.
*   **Clases de Heridas (`Herida`, `HeridaGrave`, `Indicaciones`)**: Estas clases gestionan la categorización médica de la emergencia y proporcionan instrucciones de primeros auxilios basadas en la gravedad.

---

## 3. El Paquete `com.emergencias.gui` (La Vista)

Gestiona la interacción directa con el usuario a través de la interfaz gráfica.

*   **`MainGui`**: Es la clase lanzadora de JavaFX. Su única función es cargar el archivo de diseño `.fxml` e iniciar la ventana principal de la aplicación.
*   **`UIController`**: Actúa como puente entre la interfaz gráfica (los botones, textos, casillas) y la lógica (`EmergencyManager`).
    *   Escucha los eventos (cuando el usuario pulsa "Generar Alerta").
    *   Recopila los datos que el usuario ha escrito en pantalla.
    *   Llama a `EmergencyManager.procesarEmergencia()`.
    *   **Uso de `Task` e Hilos (Threads):** Utiliza programación concurrente para ejecutar la lógica de la emergencia en un "hilo" separado. Esto es vital en JavaFX para evitar que la interfaz se quede "congelada" mientras el sistema busca en archivos o carga el mapa.
    *   Utiliza `WebView` y `WebEngine` para renderizar Google Maps directamente dentro de la aplicación, mostrando la ruta hacia el centro médico más cercano.

---

## 4. Otros Componentes Clave

*   **`com.emergencias.alert.AlertSender`**: Su responsabilidad final es tomar el `EmergencyEvent` completamente construido, serializarlo (convertirlo a JSON usando Gson) y escribirlo/guardarlo en un registro histórico (archivo `alertas.json`).
*   **`com.emergencias.util.CoordinateConverter`**: Es una clase matemática de utilidad. Como la base de datos gubernamental de centros de salud (el GeoJSON) utiliza el sistema de coordenadas cartesianas UTM (EPSG:25830), y Google Maps/GPS usan coordenadas geográficas (Latitud/Longitud), esta clase contiene el complejo algoritmo matemático para traducir de un sistema a otro, permitiendo calcular la distancia real.

---

## Flujo Principal de Ejecución (Paso a Paso)

Si tienes que explicar cómo funciona la aplicación cuando se pulsa el botón, este es el resumen perfecto:

1.  **Entrada de Datos (UIController):** El usuario rellena los datos y pulsa "Generar Alerta" en JavaFX. `UIController` recoge esa información.
2.  **Delegación (EmergencyManager):** `UIController` llama al `EmergencyManager` y le pasa los datos, creando un hilo asíncrono para no bloquear la pantalla.
3.  **Recuperación de Historial (RetrieveData):** El Manager pide a `RetrieveData` que busque el DNI en `pacientes.json`. Si lo encuentra, obtiene alergias, contactos, etc.
4.  **Localización (Location):** El Manager obtiene las coordenadas actuales del usuario.
5.  **Búsqueda de Centro Médico (HealthCenterLocator):** El Manager le pasa la ubicación actual y el mapa de centros a `HealthCenterLocator`. Este convierte las coordenadas UTM de los centros a Lat/Lon (vía `CoordinateConverter`) y usa la fórmula de Haversine para encontrar el más cercano.
6.  **Creación del Evento (EmergencyEvent):** Con toda la información recopilada (datos del llamante, datos del herido, gravedad, ubicación y centro asignado), se instancia un objeto `EmergencyEvent`.
7.  **Envío de Alerta (AlertSender):** El evento se pasa al `AlertSender`, que lo guarda físicamente en `alertas.json`.
8.  **Actualización de UI (UIController):** El Manager devuelve el control a `UIController` con los resultados, y este actualiza los textos en pantalla y dibuja la ruta en el mapa de Google Maps.
