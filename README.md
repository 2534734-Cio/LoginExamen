\# LoginExamen



Aplicación de inicio de sesión con roles, desarrollada en \*\*Kotlin Multiplatform (KMP)\*\* con backend en \*\*Ktor\*\* desplegado en la nube.



\## Estructura del proyecto



| Carpeta | Descripción |

|---|---|

| `Backend/` | Servidor Ktor (Kotlin) con Docker, desplegado en Render |

| `Frontend/` | App KMP con Compose Multiplatform (Android, escritorio e iOS) |



Son dos proyectos Gradle independientes dentro del mismo repositorio.



\## Backend



\- URL en la nube: https://loginexamen-backend.onrender.com

\- Rutas:

&#x20; - `GET /` : verifica que el servidor esté activo

&#x20; - `POST /login` : recibe `username` y `password`, y regresa el nombre y el rol del usuario

\- Usuarios: `Backend/src/main/resources/users.json`

\- Despliegue: cada push a `main` en la carpeta `Backend/` vuelve a publicar el servidor en Render



\## Frontend



\- Abrir la carpeta `Frontend/` en Android Studio y sincronizar Gradle

\- Ejecutar la configuración `androidApp` en un dispositivo Android

\- La URL del backend está en `shared/src/androidMain/kotlin/com/example/login/network/BaseUrl.android.kt`



\## Sensores



Se usa el patrón `expect/actual` de KMP: se declara en `commonMain` y cada plataforma lo implementa.



\### Sensor de luz

\- `shared/src/commonMain/.../sensor/SensorLuz.kt` : declaración `expect`

\- `shared/src/androidMain/.../sensor/SensorLuz.android.kt` : implementación con `SensorManager` (`TYPE\\\_LIGHT`)

\- `shared/src/commonMain/.../App.kt` : muestra los lux y activa el modo oscuro con menos de 10 lx



\### GPS

\- `shared/src/commonMain/.../sensor/SensorGps.kt` : declaración `expect`, `Ubicacion` y `EstadoGps`

\- `shared/src/androidMain/.../sensor/SensorGps.android.kt` : implementación con `LocationManager` y permisos

\- `shared/src/commonMain/.../ui/GpsScreen.kt` : pantalla con latitud, longitud y botón "Ver en el mapa"

\- `androidApp/src/main/AndroidManifest.xml` : permisos `ACCESS\\\_FINE\\\_LOCATION` y `ACCESS\\\_COARSE\\\_LOCATION`



En escritorio (`jvmMain`) e iOS (`iosMain`) los sensores regresan valores vacíos.



\## Flujo de trabajo con Git



1\. Crear una rama propia: `git checkout -b nombre-rama`

2\. Hacer commits separados para Frontend y Backend

3\. Subir la rama por SSH: `git push -u origin nombre-rama`

4\. Unir a `main` con merge y hacer push





