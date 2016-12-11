public class CompararAlgoritmos {

	public static int sedesH1(int[] c0, int[] c1, int f) {
		int sede = c0[0] <= c1[0] ? 0 : 1;
		int costes = sede == 0 ? c0[0] : c1[0];
		for (int i = 1; i < c0.length; i++) {
			if (sede == 0) {
				if (c0[i] <= c1[i]) {
					costes += c0[i];
				} else {
					sede = 1;
					costes += c1[i] + f;
				}
			} else {
				if (c1[i] <= c0[i]) {
					costes += c1[i];
				} else {
					sede = 0;
					costes += c0[i] + f;
				}
			}
		}
		return costes;
	}

	public static int sedesH2(int[] c0, int[] c1, int f) {
		int sede = c0[0] <= c1[0] ? 0 : 1;
		int costes = sede == 0 ? c0[0] : c1[0];
		for (int i = 1; i < c0.length; i++) {
			if (sede == 0) {
				if (c0[i] <= c1[i] + f) {
					costes += c0[i];
				} else {
					sede = 1;
					costes += c1[i] + f;
				}
			} else {
				if (c1[i] <= c0[i] + f) {
					costes += c1[i];
				} else {
					sede = 0;
					costes += c0[i] + f;
				}
			}
		}
		return costes;
	}

	private static int sedesVAAux(int[] c0, int[] c1, int f, int costeParcial, int costeOptimo, int[] solParcial,
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
			if (nivel == c0.length - 1) { // el coste parcial y la solucion
											// parcial son soluciones completas
				if (cPar < costeOptimo) {
					costeOptimo = cPar;
					for (int i = 0; i < solParcial.length; i++) {
						solOptima[i] = solParcial[i];
					}
				}
			} else { // Sigue buscando
				costeOptimo = sedesVAAux(c0, c1, f, cPar, costeOptimo, solParcial, solOptima, sede, nivel + 1);
			}

		}
		return costeOptimo;
	}

	public static int sedesVA(int[] c0, int[] c1, int f) {
		int[] solParcial = new int[c0.length];
		int[] solOptima = new int[c0.length];
		int costeOptimo = sedesVAAux(c0, c1, f, 0, Integer.MAX_VALUE, solParcial, solOptima, -1, 0);
		return costeOptimo;
	}

	public static int sedesRyP(int[] c0, int[] c1, int f) {
		int cota = 0;
		for (int i = 0; i < c0.length; i++) { // La cota inicial será la suma de
												// los costes mínimos de cada
												// mes
			cota += Math.min(c0[i], c1[i]);
		}
		int solParcial = 0;
		int solOptima = Integer.MAX_VALUE;
		int sedeAnterior = -1;
		int nivel = 0;
		int costeOptimo = sedesRyPAux(c0, c1, f, nivel, sedeAnterior, solParcial, solOptima, cota);
		return costeOptimo;
	}

	private static int sedesRyPAux(int[] c0, int[] c1, int f, int nivel, int sedeAnterior, int solParcial,
			int solOptima, int cota) {
		for (int sede = 0; sede <= 1; sede++) { // 0 o 1 para ambas sedes
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
			// Actualización de la cota. El valor de la solucion parcial más la
			// suma de
			// los mínimos costes de los meses restantes (estimación)
			nCota = sParcial;
			for (int i = nivel + 1; i < c0.length; i++) {
				nCota += Math.min(c0[i], c1[i]);
			}
			if (nCota < solOptima) {
				if (nivel == c0.length - 1) {
					if (sParcial < solOptima) {
						solOptima = sParcial;
					}
				} else
					solOptima = sedesRyPAux(c0, c1, f, nivel + 1, sede, sParcial, solOptima, nCota);
			}
		}
		return solOptima;
	}
	
	private static int sedesRecAux(int[] c0, int[] c1, int f, int i, int sedeAnterior) {
		if (i == c0.length - 1) {
			if (sedeAnterior == 0) {
				return Math.min(c0[i], c1[i] + f);
			} else {
				return Math.min(c0[i] + f, c1[i]);
			}
		} else {
			if (sedeAnterior == 0) {
				return Math.min(sedesRecAux(c0, c1, f, i + 1, 0) + c0[i],
								sedesRecAux(c0, c1, f, i + 1, 1) + c1[i] + f);
			} else {
				return Math.min(sedesRecAux(c0, c1, f, i + 1, 0) + c0[i] + f,
								sedesRecAux(c0, c1, f, i + 1, 1) + c1[i]);
			}
		}
	}

	public static int sedesRec(int[] c0, int[] c1, int f) {
		return Math.min(sedesRecAux(c0, c1, f, 0, 0), sedesRecAux(c0, c1, f, 0, 1));
	}
	
	public static int sedesPDTabDecisiones(int[] c0, int[] c1, int f) {
		int decisiones[] = new int[c0.length];

		int tabla[][] = new int[2][c0.length];
		tabla[0][c0.length - 1] = c0[c0.length - 1];
		tabla[1][c0.length - 1] = c1[c0.length - 1];
		for (int i = c0.length - 2; i >= 0; i--) {
			tabla[0][i] = Math.min(tabla[0][i + 1] + c0[i], tabla[1][i + 1] + c0[i] + f);
			tabla[1][i] = Math.min(tabla[0][i + 1] + c1[i] + f, tabla[1][i + 1] + c1[i]);
		}

		//decisiones
		for (int i = 0; i < c0.length - 1; i++) {
			decisiones[i] = tabla[0][i] <= tabla[1][i] ? 0 : 1;
		}

		// decision caso base
		if (c0.length > 1) {
			if (decisiones[c0.length - 2] == 0) {
				if (c0[c0.length - 1] <= c1[c0.length - 1] + f) {
					decisiones[c0.length - 1] = 0;
				} else {
					decisiones[c0.length - 1] = 1;
				}
			} else {
				if (c1[c0.length - 1] <= c0[c0.length - 1] + f) {
					decisiones[c0.length - 1] = 1;
				} else {
					decisiones[c0.length - 1] = 0;
				}
			}
		}

		//System.out.println(Arrays.toString(decisiones));
		return Math.min(tabla[0][0], tabla[1][0]);
	}
}
