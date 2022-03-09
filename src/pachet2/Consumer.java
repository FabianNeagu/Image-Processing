package pachet2;
import java.awt.Dimension;
import java.util.Arrays;
//Mosteneste clasa AbstractClassProdCons
public class Consumer extends AbstractClassProdCons  // Preia valorile din buffer puse(Citite din fisier) de Producer
{
	//ImageMatrix buffer este Mostenit din clasa abstracta
	//Referinta comuna la un obiect de tip Buffer prin intermediul caruia Producer si Consumer isi comunica valorile.
	//Dimension d este Mostenit din clasa abstracta 
	// Incapsularea width si height(specifice fisierului) intr-un singur obiect
	byte []vect;  //Vectorul in care Consumerul va prmimi sferturile de imagine
	
    public Consumer(ImageMatrix buffer, Dimension d) //Constructorul clasei
    { //Primeste ca parametri Bufferul din care se vor lua datele si dimensiunea fisierului
        super(buffer,d);//Foloseste constuctorul superclasei()Clasei Abstracte)
        int dim=this.getDimension();
        vect=new byte[dim+1];
    }
    public int getDimension()  //Returneaza produsul dintre inaltimea si latimea imaginii
    { 
    	return (int) ((d.getHeight()*d.getWidth()));  // returneaza un int
    }
    
    @Override
    public void run() //Consumer preia cate 1/4 din imagine de la Producer pe masura ce acesta scrie in Buffer
    {
    	int dim=this.getDimension(); //Dimensiunea totala a imaginii
        for (int i = 0; i < 4; i++) // Se primesc pe rand sferturile produse de Producer
        {
        	byte[] aux=this.buffer.get();  //Vector auxiliar al informatiilor primite din buffer
        	//Sferturile de imagine provenite din buffer(De la Prodeucer) trebuie puse in ordine si pe poitii corecte
        	System.arraycopy(aux, i*dim/4, vect,i*dim/4, dim/4); //Consumer primeste sfertul de imagine de la Producer(din buffer)
        	//vect = this.buffer.get();// Consumer primeste sfertul de imagine de la Producer(din buffer)
            System.out.println("Consumer Thread: " + (i+1) + "/4 din imagine."); //Se afiseaza statusul consumatorului corespunzator fiecarui pas
				try {
					Thread.sleep(1000);  //Thread-ul Consumer asteapta o secunda
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println("Eroare intrerupere Consumer sleep");//Afisare mesaj eroare aparitie exceptie
					e.printStackTrace(); //Afisare detalii exceptie
				}
        }
    }
    public byte[] getVect()  //Returneaza vectroul de bytes corespunzator imaginii
    {
    	return vect;  //Retuneaza vectorul
    }

}