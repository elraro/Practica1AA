package practica3.sedesVAyRyP;

public class SedesRyP {

	static int hojas = 0;

	public static int sedes(int[] c0, int[] c1, int f) {
		int cota = 0;
		for (int i = 0; i < c0.length; i++) { // La cota inicial será la suma de los costes mínimos de cada mes
			cota += Math.min(c0[i], c1[i]);
		}
		int solParcial = 0;
		int solOptima = Integer.MAX_VALUE;
		int sedeAnterior = -1;
		int nivel = 0;
		int costeOptimo = sedesRyP(c0, c1, f, nivel, sedeAnterior, solParcial, solOptima, cota);
		System.out.println("Soluciones completas: " + hojas);
		return costeOptimo;
	}

	private static int sedesRyP(int[] c0, int[] c1, int f, int nivel, int sedeAnterior, int solParcial, int solOptima,
			int cota) {
		for (int sede = 0; sede <= 1; sede++) { // 0 o 1 para ambas sedes
			System.out.println("Cota: " + cota);
			int nCota = 0;
			int sParcial = 0;
			if (sedeAnterior == -1) {
				sParcial = sede == 0 ? c0[nivel] : c1[nivel];
			} else {
				if (sede == 0) {
					int coste0 = sedeAnterior == 0 ? c0[nivel] : c0[nivel] + f;
					sParcial = solParcial + coste0;
				} else {
					int coste1 = sedeAnterior == 1 ? c1[nivel] : c1[nivel] + f;
					sParcial = solParcial + coste1;
				}
			}
			// Actualización de la cota. El valor de la solucion parcial más la suma de
			// los mínimos costes de los meses restantes (estimación)
			nCota = sParcial;
			for(int i = nivel + 1; i < c0.length; i++) {
				nCota += Math.min(c0[i], c1[i]);
			}
			if (nCota < solOptima) {
				if (nivel == c0.length - 1) {
					hojas++;
					if (sParcial < solOptima) {
						solOptima = sParcial;
						System.out.println("Encontrada solucion: " + solOptima + " con cota: " + nCota);
					}
				} else
					solOptima = sedesRyP(c0, c1, f, nivel + 1, sede, sParcial, solOptima, nCota);
			} else {
				System.out.println("Poda con cota: " + nCota);
			}
		}
		return solOptima;
	}

	public static void main(String[] args) {
		int[] c0 = new int[] { 1, 3, 20, 30 };
//		int[] c0 = new int[] { 1, 3, 2, 3 };
		int[] c1 = new int[] { 50, 20, 2, 4 };
		int f = 10;
		System.out.println(sedes(c0, c1, f));
	}
}
