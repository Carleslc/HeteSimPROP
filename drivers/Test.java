package drivers;

import java.io.IOException;
import java.util.ArrayList;

import domini.ControladorGraf;
import domini.Graf;
import domini.HeteSim;
import domini.Matriu;

public class Test extends Driver {
	
	public static void main(String[] args) throws IllegalArgumentException, IOException, InterruptedException {
		long start = System.nanoTime();
		ControladorGraf cg = new ControladorGraf();
		try {
			cg.importar("D:\\Descargas\\DBLP_four_area\\DBLP_four_area\\");
		} catch (Exception e) {
			print(e);
		}
		long elapsed = (System.nanoTime() - start) / 1000000000;
		System.out.println("Tiempo de carga " + elapsed + "s");
		Graf g = cg.getGraf();
		println("Papers: " + g.consultaMidaPaper());
		println("Conferencies: " + g.consultaMidaConferencia());
		println("Termes: " + g.consultaMidaTerme());
		println("Autors: " + g.consultaMidaAutor());
		//println(passTest(g));
		HeteSim hs = new HeteSim(g);
		String path;
		do {
			print("Path: ");
			path = nextWord();
			println("Start!");
			start = System.nanoTime();
			hs.clausura(path);
			elapsed = (System.nanoTime() - start) / 1000000000;
			System.out.println("Tiempo total " + elapsed + "s");
			println("Finished");
		} while (!path.equals("0"));
	}

	/**
	 * Comprova el temps de multiplicar matrius normalitzades (CP*PA)
	 * i tamb\u00E9 comprova que les funcions de normalitzar retornen els mateixos resultats.
	 * @throws InterruptedException 
	 */
	@SuppressWarnings("unused")
	private static String passTest(Graf g) throws InterruptedException {
		// Test que hace la media del tiempo de 5 multiplicaciones iguales
		Matriu m1 = g.consultarMatriuPaperConferencia().transposada().normalitzadaPerFiles();
		Matriu m2 = g.consultarMatriuPaperAutor().normalitzadaPerColumnes();
		int N = 5;
		// CP*PA normalitzades
		ArrayList<Long> times = new ArrayList<>(N);
		for (int i = 0; i < N; ++i) {
			System.out.println("M " + i);
			long start = System.nanoTime();
			m1.multiplicar(m2);
			long time = System.nanoTime() - start;
			times.add(time);
			System.out.println(time + "ns");
		}
		// Test per comprovar si les funcions de normalitzaci\u00F3 funcionen correctament
		// Compara els resultats de normalitzadaPerFilas/Columnes amb els creats mitjan\u00E7ant
		// la fila/columna normalitzada corresponent
		long start = System.nanoTime();
		String res = "";
		Matriu m = g.consultarMatriuPaperAutor();
		Matriu mf = m.normalitzadaPerFiles();
		testfiles: for (int i = 0; i < m.getFiles(); ++i) {
			ArrayList<Double> fn = m.getFilaNormalitzada(i);
			for (int j = 0; j < m.getColumnes(); ++j) {
				if (fn.get(j).floatValue() != (float)mf.get(i, j)) {
					res += "FAIL FILES";
					break testfiles;
				}
			}
		}
		if (!res.equals("FAIL FILES"))
			res += "PASS FILES";
		Matriu mc = m.normalitzadaPerColumnes();
		testcolumnes: for (int j = 0; j < m.getColumnes(); ++j) {
			ArrayList<Double> cn = m.getColumnaNormalitzada(j);
			for (int i = 0; i < m.getFiles(); ++i) {
				if (cn.get(i).floatValue() != (float)mc.get(i, j)) {
					res += ", FAIL COLUMNES";
					break testcolumnes;
				}
			}
		}
		if (!res.endsWith("FAIL COLUMNES"))
			res += ", PASS COLUMNES";
		long end = System.nanoTime();
		return "Mult: " + ((times.stream().mapToLong(Long::longValue).average().getAsDouble())/1000000L) + "ms\n" + res
				+ " (" + (end - start)/1000000L + "ms)";
	}
	
}
