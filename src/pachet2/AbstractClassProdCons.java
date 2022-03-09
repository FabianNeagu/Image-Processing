package pachet2;
import java.awt.Dimension;

public abstract class AbstractClassProdCons extends Thread//Clasa abstracta ce extinde clasa Thread 
//Pentru implementarea ulterioara a thread-urilor Consumer si Producer
{
	protected ImageMatrix buffer;  // Bufferul(resursa comuna) prin care Consumer si Producer vor comunica
	//Producer pune valori in buffer, iar consumer ia valori din buffer
    protected Dimension d; //Obiect de tip Dimension ce incapsuleaza dimensiunile imaginii width si height
    
    public AbstractClassProdCons(ImageMatrix buffer, Dimension d)  //Constructorul clasei abstracte
    {//va fi folosit de clasele ce o mostenesc
    	this.buffer=buffer;  //Bufferul comun
    	this.d=d; // Dimensiunea
    }
    public void run() // Metoda run specifica Thread-urilor ce va fi suprascrisa in Consumer si Producer
    {
    	
    }
}
