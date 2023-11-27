package calcnutri;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App {
	 public static void main(String[] args) {
		 ServiceAlimento sa = new ServiceAlimento();
		 ServiceDieta sd = new ServiceDieta();
		 
		 sd.MenuDieta();
		 
	}
}
