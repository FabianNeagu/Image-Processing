package pachet2;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;


//Clasa ProcessedImage contine ca variabile Imaginea initiala si imaginea prelucrata
//Contine functii de prelucrare a imaginii initiala in scopul obtinerii imaginii alb-negru
//Contine si functie de scriere a imaginii prelucrate in fisier/fisiere
//Functia extinde clasa InitialImage si implementeaza interfata ProcessedImageInterface
public class ConvertedImage extends InitialImage implements ConvertedImageInterface 
{
	// Variabila initialImage ce contine imaginea initiala citita din fisier este mostenita din clasa InitialImage
	private BufferedImage processedImage;
	
	//Prin procedeul de inheritance se mostenesc si functiile clasei parinte (getDimension si ReadFromFile)
	
	public ConvertedImage(File inputFile)  //Constructorul clasei
	{
		super(inputFile);//Apeleaza constructorul superclasei care ca citi imaginea imaginea din fisier
		//Si o va pune in obiectul de tip BufferedImage initialImage
		processedImage = initialImage;//initial setam arbitrat ca imaginea convertita sa fie identica cu cea initiala
		//Aceasta se va modifica dupa aplicarea algoritmului Average Method
	}
	
	
	public BufferedImage getProcessedImage() //functie ce returneaza imaginea procesata
	{
		return processedImage; //Se returneaza BufferedImage processedImage(Imaginea convertita)
	}
	
	
	//Prelucrare imagine - Algoritm convert Color Image to Gray-Scale Image – Average method
	public void convertToGrey(int level)
	{//Primeste ca parametri imagine de tip BufferedImage si "nivelul de grey" dorit a fi aplicat imaginii
		long startTime = System.currentTimeMillis();// Contorizare timp executie prelucrare fisier - start
		int width = this.initialImage.getWidth(); //Latimea imaginii
        int height = this.initialImage.getHeight(); //Inaltimea imaginii
       
        BufferedImage newImg = null; //Imaginea ce va fi returnata(imaginea prelucrata)
        newImg = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);//Setare dimensiuni imagine noua
        //Pixelii imaginii de input ii voi citi si prelucra, iar in final ii voi stoca intr-o matrice(cu liste)
        //Implementata sub forma de lista de lista
        //Pentru a se putea modifica si insera mai usor elemente( Foarte util la algoritmi complexi )
        List<List<Integer>> list = new ArrayList<>();//Lista de liste(matrice)in care se vor stoca pixelii prelucrati
        
        for(int x=0;x<height;x++)//Parcurgere linii
        {
        	list.add(new ArrayList<>());//adaugare linie noua in lista
        	for(int y=0;y<width;y++) //Parcurgere coloane
        	{
        		int pixel=this.initialImage.getRGB(y,x);//Pixelul din imagine de pe linia x si coloana y
        		
        		int a = (pixel>>24)&0xff;//Valoara Alpha a pixelului
        		int r = (pixel>>16)&0xff;//Valoara Red a pixelului
        		int g = (pixel>>8)&0xff;//Valoara Green a pixelului
        		int b = pixel&0xff;//Valoara Blue a pixelului
        		
        		int avg = (r+g+b)/(level+2); //Metoda mediei
        		
        		int newPixel = (a<<24) | (avg<<16) | (avg<<8) | avg;//Gasim valoarea noului pixel pentru a obtine imaginea dorita
        		
        		list.get(x).add(newPixel);//adaugare pixel nou(prelucrat) in lista(linia x si coloana y)
        	}
        }
        for(int x=0;x<height;x++)//Parcurgere linii
        	for(int y=0;y<width;y++)//Pracurgere coloane
        	{
        		newImg.setRGB(y,x,list.get(x).get(y));// Copiere valorile din lista in Valorile pixelilor imaginii noi
        	}
        long endTime = System.currentTimeMillis();// Contorizare timp executie program - momentul de stop
        System.out.println("Durata prelucrare imagine Convert to Grey-Scale: " + (endTime - startTime) + " milisecunde");//Timp executie
        processedImage=newImg;//Imaginea prelucrata o stocam in variabila de clasa processedImage
	}
	
	
	
	// Scrierea imaginii image in fisierul outputFile
	public void writeImageToFile(File ...outputFiles)  //Lucrul cu varargs(Se poate sa dorim sa stocam imaginea in mai multe fisiere)
	{
		long startTime = System.currentTimeMillis();// Contorizare timp executie scriere in fisier - start
		for(File s : outputFiles)  //Parcurgere fisiere in care se doreste stocarea imaginii
		{
			try {
				ImageIO.write(processedImage, "png", s); //Scriere imagine
			} catch (IOException e) {
				System.out.println("Eroare la scriere Imagine / Path Incorect!");//Afisare mesaj aparitie exceptie
				e.printStackTrace();//Afisare detalii exceptie aparuta
			}
		
			long endTime = System.currentTimeMillis();// Contorizare timp executie scriere in fisier - stop
			System.out.println("Durata scriere imagine in fisier: " + (endTime - startTime) + " milisecunde\n");
			//Calculul timpului necesar pentru scrierea imaginii
		}
	}
	
}
