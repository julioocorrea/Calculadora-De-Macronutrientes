package calcnutri;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Porcao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Dieta dieta;

    @ManyToOne
    private Alimento alimento;

    private int porcaoDoAlimento;

	public int getPorcaoDoAlimento() {
		return porcaoDoAlimento;
	}

	public void setPorcaoDoAlimento(int porcaoDoAlimento) {
		this.porcaoDoAlimento = porcaoDoAlimento;
	}

	public Dieta getDieta() {
		return dieta;
	}

	public void setDieta(Dieta dieta) {
		this.dieta = dieta;
	}

	public Alimento getAlimento() {
		return alimento;
	}

	public void setAlimento(Alimento alimento) {
		this.alimento = alimento;
	} 
	
    
}
