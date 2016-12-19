package commandes;

import memento.Memento;

public interface CommandeEnreg extends Commande{
	public void execute();
	public Memento getMemento();
	public void setMemento(Memento m);
}
