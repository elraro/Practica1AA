package practica3.sedesVAyRyP;

import java.util.Arrays;

public class SedesVA {

	static int hojas = 0;
	
	public static int sedesVA(int[] c0, int[] c1, int f, int costeParcial, int costeOptimo, int[] solParcial,
			int[] solOptima, int sedeAnterior, int nivel) {
		for (int sede = 0; sede <= 1; sede++) {
			int cPar = 0;
			if (sedeAnterior == -1) { // Se elige la primera sede
				cPar = sede == 0 ? c0[nivel] : c1[nivel];
				solParcial[nivel] = sede == 0 ? 0 : 1;
			} else {
				if (sede == 0) {
					cPar = sedeAnterior == 0 ? costeParcial + c0[nivel] : costeParcial + c0[nivel] + f;
					solParcial[nivel] = 0;
				} else {
					cPar = sedeAnterior == 1 ? costeParcial + c1[nivel] : costeParcial + c1[nivel] + f;
					solParcial[nivel] = 1;
				}
			}
			if (nivel == c0.length - 1) { // el coste parcial y la solucion parcial son soluciones completas
				hojas++;
				if (cPar < costeOptimo) {
					costeOptimo = cPar;
					for (int i = 0; i < solParcial.length; i++) {
						solOptima[i] = solParcial[i];
					}
				}
			} else { // Sigue buscando
				costeOptimo = sedesVA(c0, c1, f, cPar, costeOptimo, solParcial, solOptima, sede, nivel + 1);
			}

		}
		return costeOptimo;
	}

	public static int sedes(int[] c0, int[] c1, int f) {
		int[] solParcial = new int[c0.length];
		int[] solOptima = new int[c0.length];
		int costeOptimo = sedesVA(c0, c1, f, 0, Integer.MAX_VALUE, solParcial, solOptima, -1, 0);
		System.out.println("Solucion optima: " + Arrays.toString(solOptima));
		System.out.println("Soluciones completas: " + hojas);
		return costeOptimo;
	}

	public static void main(String[] args) {
		int[] c0 = new int[] { 1, 3, 20, 30 };
		int[] c1 = new int[] { 50, 20, 2, 4 };
		int f = 10;
		// {s0,s0,s1,s1} con un coste igual 1+3+10+2+4=20
		System.out.println(sedes(c0, c1, f));
	}
}
