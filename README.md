# 🚑 Sistema de Emergencias

## 📌 Descripción general

Aplicación desarrollada en **Java** que permite la gestión y registro de emergencias médicas.

El sistema permite:

- Registrar emergencias manualmente.
- Detectar situaciones de riesgo automáticamente mediante un detector de caídas.
- Localizar centros de salud cercanos a partir de la ubicación obtenida por IP.
- Guardar el historial de emergencias en formato JSON.

El proyecto utiliza control de versiones con Git siguiendo una estrategia basada en ramas (`main`, `developer`, `feature_*`).

---

## 🏗 Arquitectura del sistema

### Clases principales

| Clase | Función |
|-------|----------|
| `Main` | Controla el flujo principal y menú de usuario |
| `EmergencyManager` | Gestiona creación y almacenamiento de emergencias |
| `FallDetector` | Hilo independiente que simula detección automática |
| `EmergencyRecord` | Modelo de datos de emergencia |
| `EmergencyHistoryManager` | Persistencia del historial en JSON |
| `UserAccount` / `UserData` | Gestión de datos del usuario |
| `Location` | Obtiene ubicación por IP y localiza centro cercano |
| `HealthCenterReader` | Carga centros de salud desde GeoJSON |

---

## 💾 Persistencia de datos

- El historial se almacena en `alertas.json`.
- Los centros de salud se cargan desde:


src/resources/ca_centros_salud_20260105.geojson


- Se utiliza la librería **Gson** para procesar JSON.
- La ubicación se obtiene mediante la API pública `http://ip-api.com`.

---

## ⚙️ Detección automática de emergencias

La clase `FallDetector` se ejecuta como hilo independiente:

1. Simula detección de caída.
2. Inicia cuenta atrás de 10 segundos.
3. Si no hay cancelación, registra la emergencia automáticamente.

Las emergencias automáticas se etiquetan como:


Emergencia detectada automáticamente


---

## 🔎 Cuenta atrás de confirmación

Permite al usuario cancelar el envío antes de que la alerta sea definitiva.

---

## ⚠️ Consideraciones técnicas

- Uso de `Thread` para concurrencia básica.
- Posible comportamiento no determinista en consola debido a uso simultáneo de `Scanner` y hilos.
- Proyecto con finalidad educativa.
- Uso de herencia y polimorfismo en la jerarquía `Herida`, `HeridaLeve`, `HeridaGrave`.

---

## 🧪 Compilación y ejecución manual

### Requisitos

- JDK 17 o superior
- Librería `gson-2.10.1.jar` dentro de la carpeta `lib`

### Compilar

En PowerShell:

```powershell
$sources = Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac -cp "lib\gson-2.10.1.jar;bin" -d bin $sources
Ejecutar
java -cp "bin;lib\gson-2.10.1.jar" com.emergencias.main.Main
🌿 Control de versiones
Rama	Uso
main	Versión estable
developer	Integración de nuevas funcionalidades
feature_*	Desarrollo aislado de mejoras

Solo se integran en developer funcionalidades estables mediante Pull Request.