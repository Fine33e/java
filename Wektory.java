
import java.util.*;
import java.io.*;

class WektoryException extends Exception {
	
	public final int dlA, dlB;
	
	public WektoryException(int dlA, int dlB) {
		super("Wektory maja rozne dlugosci");
		this.dlA = dlA;
		this.dlB = dlB;
	}
	
}

public class Wektory {

	public static void main(String[] args) {
		try {
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader rd = new BufferedReader(isr);
			Vector<Double> C = null;
			boolean w = false;
			do {
				w = false;
				System.out.print("Podaj pierwszy wektor: ");
				Vector<Double> A = readVec(rd.readLine());
				System.out.print("Podaj drugi wektor: ");
				Vector<Double> B = readVec(rd.readLine());
				try {
					C = addVec(A, B);
				} catch(WektoryException e) {
					System.out.println("ERROR: " + e.getMessage() + " pierwszy ma dlugosc " + e.dlA + " a drugi " + e.dlB);
					System.out.println("Podaj ponownie wektory.");
					w = true;
				}
			} while(w);
			saveVec(C, "suma.txt");
			rd.close();
			isr.close();
		} catch(IOException e) {
			System.out.println("ERROR IO" + e.getLocalizedMessage());
		}
	}
	
	static Vector<Double> readVec(String l) {
		Scanner scan = new Scanner(l);
		Vector<Double> vec = new Vector<Double>();
		while(scan.hasNext()) {
			if(scan.hasNextDouble())
				vec.add(scan.nextDouble());
			else
				scan.next();
		}
		scan.close();
		return vec;
	}
	
	static Vector<Double> addVec(Vector<Double> A, Vector<Double> B) throws WektoryException {
		if(A.size() != B.size())
			throw new WektoryException(A.size(), B.size());
		Vector<Double> C = new Vector<Double>(A.size());
		for(int i = 0; i < A.size(); ++i)
			C.add(A.elementAt(i) + B.elementAt(i));
		return C;
	}
	
	static void saveVec(Vector<Double> v, String f) throws IOException {
		OutputStream out = new FileOutputStream(f);
		Writer wr = new OutputStreamWriter(out);
		PrintWriter pr = new PrintWriter(wr);
		for(int i = 0; i < v.size(); ++i)
			pr.print(v.elementAt(i) + " ");
		pr.close();
		wr.close();
		out.close();
	}

}
