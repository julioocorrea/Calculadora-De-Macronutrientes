package calcnutri;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class ServiceAlimento {
	public void AdicionarAlimento() {
		Scanner input = new Scanner(System.in);
		
        System.out.println("\n================================");
        System.out.println("         Adicionar Alimento");
        System.out.println("================================");
		
        System.out.println("================================");
		System.out.print("Id: ");
		int id = input.nextInt();
		input.nextLine();
        System.out.println("================================");
		
        System.out.println("================================");
		System.out.print("Nome:");
		String nome = input.nextLine();
		
        System.out.println("================================");
		System.out.print("Caloria: ");
		int kcal = input.nextInt();
		
        System.out.println("================================");
		System.out.print("Proteina: ");
		int prot = input.nextInt();
		
        System.out.println("================================");
		System.out.print("Gordura: ");
		int gord = input.nextInt();
		
        System.out.println("================================");
		System.out.print("Carboidrato: ");
		int carb = input.nextInt();
        System.out.println("================================");
		
		Alimento a = new Alimento(id, nome, kcal, prot, gord, carb);
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("calcnutri-persist");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		em.persist(a);
		
		em.getTransaction().commit();
		
		em.close();
		
        System.out.println("================================");
        System.out.println("Alimento adicionado!");
        System.out.println("================================");
	}
	
	public void ConsultarAlimentoEspecifico() {
		Scanner input = new Scanner(System.in);
		
        System.out.println("\n================================");
        System.out.println("         Consultar Alimento");
        System.out.println("================================");
		
        System.out.println("================================");
		System.out.print("Id do Alimento: ");
		int id = input.nextInt();
		input.nextLine();
        System.out.println("================================");
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("calcnutri-persist");
		EntityManager em = emf.createEntityManager();
		
		Alimento ACon = em.find(Alimento.class, id);

        if (ACon != null) {
            System.out.println("================================");
            System.out.println("Alimento encontrado");
            System.out.println("================================");
            System.out.println(ACon.toString());
            System.out.println("================================");
        } else {
            System.out.println("================================");
            System.out.println("Alimento não encontrado.");
            System.out.println("================================");
        }
        
		em.close();
	}
	
	public List<Alimento> ConsultarTodosAlimentos() {
		Scanner input = new Scanner(System.in);
		
        System.out.println("\n================================");
        System.out.println("         Consultar Alimento");
        System.out.println("================================");
		
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("calcnutri-persist");
		EntityManager em = emf.createEntityManager();
		
		TypedQuery<Alimento> query = em.createQuery("SELECT a FROM Alimento a", Alimento.class);

        List<Alimento> Todosalimentos = query.getResultList();

        if (!Todosalimentos.isEmpty()) {
    		em.close();
        	return Todosalimentos;
        } 
        else {
    		em.close();
        	return null;
        }
	}
	
	public void EditarAlimento() {
		Scanner input = new Scanner(System.in);
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("calcnutri-persist");
		EntityManager em = emf.createEntityManager();
		
        System.out.println("\n================================");
        System.out.println("         Editar Alimento");
        System.out.println("================================");
        
    	TypedQuery<Alimento> query = em.createQuery("SELECT a FROM Alimento a", Alimento.class);

	    Set<Alimento> alimentos = new HashSet<>(query.getResultList());

	    if (!alimentos.isEmpty()) {
	        System.out.println("================================");
	        System.out.println("Lista de Alimentos");
	        System.out.println("================================");

	        for (Alimento alimento : alimentos) {
	            System.out.println("================================");
	            System.out.println(alimento.toString());
	            System.out.println("================================");
	        }
	        
	        System.out.println("================================");
			System.out.print("Id do Alimento que deseja editar: ");
			int id = input.nextInt();
			input.nextLine();
	        System.out.println("================================");
			
			em.getTransaction().begin();
			
			Alimento ACon = em.find(Alimento.class, id);

	        if (ACon != null) {
	            System.out.println("================================");
				System.out.println("Novo Nome: ");
				String novoNome = input.next();
				
	            System.out.println("================================");
				System.out.println("Nova quantidade de Caloria: ");
				int novaCaloria = input.nextInt();
			
	            System.out.println("================================");
				System.out.println("Nova quantidade de Proteina: ");
				int novoProteina = input.nextInt();
				
	            System.out.println("================================");
				System.out.println("Nova quantidade de Gordura: ");
				int novoGordura = input.nextInt();
				
	            System.out.println("================================");
				System.out.println("Nova quantidade de Carboidrato: ");
				int novoCarboidrato = input.nextInt();
		        System.out.println("================================");
				
				try {
					ACon.setNome(novoNome);
					ACon.setKcal(novaCaloria);
					ACon.setProt(novoProteina);
					ACon.setGord(novoGordura);
					ACon.setCarb(novoCarboidrato);

					em.merge(ACon);
					
					em.getTransaction().commit();
		            System.out.println("================================");
					System.out.println("Atualizado com sucesso.");
		            System.out.println("================================");
				} catch(NumberFormatException e) {
		            System.out.println("================================");
					System.out.println("Id invalido");
		            System.out.println("================================");
				}
	        } else {
	            System.out.println("================================");
	            System.out.println("Alimento não encontrado.");
	            System.out.println("================================");
	        }
    	
	    }
	    else {
	        System.out.println("================================");
	        System.out.println("Nenhum Alimento Cadastrado!");
	        System.out.println("================================");
	    } 
	}
	
	public void DeletarAlimento() {
	    Scanner input = new Scanner(System.in);
		
        System.out.println("\n================================");
        System.out.println("         Deletar Alimento");
        System.out.println("================================");

        System.out.println("================================");
	    System.out.print("Id do Alimento: ");
	    int id = input.nextInt();
        System.out.println("================================");
	    
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("calcnutri-persist");
		EntityManager em = emf.createEntityManager();
	    em.getTransaction().begin();
	    
	    Alimento ACon = em.find(Alimento.class, id);
        System.out.println("================================");
        System.out.println("Alimento: ");
        System.out.println("================================");
	    System.out.println(ACon.toString());
        System.out.println("================================");
	    
        System.out.println("================================");
	    System.out.println("Confirmar Delete?\n"
	    				 + "1. Sim\n"
	    				 + "2. Nao");
        System.out.println("================================");
        System.out.print("Digite sua escolha: ");
	    Integer cont = input.nextInt();
        System.out.println("================================");
	    
	    if (cont == 1) {
		    em.remove(ACon);
	        System.out.println("================================");
		    System.out.println("Alimento deletado com sucesso!");
	        System.out.println("================================");
	    }
	    else {
	        System.out.println("================================");
		    System.out.println("Delete cancelado!");
	        System.out.println("================================");
	    }
	    
	    em.getTransaction().commit();
	    
	    em.close();
	}
	
	public void MenuAlimento() {
        Scanner scanner = new Scanner(System.in);
        int escolha;
		
		do {
            System.out.println("\n================================");
            System.out.println("         Menu de Alimentos");
            System.out.println("================================");
            System.out.println("1. Adicionar Alimento");
            System.out.println("2. Editar Alimento");
            System.out.println("3. Consultar Alimentos");
            System.out.println("4. Deletar Alimento");
            System.out.println("5. Sair");
            System.out.println("================================");
            System.out.print("Escolha uma opção: ");

            escolha = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer do scanner

            switch (escolha) {
                case 1:
                	AdicionarAlimento();
                    break;
                case 2:
                	EditarAlimento();
                    break;
                case 3:
                	ConsultarAlimentoEspecifico();
                    break;
                case 4:
                	DeletarAlimento();
                    break;
                case 5:
                    System.out.println("\nSaindo do programa...");
                    break;
                default:
                    System.out.println("\nOpção inválida. Tente novamente.");
                    break;
            }

        } while (escolha != 5);
	}
	
}
