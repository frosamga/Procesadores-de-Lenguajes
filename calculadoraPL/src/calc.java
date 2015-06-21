import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * 
 * @author Alan
 *
 *Calculadora para Procesadores de Lenguajes.
 *Para que funcione se le debe pasar como argumento el fichero en cuestion.
 *Funciona con el evaluador ScriptEngineManager.
 */

public class calc {

	public static void main(String[] args) throws IOException, ScriptException {

		if (args.length == 2) {
			String linea;
			File archivo = new File(args[0]);
			FileReader fr = new FileReader(archivo);
			BufferedReader br = new BufferedReader(fr);

			while ((linea = br.readLine()) != null)
				evaluar(linea, args[1]);
		}

	}

	public static void evaluar(String linea, String ficheroSalida)
			throws ScriptException {
		FileWriter fichero = null;
		PrintWriter pw = null;
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine interprete = manager.getEngineByName("js");
		StringTokenizer st = new StringTokenizer(linea);
		StringBuilder sb = new StringBuilder();
		while (st.hasMoreTokens()) {
			sb.append(st.nextToken());

		}

		if (sb.toString().contains("--")) {
			sb.replace(0, sb.length(), (sb.toString().replace("--", "+")));
		}
		if (sb.toString().contains("++")) {
			sb.replace(0, sb.length(), (sb.toString().replace("++", "+")));
		}
		if (sb.toString().contains("-+")) {
			sb.replace(0, sb.length(), (sb.toString().replace("-+", "-")));
		}
		if (sb.toString().contains("+-")) {
			sb.replace(0, sb.length(), (sb.toString().replace("+-", "-")));
		}

		if (ficheroSalida != null) {
			
			try {
				fichero = new FileWriter(ficheroSalida,true);
				
				if (!sb.toString().isEmpty()) {
					Object result = interprete.eval(sb.toString());
					fichero.write((int) Double.parseDouble(result.toString())+"\n");					
				} else {
					fichero.write("\n");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (null != fichero)
						fichero.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		} else {
			if (!sb.toString().isEmpty()) {
				Object result = interprete.eval(sb.toString());
				System.out.println((int) Double.parseDouble(result.toString()));
			} else {
				System.out.println("");
			}
		}
	}
}
