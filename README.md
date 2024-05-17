# Integrales Distrubuidas

En grupos de 4, diseñar e implementar un sistema de software que permita a los usuarios aproximar integrales definidas utilizando diferentes métodos numéricos. Los estudiantes serán responsables de elegir la estrategia de aproximación que consideren más adecuada, basándose en resultados de pruebas.

 Como mínimo, debe tener:

- Un  "cliente" que pida la función que se desea integrar junto con los rangos de integración. Puede implementar un interprete para leer la función o usar una libreria externa (investigar).

- El dist_integ debe implementar una estrategia de aproximación de integrales que combine varios patrones de diseño, con estructuras de almacenamiento y procesamiento distribuidos, cuidadosamente escogidos para que la estrategia logre un balance entre los principales drivers arquitectónicos de performance y precisión. 

- Ejecución y análisis de experimentos para determinar a partir de qué cantidad de datos vale la pena distribuir, variables y valores que afectan los resultados, y la capacidad de aproximación del  sistema.

Para medir la aceleración de la distribución, deben hacer varios experimentos:

- La ejecución, además de hacerla en 1 máquina, deben hacerla distribuida en 4, 8 y 12 máquinas. Estas se denominan configuraciones.
- En cada configuración, deben ejecutar el sistema para al menos 3 funciones diferentes.
- En cada configuración deben variar los parámetros de el método de aproximación elegido, al menos 3 valores diferentes por cada uno.

Con lo anterior, deben entregar un .zip con los siguientes folders:

- ./dep: Diagrama de deployment de la solución. En una versión del diagrama puesta en PowerPoint, deben identificar y explicar(además de ser reconocibles):
- Estructura de flynn: Almacenamiento (UMA, NUMA, NORMA) y Procesamiento (SIMD, MISD, MIMD)
- Estilos de arquitectura
- Patrones de diseño
- Identificar el mapeo de los componentes de la arquitectura a los tipos de componentes de cada patrón/estilo.
- Explicación de la estrategia para resolver el problema en forma distribuida.
- ./src: código fuente de los proyectos en la estructura definida por convención.
- ./README con instrucciones de compilación y ejecución, y los nombres de los integrantes.
- ./doc: Informe en word incluyendo:
  - (Excel con la tabla de valores de conf-parmas vs tiempos vs. precisión, y respectiva gráfica XY comparativa de las n ejecuciones;
  - Un análisis del resultado comparativo de esas ejecuciones). 
