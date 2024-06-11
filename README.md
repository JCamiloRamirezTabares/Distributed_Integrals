# Sistema de Integración de Funciones

## Descripción
Este sistema calcula la integral definida de funciones matemáticas ingresadas por el usuario. La computación se realiza de manera distribuida a través de múltiples servidores para optimizar el rendimiento.

## Integrantes
- Juan Camilo Ramírez
- Juan Felipe Castillo
- Santiago Prado
- Juan Diego Lora

## Requisitos
- **Java**: Debes tener Java instalado en tu máquina para ejecutar los archivos `.jar`.
- **Gradle**: Necesario para compilar el proyecto.

## Compilación
Para compilar el proyecto y crear los archivos `.jar`, ejecuta el siguiente comando en la terminal:

gradle build

Este comando generará tres archivos JAR: `broker.jar`, `server.jar`, `client.jar`.

## Ejecución
Sigue estos pasos para ejecutar el sistema correctamente:

1. **Broker**:

java -jar broker.jar

Ejecuta el broker primero para que esté activo cuando los otros componentes intenten conectarse.

2. **Servidores**:
java -jar server.jar
Puedes iniciar múltiples instancias del servidor en diferentes terminales si deseas mejorar el rendimiento.

3. **Cliente**:
java -jar client.jar
Inicia el cliente para interactuar con el sistema y calcular integrales.

## Uso
Una vez que el cliente está en ejecución, sigue las instrucciones en la consola para ingresar la función matemática y los rangos de integración. El sistema calculará y devolverá el resultado de la integral definida.
