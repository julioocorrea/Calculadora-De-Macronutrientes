package calcnutri;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Dieta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;
	
    @OneToMany(mappedBy = "dieta", cascade = CascadeType.ALL)
    private Set<Porcao> porcoes = new HashSet<>();

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "dieta_alimentos", 
        joinColumns = { @JoinColumn(name = "dieta_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "alimento_id") }
    )
    Set<Alimento> Alimentos = new HashSet<>();
    
    public Dieta () {
    	
    }
    
    
    // adiciona alimentos ao conjunto
    public void adicionaAlimentos(Set<Alimento> alimentosSelecionados) {
        this.Alimentos.addAll(alimentosSelecionados);
    }
    
    // adiciona um único alimento ao conjunto
    public void adicionaAlimentoUnico(Alimento alimento) {
        this.Alimentos.add(alimento);
    }
    
    public void removeAlimentoUnico(Alimento alimento) {
        this.Alimentos.remove(alimento);
    }

    // acessa a lista de funcionários
    public Set<Alimento> getAlimento(){
    	return Alimentos;
    }

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}
	
	public Set<Porcao> getPorcoes() {
		return porcoes;
	}


	public void setPorcoes(Set<Porcao> porcoes) {
		this.porcoes = porcoes;
	}
	
    public int getPorcaoParaAlimento(Alimento alimento) {
        for (Porcao porcao : porcoes) {
            if (porcao.getAlimento().equals(alimento)) {
                return porcao.getPorcaoDoAlimento();
            }
        }
        return 0;
    }


	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("==============================================================================\n")
	      .append("Id da Dieta: ").append(this.Id).append("\n")
	      .append("==============================================================================\n")
	      .append("Alimentos:\n");

	    for (Alimento alimento : Alimentos) {
	        sb.append("==============================================================================\n")
	          .append("Id: ").append(alimento.getId())
	          .append(", Nome: ").append(alimento.getNome())
	          .append(", Caloria: ").append(alimento.getKcal())
	          .append(", Proteina: ").append(alimento.getProt())
	          .append(", Gordura: ").append(alimento.getGord())
	          .append(", Carboidrato: ").append(alimento.getCarb());

	        // Verifica se há porções associadas a esse alimento na dieta
	        for (Porcao porcao : porcoes) {
	            if (porcao.getAlimento().equals(alimento)) {
	                sb.append("\n==============================================================================\n")
	                  .append("Porção em gramas: ").append(porcao.getPorcaoDoAlimento()).append("\n");
	            }
	        }

	        sb.append("==============================================================================\n");
	    }

	    return sb.toString();
	}

    
}
