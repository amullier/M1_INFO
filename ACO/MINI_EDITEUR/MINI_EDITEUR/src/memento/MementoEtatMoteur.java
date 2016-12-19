package memento;

import classes.Selection;

public class MementoEtatMoteur implements Memento{
	private String contenu;
	private Selection selection;
	
	public MementoEtatMoteur(String contenu, Selection selection) {
		super();
		this.contenu = contenu;
		this.selection = selection;
	}

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		//TODO : Ajout le .clone()
		this.contenu = contenu;
	}

	public Selection getSelection() {
		return selection;
	}

	public void setSelection(Selection selection) {
		//TODO : Ajout le .clone()
		this.selection = selection;
	}
}
