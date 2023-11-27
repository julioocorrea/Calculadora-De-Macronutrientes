package calcnutri;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Alimento {
	@Id
	private int Id;
	@Column
	private String Nome;
	@Column
	private int Kcal;
	@Column
	private int Prot;
	@Column
	private int Gord;
	@Column
	private int Carb;
	
    @OneToMany(mappedBy = "alimento", cascade = CascadeType.ALL)
    private Set<Porcao> porcoes = new HashSet<>();
	
	// mapeamento do relacionamento Muitos (Alimentos) para Muitas (Dietas)
    @ManyToMany(mappedBy = "Alimentos")
    private Set<Dieta> Dietas = new HashSet<>();
	
	public Alimento() {
		
	}
	
	public Alimento (int id, String nome, int kcal, int prot, int gord, int carb) {
		this.Id = id;
		this.Nome = nome;
		this.Kcal = kcal;
		this.Prot = prot;
		this.Gord = gord;
		this.Carb = carb;
	}
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getNome() {
		return Nome;
	}
	public void setNome(String nome) {
		Nome = nome;
	}
	public int getKcal() {
		return Kcal;
	}
	public void setKcal(int kcal) {
		Kcal = kcal;
	}
	public int getProt() {
		return Prot;
	}
	public void setProt(int prot) {
		Prot = prot;
	}
	public int getGord() {
		return Gord;
	}
	public void setGord(int gord) {
		Gord = gord;
	}
	public int getCarb() {
		return Carb;
	}
	public void setCarb(int carb) {
		Carb = carb;
	}
	
	
	public String toString() {
		return 	"==============================================================================" +
				"\nId: " + this.Id +
				"\nNome: " + this.Nome +
				"\nCaloria: " + this.Kcal +
				"\nProteina: " + this.Prot +
				"\nGordura: " + this.Gord +
				"\nCarboidrato: " + this.Carb +
				"\n==============================================================================";
	}
	
	
}
