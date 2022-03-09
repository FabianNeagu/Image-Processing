package pachet1;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import javax.imageio.ImageIO;

import pachet2.ConvertedImage;
import pachet2.InitialImage;


public class MainTest
{
	public static void main(String args[]) throws IOException
	{
		File fisierInput = null; //fisier de input din care se va citit imaginea dorita a fi prelucrata
		File fisierOutput=null; //fisier de output in care se va scrie imaginea prelucrata
		BufferedImage imageInput; //imaginea care se citeste din fisier
		BufferedImage imageOutput; //imaginea(prelucrata) care se va scrie in fisier
		
		System.out.println("Introduceti Path-ul de input: "); // Introducere path-ul fisierului de input
		System.out.println("Exemplu: D:\\AWJ - Aplicatii Web Suport Java\\Final_form\\input1.bmp");// Afisare exemplu path
		Scanner input=new Scanner(System.in); //Creare obiect de tip Scanner pentru citirea path-ului
		String pathInput=input.nextLine(); //Citire si stocare path in variabila de tip string
		fisierInput = new File(pathInput); //Crearea fisierului de input pe baza path-ului respectiv
		System.out.println("Introduceti Path-ul de output: ");// Introducere path-ul fisierului de output
		String pathOutput=input.nextLine();//Citire si stocare path in variabila de tip string
		fisierOutput=new File(pathOutput);//Crearea fisierului de output pe baza path-ului respectiv
		System.out.println("Introduceti nivelul de alb-negru dorit( 1 - 10 ): ");  //Variabila ce repreinta nicelul de alb-negru
		int level=input.nextInt(); //Nivel de alb-negru
		System.out.println("Executie inceputa ! "); //Semanalare incepere executie
	
		
		ConvertedImage processedImageObj=new ConvertedImage(fisierInput); //Creare obiect de tip ProcessedImage
		//ce contine atat imaginea citita cat si iaginea viitor prelucrata
		imageInput=processedImageObj.getInitialImage(); // Aceasta este imaginea initiala
		processedImageObj.convertToGrey(level); //Apelare algortim ce convertire a imaginii initiale
		imageOutput=processedImageObj.getProcessedImage();//Aceasta este imaginea convertita
		processedImageObj.writeImageToFile(fisierOutput); //Scriere imagine prelucrata in fisier
		System.out.println("Vizualizati fisierul output ! ");  //Semanalare terinare proces complet
	}
}
