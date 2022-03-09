package pachet2;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class Producer extends AbstractClassProdCons //Mosteneste clasa AbstractClassProdCons
{
	//protected ImageMatrix buffer; //Referinta comuna la un obiect de tip Buffer prin intermediul caruia isi comunica valorile.
	//Dimension d;  // Incapsularea width si height(specifice fisierului) intr-un singur obiect
	File inputFile; //Fisierul din care Producatorul va face citirea fiecarui sfert din continutul imaginii
	InputStream is;  //Stream input de Bytes(folosit pentru citirea din fisier) -> Metoda read
	
	//Constructorul clasei
    public Producer(ImageMatrix buffer, File inputFile, Dimension d) //Constructorul
    {//Primeste ca parametrii bufferul in care se vor pune informatiile, fisierul si dimensiunea lui
        super(buffer,d); //Apel constructor superclasei
        this.inputFile = inputFile;  //Fisierul sursa
    }
    
    public int getDimension() // Returneaza dimeniunea calculata din obiectul de tip Dimension
    { 
    	return (int) ((d.getHeight()*d.getWidth()));// Returneaza un int (produs)
    }

    // Producer primeste cate 1/4 din imagine
	@Override
    public void run() 
	{

		int dim = getDimension();	//Dimensiunea fisierului	
        try {
			is = new FileInputStream(inputFile);  //InputStream necesar citirii din fisier
		} catch (FileNotFoundException e1) 
        {
			System.out.println("FileNotFoundException in Producer Thread");//Afisare mesaj eroare aparitie exceptie
			e1.printStackTrace();//Semnalare eroare de tip FileNotFound
		}
		     
        for (int i = 0; i < 4; i++) // Structura repetitiva for cu 4 cicli specifici citirii fiecarui sfert din imagine
        {
        	byte[] vect1 = new byte[dim/4+1]; //Vector de bytes in care se va extrage pe rand fiecare sfert din bitii fisierului
        	try 
        	{
        		is.read(vect1, 0, dim/4);//Se citeste in vectorul vect1 cate un sfert din imagine
        		// Se citeste sferul de imagine corespunzator pasului la care suntem
			} catch (IOException e) {
				System.out.println("Eroare read-Producer");//Afisare mesaj eroare aparitie exceptie
				e.printStackTrace();  //Semnalare eroare de tip IO
			}
        	this.buffer.put(vect1, i);// Producer trimite rezultatul(sfertul de imagine) catre consumer (Prin BUFFER)
        	System.out.println("Producer Thread: " + (i+1) + "/4 din imagine."); //Afisare status executie
            try {
                Thread.sleep(1000); //Threadul Producer asteapta o secunda
            } catch (InterruptedException e) { 
            	System.out.println("Eroare put-Producer");//Afisare mesaj eroare aparitie exceptie
                e.printStackTrace();//Semnalare eroare de tip Intrerupere
            }
        }
    }
}
