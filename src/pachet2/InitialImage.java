package pachet2;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.*;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;

public class InitialImage implements InitialImageInterface  //Clasa ce contine doar imaginea initiala drept variabila de clasa(Ce va fi citita din fisier)
{	
	
	protected BufferedImage initialImage;  //imaginea initiala(ce va fi citita din fisier)
	
	
	public InitialImage(File inputFile) // Constructorul clasei
	{ //Primeste ca parametru fisierul din care se va citi imaginea
		try {
			initialImage= readImageFromFile(inputFile);  //Este apelata functia de citire din fisier, imaginea este construita din fisier
			//Sfert cu sfert
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();  // Semnalare eroare
			System.out.println("Eroare la citire Imagine / Path Incorect!"); //Afisare mesaj aparitie exceptie
		} // de obicei eroarea se refera la introducerea gresita a path ului de input
	}
	
	
	public BufferedImage getInitialImage()  // Getter ce are rolul de a returna imaginea citita din fisier
	{//returneaza obiect de tip BufferedImage
			return initialImage; // Imaginea citita din fisier este stocata intr-un obiect de tip BufferedImage
	}//cu numele InitialImage 

	
	// Returneaza dimesniunile imaginii ca obiect de tip Dimension(incapsuleaza width si height)
	public Dimension getDimension(File inputFile) throws IOException 
	{ 
		BufferedImage buffImg; //Obiect de tip BufferedImage necesar pentru aflarea dimensiunilor imaginii
		buffImg = ImageIO.read(inputFile); // Citesc continutul din fisier ca BufferedImage pentru a afla dimensiunile imaginii
		int width = buffImg.getWidth();  //Determinare latime imagine
		int height = buffImg.getHeight();  //Determinare inaltime imagine
		return new Dimension(width, height);  //Retunare obiect de tip Dimension ce contine Width si Height
		//Incapsulate intr-un singur Obiect de tip Dimension
	}

	// Pornirea therdurilor pentru citirea imaginii din fisierul inputFile
	public BufferedImage readImageFromFile(File inputFile) throws IOException 
	{ // Citire imagine prin intermediul thread-urilor Consumer si Producer
		BufferedImage img=null; //Imaginea ce va fi returnata(Imaginea citita)
		long startTime = System.currentTimeMillis(); // Contorizare timp executie program - momentul de start
		Dimension d = getDimension(inputFile); // Dimensiunea fisierului
		ImageMatrix buff = new ImageMatrix((int) (d.getHeight() * d.getWidth() ) );//Crearea  Bufferului in care
		//se vor pune/lua informatii de catre cele 2 Thread-uri(Producer pune sferturi de imagine din buffer si Consumer le preia)
		
		
		Producer producerThread = new Producer(buff, inputFile, d); // Thread 1 - Producer(Citeste sferturi din fisier si pune in buffer)
		Consumer consumerThread = new Consumer(buff, d); // Thread 2 - Consumer(Primeste cea ce Producer citeste(ia din buffer sferturi de imagine))
		producerThread.start(); // Pornire thread Producer
		consumerThread.start(); // Pornire thread Consumer
		
		try {
			consumerThread.join();//Thread main este blocat pana cand se termina thread 2
			//producerThread.join();
		} catch(Exception e) {
			System.out.println("Exceptie la join");//Afisare mesaj aparitie exceptie
			e.printStackTrace();//Afisare detalii exceptie aparuta
		}
		
		// Transform din vector de bytes in BufferedImage!!!
		//ByteArrayInputStream bais = new ByteArrayInputStream(buff.getVect()); //As fi putut prelua vectorul de bytes
		//si direct din buffer, reultatul ar fi fost identic
		ByteArrayInputStream bais = new ByteArrayInputStream(consumerThread.getVect());//Preiau vectorul de bytes de la Consumer
		
		try {
			img = ImageIO.read(bais); //Transformare din vector de biti in BufferedImage!!!
		} catch (IOException e) {
			System.out.println("Eroare transformare vector biti in BufferedImage");//Afisare mesaj aparitie exceptie
			e.printStackTrace();//Afisare detalii exceptie aparuta
		}
		long endTime = System.currentTimeMillis();// Contorizare timp executie program - momentul de stop
		System.out.println("\nDurata citire imagine din fisier: " + (endTime - startTime) + " milisecunde"); 
		//Calculul timpului necesar pentru citirea imaginii
		return img; //Retuneaza un Buffered image(imaginea citita)
	}
	
}
