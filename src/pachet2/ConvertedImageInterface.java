package pachet2;

import java.awt.image.BufferedImage;
import java.io.File;

//Interfata pe care o va implementa 
public interface ConvertedImageInterface
{
	public void writeImageToFile(File ...outputFiles);//Functie ce scrie imaginea in fisier
	public void convertToGrey(int level);//Functie ce converteste imaginea in GrayScale
	public BufferedImage getProcessedImage();//Functie ce returneaza imaginea(obiect de tip BufferedImage)
}
