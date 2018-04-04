
import java.util.*;
import java.io.*;
import java.nio.*;
import java.nio.file.*;
import java.nio.channels.*;

public class Pliki {
	
	static final int NUMBER_OF_CHARACTERS = 1000;
	
	static final int FIRST_ASCII_CHAR = 32;
	static final int LAST_ASCII_CHAR = 126;

	public static void main(String[] args) {
		Random rand = new Random();
		char[] tab = new char[NUMBER_OF_CHARACTERS];
		for(int i = 0; i < NUMBER_OF_CHARACTERS; ++i)
			tab[i] = (char)(rand.nextInt(LAST_ASCII_CHAR - FIRST_ASCII_CHAR + 1) + FIRST_ASCII_CHAR);
		test("java.io", "plik_io.txt", new PlikIO(), tab);
		test("java.nio", "plik_nio.txt", new PlikNIO(), tab);
	}
	
	static void test(String n, String f, Plik p, char[] s) {
		try {
			System.out.print(n + " Zapisywanie: ");
			long wrt0 = System.currentTimeMillis();
			p.zapisz(f, s);
			long wrt1 = System.currentTimeMillis();
			System.out.println((wrt1 - wrt0) + "ms");
			System.out.print(n + " Odczytywanie: ");
			long rdt0 = System.currentTimeMillis();
			char[] s2 = p.odczytaj(f);
			long rdt1 = System.currentTimeMillis();
			System.out.println((rdt1 - rdt0) + "ms");
			System.out.println(s2);
		} catch(Exception e) {
			System.out.println();
			System.out.println(n + " Error: " + e.getLocalizedMessage());
			System.out.println();
		}
	}

}

interface Plik {
	void zapisz(String f, char[] s) throws Exception;
	char[] odczytaj(String f) throws Exception;
}

class PlikIO implements Plik {
	
	@Override
	public void zapisz(String f, char[] s) throws Exception {
		OutputStream out = new FileOutputStream(f);
		Writer wr = new OutputStreamWriter(out);
		wr.write(s);
		wr.close();
		out.close();
	}

	@Override
	public char[] odczytaj(String f) throws Exception {
		File file = new File(f);
		long len = file.length();
		char[] t = new char[(int)len];
		InputStream in = new FileInputStream(file);
		Reader rd = new InputStreamReader(in);
		rd.read(t);
		rd.close();
		in.close();
		return t;
	}
	
}

class PlikNIO implements Plik {

	@Override
	public void zapisz(String f, char[] s) throws Exception {
		Path path = Paths.get(f);
		FileChannel fch = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
		ByteBuffer bbuf = ByteBuffer.allocate(s.length * 2);
		CharBuffer cbuf = bbuf.asCharBuffer();
		cbuf.put(s);
		fch.write(bbuf);
		fch.close();
	}

	@Override
	public char[] odczytaj(String f) throws Exception {
		Path path = Paths.get(f);
		FileChannel fch = FileChannel.open(path, StandardOpenOption.READ);
		ByteBuffer bbuf = ByteBuffer.allocate((int)fch.size());
		CharBuffer cbuf = bbuf.asCharBuffer();
		fch.read(bbuf);
		fch.close();
		char[] t = new char[cbuf.length()];
		cbuf.get(t);
		return t;
	}
}
