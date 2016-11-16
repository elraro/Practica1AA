public class EliminacionRecursividad {

	public static int f(int x, int y) {
		if (y == 0)
			return x;
		else
			return f(x, y - 1) + f(x + 1, y - 1);
	}

	public static int fMem(int x, int y) {
		int[][] tabla = new int[y + 1][y + 1];
		for (int i = 0; i < tabla.length; i++) {
			for (int j = 0; j < tabla[0].length; j++) {
				tabla[i][j] = -1;
			}
		}
		fMemAux(x, y, tabla, x);
		return tabla[y][x - x];
	}

	private static void fMemAux(int x, int y, int[][] t, int x_f) {
		if (t[y][x - x_f] == -1) {
			if (y == 0) {
				t[y][x - x_f] = x;
			} else {
				fMemAux(x, y - 1, t, x_f);
				fMemAux(x + 1, y - 1, t, x_f);
				t[y][x - x_f] = t[y - 1][x - x_f] + t[y - 1][x - x_f + 1];
			}
		}
	}

	public static int fTab(int x, int y) {
		int[][] tabla = new int[y + 1][y + 1];
		for (int i = x; i <= x + y; i++) {
			tabla[0][i - x] = i;
		}

		for (int i = 1; i <= y; i++) {
			for (int j = 0; j <= y - i; j++) {
				tabla[i][j] = tabla[i - 1][j] + tabla[i - 1][j + 1];
			}
		}
		return tabla[y][x - x];
	}

	public static int fTabOpt(int x, int y) {
		int[] tabla = new int[y + 1];
		for (int i = x; i <= x + y; i++) {
			tabla[i - x] = i;
		}

		for (int i = 0; i < y; i++) {
			for (int j = 0; j < y - i; j++) {
				tabla[j] = tabla[j] + tabla[j + 1];
			}
		}

		return tabla[0];
	}

	public static void main(String[] args) {
		System.out.println("f=" + f(3, 4));
		System.out.println("fMem=" + fMem(3, 4));
		System.out.println("fTab=" + fTab(3, 4));
		System.out.println("fTabOpt=" + fTabOpt(3, 4));
	}
}
