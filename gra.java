import java.util.*;

public class gra {
	
	static final int MAX_RAND = 100;
	
	public static void main(String[] args) {
		try {
			Random rand = new Random();
			int r = rand.nextInt(MAX_RAND + 1);
			int p = 0;
			Scanner in = new Scanner(System.in);
			try {
				for(;;) {
					System.out.print("Podaj liczbę: ");
					int i = in.nextInt();
					++p;
					if(i == r) {
						System.out.println("Poprawna odpowiedz. Liczba prob=" + p);
						p = 0;
						String c;
						do {
							r = rand.nextInt(MAX_RAND + 1);
							System.out.println("Grasz dalej? T/N ");
							c = in.next();
							if(c.equalsIgnoreCase("N"))
								return;
						} while(!c.equalsIgnoreCase("T"));
					} else if(i < r)
						System.out.print("Za mało. ");
					else
						System.out.print("Za dużo. ");
				}
			} finally {				
				in.close();
			}
		} catch(Exception e) {
		System.out.println("Nie podałeś liczby tylko inny znak");
		}
	}

}
