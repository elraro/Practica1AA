public class Sedes {

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
}
