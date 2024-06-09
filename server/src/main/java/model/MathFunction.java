package model;

/*
    Define la estructura de una funcion matematica simple, recibe una variable x y retorna el
    resultado de evaluar la variable en la funcion.
 */
@FunctionalInterface
public interface MathFunction {
    double solve(double x);
}
