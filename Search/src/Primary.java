import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Primary {
	public static void main(String[] args) {
		Scanner scan = new Scanner (System.in);
		String Path1;
		String Path2;
		String Choice;
		boolean deleteDouble = false;
		boolean checkPath = true;
		
		
		do {
		System.out.println("\nInserisci il path del primo file (Es: C:/user/Desktop/file1.txt)");
		System.out.print("1°Path: ");
		Path1 = scan.next();
		System.out.println("Inserisci il path del secondo file (Es: C:/user/Desktop/file2.txt)");
		System.out.print("2°Path: ");
		Path2 = scan.next();
		System.out.print("Vuoi eliminare eventuali doppioni? (Si/No)\n");
		System.out.print("Scelgo: ");
		Choice = scan.next();
		if(Choice.equalsIgnoreCase("si")) {
			deleteDouble = true;
		}
		
		if (Path1.equals(Path2)) {
			System.out.println("\nAttenzione, hai inserito due path uguali, assicurati di analizzare due file diversi!\n");
			checkPath = false;
		}else {
			checkPath = true;
		}
		}while (checkPath == false);
		
		confrontaFile(Path1, Path2, deleteDouble);
		System.out.println("\nProgramma terminato");
		scan.close();
	}
	
	
	
	private static void confrontaFile(String Path1, String Path2, boolean deleteDouble) {
		Scanner scan2 = new Scanner (System.in);
		ArrayList file1 = new ArrayList(); 
		ArrayList file2 = new ArrayList();
		ArrayList common = new ArrayList();
		file1 = populateArray (Path1, deleteDouble, 1);
		file2 = populateArray (Path2, deleteDouble, 2);
		int choice;
		String s1, s2;
		boolean exit = true;
		
		System.out.println("\nRicerco elementi in comune...");
		int foundElement = 0;
		for (int i = 0; i< file1.size(); i++) {
			s1 = (String)file1.get(i);
			for (int j = 0; j<file2.size(); j++) {
				s2 = (String)file2.get(j);
				if (s1.equalsIgnoreCase(s2)) {
					common.add(s1);
					foundElement ++;
				}
			}
		}
		System.out.println("Elementi in comune trovati: " + foundElement);
		if (foundElement > 0) {
		do {
			exit = true;
			System.out.println("\nVuoi visualizzare qui gli elementi o salvarli sul desktop?\n\t1.Qui\n\t2.Voglio salvarli sul desktop");
			System.out.print("Scelgo: ");
			choice = scan2.nextInt();
			
			switch(choice) {
			
			case 1: 
				System.out.println("\nLista degli elementi in comune: ");
				String str2;
				for (int i = 0; i< common.size(); i++) {
					str2 = (String) common.get(i);
					System.out.println(str2);
				}
			break;
			case 2:
				String desktopPath = System.getProperty("user.home");
				String fileName = desktopPath + "\\Desktop\\elementi_comuni.txt";
				String str;
				try {
					BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));					
					for (int i = 0; i< common.size(); i++) {
						str = (String) common.get(i);
						writer.write(str + "\n");
					}
					System.out.println("\nHo creato un file sul desktop \"elementi_comuni.txt\" ");
					writer.close();
				}catch (IOException e) {
					System.out.println("Problemi con la creazione del file");
					System.exit(0);
				}
			break;
			default:
				System.out.println("\nScelta non corretta, riprovare!\n");
				exit = false;
			
			}
		}while(exit == false);
		}
		scan2.close();
	}
	
	
	
private static ArrayList populateArray(String filePath, boolean deleteDouble,int number) {
	ArrayList array = new ArrayList(); 
	boolean found = false;
	
			try {
				BufferedReader in = new BufferedReader(new FileReader(filePath));
				String str ;
				//Se bisogna rimuovere i doppioni
				if (deleteDouble == true) {
				while ((str = in.readLine()) != null) {	
					found = false;
					for (int i = 0; i< array.size(); i++) {	
						//Rimuove la stringa nel caso in cui corrisponde al rigo vuoto
							if (array.get(i).equals(str) || str.equals("")) {
								found = true;
								break;
							}
					}
					//Se il dato non è stato già inserito viene aggiunto
					if (found == false) {
						array.add(str);
					}
				}
				//Se non bisogna togliere i doppioni aggiungi il nuvo dato
				}else {
					while ((str = in.readLine()) != null ) {
						//Rimuove la stringa nel caso in cui corrisponde al rigo vuoto
						if (str.equals("")) {
						}else
							array.add(str);
					}
				}
						
				in.close();
			} catch(FileNotFoundException e) {
				System.out.println("\nAttenzione, file non trovato, controllare il path!");
				System.out.println("Programma terminato");
				System.exit(0);
			}catch (IOException e) {
				e.printStackTrace();
				}
			System.out.println("Nel file " + number +" ho trovato " + array.size() + " elementi");
	return array;
	}
		
}