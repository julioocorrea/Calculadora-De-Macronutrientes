package calcnutri;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class ServiceDieta {
	public void AdicionarDieta() {
	    ServiceAlimento sa = new ServiceAlimento();
	    Scanner input = new Scanner(System.in);

	    System.out.println("\n==============================================================================");
	    System.out.println("         Adicionar Dieta");
	    System.out.println("==============================================================================");

	    EntityManagerFactory emf = Persistence.createEntityManagerFactory("calcnutri-persist");
	    EntityManager em = emf.createEntityManager();

	    Dieta d = new Dieta();

	    em.getTransaction().begin();

	    em.persist(d);

	    em.getTransaction().commit();

	    em.close();

	    System.out.println("==============================================================================");
	    System.out.println("Dieta adicionada!");
	    System.out.println("==============================================================================");
	}

	public void AdicionarAlimentos() {
		ServiceAlimento sa = new ServiceAlimento();
		Scanner input = new Scanner(System.in);

		System.out.println("\n==============================================================================");
		System.out.println("   Adicionar Alimentos a Dieta");
		System.out.println("==============================================================================");

		System.out.println("==============================================================================");
		System.out.print("Id da Dieta: ");
		int idDieta = input.nextInt();
		input.nextLine();
		System.out.println("==============================================================================");

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("calcnutri-persist");
		EntityManager em = emf.createEntityManager();

		Dieta dietaExistente = em.find(Dieta.class, idDieta);

		if (dietaExistente != null) {
		    TypedQuery<Alimento> query = em.createQuery("SELECT a FROM Alimento a", Alimento.class);

		    Set<Alimento> alimentos = new HashSet<>(query.getResultList());


		    if (!alimentos.isEmpty()) {
		        System.out.println("==============================================================================");
		        System.out.println("Lista de Alimentos");
		        System.out.println("==============================================================================");

		        for (Alimento alimento : alimentos) {
		            System.out.println(alimento.toString());
		        }

		        // Solicitação de entrada para adicionar alimentos à dieta
	            System.out.println("==============================================================================");
		        System.out.print("Ids dos Alimentos a serem adicionados à Dieta (separados por vírgula): ");
		        String idsAlimentosInput = input.nextLine();
	            System.out.println("==============================================================================");
		        
		        String[] idsAlimentos = idsAlimentosInput.split(",");
		        Set<Alimento> alimentosSelecionados = new HashSet<>();
		        Set<Porcao> porcoesSelecionadas = new HashSet<>();

		        for (String idAlimentoStr : idsAlimentos) {
		            int idAlimento = Integer.parseInt(idAlimentoStr.trim());
		            Alimento alimentoSelecionado = em.find(Alimento.class, idAlimento);

		            if (alimentoSelecionado != null && !dietaExistente.getAlimento().contains(alimentoSelecionado)) {
		                alimentosSelecionados.add(alimentoSelecionado);
			            System.out.println("==============================================================================");
		                System.out.println("Porção diaria em gramas de "+ alimentoSelecionado.getNome() +" na dieta");
			            System.out.println("==============================================================================");
			            int porcaoDoAlimento = input.nextInt();
			            Porcao porcao = new Porcao();
			            porcao.setDieta(dietaExistente);
			            porcao.setAlimento(alimentoSelecionado);
			            porcao.setPorcaoDoAlimento(porcaoDoAlimento);
			            porcoesSelecionadas.add(porcao);
			            System.out.println("==============================================================================");
		            } else {
			            System.out.println("==============================================================================");
		                System.out.println("Alimento com Id " + idAlimento + " não encontrado ou já associado à dieta.");
			            System.out.println("==============================================================================");
		            }
		        }  
		        
		        dietaExistente.adicionaAlimentos(alimentosSelecionados);
		        dietaExistente.setPorcoes(porcoesSelecionadas);
		        
	            System.out.println("\n==============================================================================");
		        System.out.println("Nova Dieta: ");
			    System.out.println(dietaExistente.toString());
			    
	            System.out.println("==============================================================================");
			    System.out.println("Confirmar Nova Dieta?\n"
			    				 + "1. Sim\n"
			    				 + "2. Nao");
	            System.out.println("==============================================================================");
		        System.out.print("Digite sua escolha: ");
			    Integer cont = input.nextInt();
	            System.out.println("==============================================================================");
			    
			    if (cont == 1) {
			        // Inicia a transação e atualiza a dieta
			        em.getTransaction().begin();
			        em.merge(dietaExistente);
			        em.getTransaction().commit();

		            System.out.println("==============================================================================");
			        System.out.println("Alimentos adicionados à Dieta!");
		            System.out.println("==============================================================================");
			    }
			    else {
		            System.out.println("==============================================================================");
				    System.out.println("Operação cancelada!");
		            System.out.println("==============================================================================");
			    }
			    
		    } else {
	            System.out.println("==============================================================================");
		        System.out.println("Nenhum alimento encontrado.");
	            System.out.println("==============================================================================");
		    }
		} else {
            System.out.println("==============================================================================");
		    System.out.println("Dieta não encontrada.");
            System.out.println("==============================================================================");
		}

		em.close();
	}
	
	public void ConsultarDieta() {
		Scanner input = new Scanner(System.in);
		
        System.out.println("\n==============================================================================");
        System.out.println("         Consultar Dieta");
        System.out.println("==============================================================================");
		
        System.out.println("==============================================================================");
		System.out.print("Id da Dieta: ");
		int id = input.nextInt();
		input.nextLine();
        System.out.println("==============================================================================");
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("calcnutri-persist");
		EntityManager em = emf.createEntityManager();
		
		Dieta DCon = em.find(Dieta.class, id);

        if (DCon != null) {
            System.out.println("==============================================================================");
            System.out.println("                             Dieta encontrada");
            System.out.println(DCon.toString());
        } else {
            System.out.println("==============================================================================");
            System.out.println("Dieta não encontrada.");
            System.out.println("==============================================================================");
        }
        
		em.close();
	}
	
	public void RemoverAlimentos() {
		Scanner input = new Scanner(System.in);
		
        System.out.println("\n==============================================================================");
        System.out.println("         Editar Dieta");
        System.out.println("==============================================================================");
		
        System.out.println("==============================================================================");
		System.out.print("Id da Dieta: ");
		int idDieta = input.nextInt();
		input.nextLine();
		System.out.println("==============================================================================");

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("calcnutri-persist");
		EntityManager em = emf.createEntityManager();

		Dieta dietaExistente = em.find(Dieta.class, idDieta);

        if (dietaExistente != null) {
		    if (!dietaExistente.getAlimento().isEmpty()) {
		    	System.out.println("==============================================================================");
		        System.out.println("Lista de Alimentos");
		        System.out.println("==============================================================================");

		        for (Alimento alimento : dietaExistente.getAlimento()) {
		            System.out.println(alimento.toString());
		        }

		        // Solicitação de entrada para adicionar alimentos à dieta
	            System.out.println("==============================================================================");
		        System.out.print("Id do Alimento a ser excluido da Dieta: ");
		        int idAlimento = input.nextInt();
	            System.out.println("==============================================================================");
		        
	            Alimento alimentoSelecionado = em.find(Alimento.class, idAlimento);

	            if (alimentoSelecionado != null) {
			        dietaExistente.removeAlimentoUnico(alimentoSelecionado);
			        
		            System.out.println("\n==============================================================================");
			        System.out.println("Nova Dieta: ");
				    System.out.println(dietaExistente.toString());
				    
		            System.out.println("==============================================================================");
				    System.out.println("Confirmar Nova Dieta?\n"
				    				 + "1. Sim\n"
				    				 + "2. Nao");
		            System.out.println("==============================================================================");
			        System.out.print("Digite sua escolha: ");
				    Integer cont = input.nextInt();
		            System.out.println("==============================================================================");
				    
				    if (cont == 1) {
				        // Inicia a transação e atualiza a dieta
				        em.getTransaction().begin();
				        em.merge(dietaExistente);
				        em.getTransaction().commit();

			            System.out.println("==============================================================================");
				        System.out.println("Alimentos adicionados à Dieta!");
			            System.out.println("==============================================================================");
				    }
				    else {
			            System.out.println("==============================================================================");
					    System.out.println("Operação cancelada!");
			            System.out.println("==============================================================================");
				    }
	            } 
	            else {
		            System.out.println("==============================================================================");
	                System.out.println("Alimento com Id " + idAlimento + " não encontrado.");
		            System.out.println("==============================================================================");
	            }
		    }
		    else {
		        System.out.println("================================");
		        System.out.println("Nenhum Alimento Cadastrado!");
		        System.out.println("================================");
		    }
        } 
        else {
            System.out.println("================================");
            System.out.println("Dieta não encontrada.");
            System.out.println("================================");
        }
	}
	
	public void DeletarDieta() {
	    Scanner input = new Scanner(System.in);
		
        System.out.println("\n==============================================================================");
        System.out.println("         Deletar Dieta");
        System.out.println("==============================================================================");

        System.out.println("==============================================================================");
	    System.out.print("Id da Dieta: ");
	    int id = input.nextInt();
        System.out.println("==============================================================================");
	    
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("calcnutri-persist");
		EntityManager em = emf.createEntityManager();
	    em.getTransaction().begin();
	    
	    Dieta DCon = em.find(Dieta.class, id);
        System.out.println("==============================================================================");
        System.out.println("Dieta: ");
	    System.out.println(DCon.toString());
	    
        System.out.println("==============================================================================");
	    System.out.println("Confirmar Delete?\n"
	    				 + "1. Sim\n"
	    				 + "2. Nao");
        System.out.println("==============================================================================");
        System.out.print("Digite sua escolha: ");
	    Integer cont = input.nextInt();
        System.out.println("==============================================================================");
	    
	    if (cont == 1) {
		    em.remove(DCon);
	        System.out.println("==============================================================================");
		    System.out.println("Dieta deletada com sucesso!");
	        System.out.println("==============================================================================");
	    }
	    else {
	        System.out.println("==============================================================================");
		    System.out.println("Delete cancelado!");
	        System.out.println("==============================================================================");
	    }
	    
	    em.getTransaction().commit();
	    
	    em.close();
	}
	
	public void CalcularMacroNutrientes() {
		Scanner input = new Scanner(System.in);
		int TotalKcal = 0;
		int TotalProt = 0;
		int TotalGord = 0;
		int TotalCarb = 0;
		
        System.out.println("\n==============================================================================");
        System.out.println("         Calculadora de MacroNutrientes");
        System.out.println("==============================================================================");
		
        System.out.println("==============================================================================");
		System.out.print("Id da Dieta: ");
		int id = input.nextInt();
		input.nextLine();
        System.out.println("==============================================================================");
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("calcnutri-persist");
		EntityManager em = emf.createEntityManager();
		
		Dieta DCon = em.find(Dieta.class, id);

        if (DCon != null) {
        	if (!DCon.getAlimento().isEmpty()) {
        		
		        for (Alimento alimento : DCon.getAlimento()) {
		            int porcaoDoAlimento = DCon.getPorcaoParaAlimento(alimento);
		            TotalKcal += alimento.getKcal() * porcaoDoAlimento;
		            TotalProt += alimento.getProt() * porcaoDoAlimento;
		            TotalGord += alimento.getGord() * porcaoDoAlimento;
		            TotalCarb += alimento.getCarb() * porcaoDoAlimento;
		        }
		        
		        System.out.println("==============================================================================");
		        System.out.println("         Total de MacroNutrientes da dieta " + DCon.getId());
	            System.out.println("==============================================================================");
	            System.out.println("Total de Caloria: " + TotalKcal + "g");
	            System.out.println("Total de Proteina: " + TotalProt + "g");
	            System.out.println("Total de Gordura: " + TotalGord + "g");
	            System.out.println("Total de Carboidrato: " + TotalCarb + "g");
	            System.out.println("==============================================================================");
		        
        	}
        } else {
            System.out.println("==============================================================================");
            System.out.println("Dieta não encontrada.");
            System.out.println("==============================================================================");
        }
        
		em.close();
	}
	
	public void MenuDieta() {
        Scanner scanner = new Scanner(System.in);
        int escolha;
		
		do {
	        System.out.println("\n==============================================================================");
            System.out.println("         Menu de Dieta");
	        System.out.println("==============================================================================");
            System.out.println("1. Adicionar Dieta");
            System.out.println("2. Adicionar Alimento a Dieta");
            System.out.println("3. Remover Alimento da Dieta");
            System.out.println("4. Consultar Dieta");
            System.out.println("5. Calcular Macronutrientes da Dieta");
            System.out.println("6. Deletar Dieta");
            System.out.println("0. Sair");
	        System.out.println("==============================================================================");
            System.out.print("Escolha uma opção: ");

            escolha = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer do scanner

            switch (escolha) {
                case 1:
                	AdicionarDieta();
                    break;
                case 2:
                	AdicionarAlimentos();
                    break;
                case 3:
                	RemoverAlimentos();
                    break;
                case 4:
                	ConsultarDieta();
                    break;
                case 5:
                    CalcularMacroNutrientes();
                    break;
                case 6:
                	DeletarDieta();
                    break;
                case 0:
                    System.out.println("\nSaindo do programa...");
                    break;
                default:
                    System.out.println("\nOpção inválida. Tente novamente.");
                    break;
            }

        } while (escolha != 5);
	}
}
