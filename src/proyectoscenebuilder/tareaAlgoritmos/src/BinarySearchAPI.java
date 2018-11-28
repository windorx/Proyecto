
/**
 * Algoritmos y Estructuras de Datos. 2018-2
 * Tarea Unidad 1
 * 
 */

public interface BinarySearchAPI {

    /**
     * Método que recibe un arreglo de enteros positivos ordenados y un número
     * para realizar una búsqueda en el arreglo de tal número.
     *
     * @param array es el arreglo en cual realizará la búsqueda
     * @param key es el número a buscar
     * @return El índice del número búscado. -1 si es que no se encuentra
     */
    public int binarySearch(int[] array, int key);

}
