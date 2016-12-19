package classes;

public interface Moteur
{	
	public Buffer getBuffer();
	public String getBufferContenu();
	public String getSelection();
	public String getClipboard();

	public void inserer(String substring);
	public void selectionner(int start, int stop);
	public void copier();
	public void couper();
	public void coller();
}
