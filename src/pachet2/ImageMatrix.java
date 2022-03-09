package pachet2;
import java.awt.Dimension;
import java.util.*;

public class ImageMatrix // Bufferul dintre cele Producator si Consumator
{ //Se ocupa de sincronizarea Thread-urilor Consumator si Producator

	private int dimensiune;// Dimeniunea imaginii necesara stabilirii dimensiunii bufferului
	private final byte[] vect;  //Vectorul de bytes ce va stoca imaginea(imaginea va fi stocata sub forma vect de bytes)
	//In acest vector se vor pune elementee corespunzatoare fiecarui sfert de imagine
	//Iar mai apoi vectorul de bytes va fi transformat in obiect de tip BufferedImage
	private static boolean available;  //Daca este available sau nu resursa noastra(necesar la coordonarea threadurilor)
	
	public ImageMatrix(int dimensiune) //Constructorul clasei
	{
		this.dimensiune = dimensiune;//Primeste ca parametru dimensiunea
		vect = new byte[dimensiune];//Alocare vector de bytes cu dimensiunea respectiva
		available=false;//Initializare available de tip false->buffer indisponibil pentru citire
		//Disponibil pentru scriere
	}
	
	public byte[] getVect() //Returneaza vectorul (bufferul) in care s-au pus valori
	{ 
		return vect; //Returneaza vectorul care a fost populat de producer
	}
	
	public synchronized byte[] get() // Functie get apelata de Consumer(ia valori din buffer)
	{ 
		if (!available) { //Daca producatorul inca pune valoare in buffer
			try {
				wait(); // Asteapta producatorul sa termine de pus valorile
			} catch (InterruptedException e) {
				System.out.println("Eroare get-wait()"); //Mesaj aparitie exceptie
				e.printStackTrace(); // Afisare detalii exceptie
			}
		}
		available = false;  //Am realizat si terminat get-ul
		//=>acum bufferul e disponibil din nou pentru scriere

		notifyAll(); // Se anunta terminarea functiei
		return vect; //Returnare buffer ce contine bytes din imagine
	}

	public synchronized void put(byte [] vect1, int pozitie) // Functie put apelata de Producer
	{ 
		while (available) {  // Daca Consumatorul inca preia valoarea din buffer
			try {
				wait();
				// Asteapta consumatorul pana termina de preluat valoarea
			} catch (InterruptedException e) {
				System.out.println("Eroare put-wait()");//Mesaj aparitie exceptie
				e.printStackTrace(); // Afisare detalii exceptie
			}		
		}
		// Thread-ul Producer pune cate un sfert din imagine in Vectorul vect pentru a putea fi prelulata de Consumer
		for(int i = pozitie * dimensiune / 4; i < (pozitie + 1)*dimensiune/4; i++) 
		{
			vect[i] = vect1[i - pozitie * dimensiune / 4]; // Se completeaza noile elemente(Nu se suprascriu cele vechi)
		}//Completez mereu incepand cu pozitia: pozitie*dimensiune/4
		
		available = true;//Am realizat put-ul => bufferul e disponibil din nou pentru ciire(De catre consumer)
		notifyAll(); // Se anunta terminarea functiei
	}

}
