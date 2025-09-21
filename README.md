##Prueba Técnica

1. Descripción de la Solución
La aplicación permite al usuario capturar dos imágenes de rostros utilizando una interfaz de usuario integrada del SDK, y luego las compara para determinar su similitud. El resultado de la comparación se muestra en pantalla.

2. Patrones y Tecnologías Utilizadas
Arquitectura MVVM (Model-View-ViewModel): Se implementó este patrón para separar la lógica de negocio de la interfaz de usuario, garantizando un código más mantenible, escalable y fácil de probar.

Jetpack Compose: La interfaz de usuario fue construida completamente con este toolkit moderno, lo que permitió un desarrollo declarativo, más rápido e intuitivo.

Corrutinas de Kotlin: Se utilizaron para gestionar operaciones asíncronas, como la comparación de rostros con el SDK, evitando bloquear el hilo principal de la UI y asegurando una experiencia de usuario fluida.

Inyección de Dependencias con Hilt: Se integró Hilt para gestionar las dependencias del proyecto. Esto mejora la legibilidad del código y facilita el manejo de la lógica de negocio y las pruebas.

Face SDK de Regula: El SDK de terceros fue integrado en la capa de datos para la funcionalidad central de captura y comparación de rostros, manteniendo la lógica del negocio desacoplada de la implementación del proveedor.

3. Decisiones Técnicas Destacadas
Abstracción de la Capa de Datos: Se decidió abstraer el uso del SDK en la capa de datos. Esto significa que si en el futuro se necesitara cambiar a otro SDK o a una API de comparación diferente, el resto de la aplicación (la lógica de negocio y la UI) no se verían afectadas.

Manejo Asíncrono: Se optó por un enfoque completamente asíncrono utilizando corrutinas, lo que garantiza que la aplicación no se congele durante las operaciones que consumen tiempo, como la comunicación con el SDK.

UI Declarativa con Compose: El uso de Compose permitió crear una UI flexible y moderna, que responde de manera eficiente a los cambios de estado.

4. Oportunidades de Mejora y Extensión
A pesar de cumplir con los requisitos funcionales, hay varios aspectos que se podrían mejorar para llevar la aplicación a un nivel superior:

Internacionalización (i18n): Los strings de la aplicación están codificados y podrían ser extraídos a archivos de recursos para soportar múltiples idiomas.

Mejoras en la Interfaz de Usuario: El diseño es funcional, pero se podría mejorar con un enfoque más pulido y detallado para una experiencia de usuario más atractiva.

Gestión de Errores: Se podrían implementar mecanismos de gestión de errores más robustos para situaciones como la falta de una foto o un fallo en la comparación.

Mejora del Código: Se podría añadir más documentación en línea y comentarios para explicar decisiones de diseño específicas, lo que facilitaría la colaboración y el mantenimiento a largo plazo.

Manejo de Permisos: La solicitud de permisos de la cámara podría gestionarse de forma más explícita y elegante.

Testing: La cobertura de pruebas podría ser ampliada para incluir tests de UI y de integración, garantizando la estabilidad de la aplicación.
