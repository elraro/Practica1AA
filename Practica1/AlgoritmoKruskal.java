package algoritmos.avanzados.practica1;

import java.util.Arrays;

public class AlgoritmoKruskal {

	public static int Kruskal(int[] us, int[] vs, int[] costes, int n) {
		// calcula el coste mínimo y los arcos correspondientes
		// grafo como lista de adyacencia
		// vectores que representan los arcos del MST (orígenes y destinos)
		int[] orig = new int[n - 1];
		int[] dest = new int[n - 1];
		int a = 0;
		int coste = 0;
		// representación implícita del bosque de nodos que forman el MST
		int[] conjs = new int[n];
		for (int i = 0; i < n; i++)
			conjs[i] = i;
		// creacion del vector índice de costes ordenado
		int[] indicesOrdenados = ordenarIndices(costes);
		// bucle voraz
		for (int i = 0; i < indicesOrdenados.length && a < n; i++) {
			// se selecciona el arco más corto
			int e = indicesOrdenados[i];
			int u = us[e];
			int v = vs[e];
			// se halla el conjunto disjunto de sus nodos
			int conju = conjs[u];
			int conjv = conjs[v];
			// se comprueba si pertenecen a conjuntos disjuntos
			if (conju != conjv) {
				orig[a] = u;
				dest[a] = v;
				a++;
				coste += costes[e];
				// se fusionan
				int min = Math.min(conju, conjv);
				int max = Math.max(conju, conjv);
				for (int k = 0; k < n; k++)
					if (conjs[k] == max)
						conjs[k] = min;
			}
		}
		imprimirVectoresEntrada(us, vs, costes);
		imprimirArcos(orig, dest);
		return coste;
	}

	private static void imprimirVectoresEntrada(int[] us, int[] vs, int[] costes) {
		System.out.println("Imprimimos los vectores de entrada para demostrar que no han sido modificados.");
		System.out.println("Vector us: " + Arrays.toString(us));
		System.out.println("Vector vs: " + Arrays.toString(vs));
		System.out.println("Vector costes: " + Arrays.toString(costes));
		System.out.println("-------------------------");
	}

	private static void imprimirArcos(int[] orig, int[] dest) {
		System.out.println("Arcos con los que se forma el árbol recubridor mínimo: ");
		for (int i = 0; i < orig.length; i++)
			System.out.println("Arco " + i + ": (" + orig[i] + "," + dest[i] + ")");
		System.out.println("-------------------------");
	}

	private static int[] ordenarIndices(int[] v1) {
		// ordena los datos del vector v1 en orden creciente respecto a sus índices
		int[] v2 = new int[v1.length];
		v2[0] = 0;
		for (int i = 1; i < v1.length; i++) {
			float aux = v1[i];
			int j;
			for (j = i - 1; j >= 0 && v1[v2[j]] > aux; j--)
				v2[j + 1] = v2[j];
			v2[j + 1] = i;
		}
		return v2;
	}

	public static void main(String[] args) {
		int[] us = { 0, 0, 0, 1, 1, 2, 2, 3};
		int[] vs = { 1, 2, 4, 3, 4, 3, 4, 4};
		int[] cs = {16, 12, 21, 6, 11, 18, 33, 14};
		int n = 5;
		System.out.println("Longitud del árbol recubridor mínimo: " + Kruskal(us,vs,cs,n));

	}
}
