import java.util.ArrayList;

import domini.ControladorGraf;
import domini.Graf;
import domini.HeteSim;
import domini.Matriu;
import drivers.Driver;

public class Test extends Driver {
	
	public static void main(String[] args) {
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
		println(passTest(g));
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

	private static String passTest(Graf g) {
		Matriu m1 = g.consultarMatriuPaperConferencia().transposada().normalitzadaPerFiles();
		Matriu m2 = g.consultarMatriuPaperAutor().normalitzadaPerColumnes();
		// CP * PA
		int N = 3;
		ArrayList<Long> times = new ArrayList<>(N);
		for (int i = 0; i < N; ++i) {
			System.out.println("M " + i);
			long start = System.nanoTime();
			m1.multiplicar(m2);
			long time = System.nanoTime() - start;
			times.add(time);
			System.out.println(time + "ns");
		}
		/*String res = "";
		Matriu m = g.consultarMatriuPaperAutor();
		Matriu mf = m.normalitzadaPerFiles();
		testfiles: for (int i = 0; i < m.getFiles(); ++i) {
			ArrayList<Double> fn = m.getFilaNormalitzada(i);
			for (int j = 0; j < m.getColumnes(); ++j) {
				if (!fn.get(j).equals(Double.valueOf(mf.get(i, j)))) {
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
				if (!cn.get(i).equals(Double.valueOf(mc.get(i, j)))) {
					res += ", FAIL COLUMNES";
					break testcolumnes;
				}
			}
		}
		if (!res.endsWith("FAIL COLUMNES"))
			res += ", PASS COLUMNES";*/
		return "Mult: " + ((times.stream().mapToLong(Long::longValue).average().getAsDouble())/1000000L) + "ms";
	}
	
}
