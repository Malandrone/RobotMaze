
public class Record {  //contiene i dati sugli spostamenti dell'automa nel labirinto

	public int x ;
	public int y ;
	public int orientamento;
	public boolean accaduto ;
	
	public Record() {
		
		this.x=0;
		this.y=0;
		this.accaduto= false ; 
	}

}
