package pachet2;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public interface InitialImageInterface  //Interfata ce va fi implementata de Clasa InitialImage
{
	public Dimension getDimension(File inputFile) throws IOException; //Functie ce returneaza Dimeniunea imaginii
	public BufferedImage readImageFromFile(File inputFile) throws IOException;//Functie ce citeste imaginea din fisier(sincronizare)
	public BufferedImage getInitialImage();//Functie ce returneaza imaginea citita din fisier
}
