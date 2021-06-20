import java.awt.EventQueue;
import java.io.* ; 


public class Main {

	public static void main(String[] args) throws IOException {
	
		Maze labirinto = new Maze( ) ;  //creo un oggetto Maze 100x100
		  
		
//********************************************* Caricamento dati labirinto *****************************************************************************
		
		FileReader f ;
		f= new FileReader(  "dati_labirinto.txt"   );// nel file non ci devono essere "caratteri spazio"
		BufferedReader b;
		b= new BufferedReader(f);
		
		String riga ;
	
	
		//carico i dati del labirinto nella struttura dati Maze
		 
		 riga=b.readLine();   //riga++
		 int dimensione = Integer.parseInt(riga);     // carico dimensione come intero
		 
		 
	     riga = b.readLine() ; 
	     int celle_vuote = Integer.parseInt(riga);     // carico numero celle vuote come intero
	     

	    labirinto.set_global( dimensione, celle_vuote) ; 	     //imposto info globali labirinto
	                                                     
	    
    //imposto info su ogni singola cella (muro o vuoto)
	    	    
		for(int i=0 ; i< celle_vuote  ; i++){            
						  
				
				  riga = b.readLine() ; 
				  int x =  Integer.parseInt(riga);
				  riga = b.readLine() ; 
				  int y =  Integer.parseInt(riga);
				  
				  
				labirinto.posizioni[x-1][y-1].vuoto = true ;       //imposto la cella vuota
			  								
				
			  
			}
	     
	   	
		 
		 b.close(); //fine lettura dati labirinto
		
		 
		 
		 
		 
		
 		 		 
		 
			
//********************************************* Caricamento dati automa *****************************************************************************
				
				
			f= new FileReader(  "dati_automa.txt"   );// nel file non ci devono essere "caratteri spazio"
			b= new BufferedReader(f);
			
			riga=b.readLine();   //riga++
			int x = Integer.parseInt(riga);     // carico coordinata x come intero
			riga=b.readLine();   //riga++
			int y = Integer.parseInt(riga);     // carico coordinata y come intero	
			
			
			 
										 
			 riga=b.readLine();   //riga++
			 float alpha = Float.parseFloat(riga);     // carico parametro alpha come float	
			 
			 riga=b.readLine();   //riga++
			 float beta = Float.parseFloat(riga);     // carico parametro  beta come float	
			 
			 
			//carico i dati dell'automa nella struttura dati Robot	
			 
			Robot automa = new Robot( x,y , alpha , beta  );
			 
			b.close(); //fine lettura  dati automa 
		 
		 
		 
			
			
//********************************************* Caricamento dati obiettivo *****************************************************************************
						
			
			 
		 		 
			 
				//Leggo dati obiettivo da file
						
					f= new FileReader(  "dati_obiettivo.txt"   );// nel file non ci devono essere "caratteri spazio"
					b= new BufferedReader(f);
					
					riga=b.readLine();   //riga++
					x = Integer.parseInt(riga);     // carico coordinata x come intero
					riga=b.readLine();   //riga++
					y = Integer.parseInt(riga);     // carico coordinata y come intero	
					riga=b.readLine();   //riga++
					float p = Float.parseFloat(riga);     // carico potenza segnale come float	
					
					 //carico i dati dell'obiettivo nella struttura dati Target
					
								
					Target obiettivo = new Target (x, y , p ) ; //Creo oggetto tipo Target
					
					//carico info obiettivo sulla struttura dati Maze
				
					
					
					labirinto.posizioni[x-1][y-1].target = true ;  
					labirinto.posizioni[x-1][y-1].segnale = p ;   
					
					b.close(); //fine lettura  dati automa 
				 
			
			
			
//*************************************Calcolo della potenza*******************************************************************   

					System.out.println("\nDistribuzione della potenza irradiata:\n ");

			
					for ( int j= labirinto.N-1 ; j >= 0   ; j--) {


						for(int i=0 ; i < labirinto.N    ; i++){           
							
							  
							labirinto.calcola_potenza( obiettivo , i+1 ,  j+1    ) ; 
							int potenza = (int) labirinto.posizioni[i][j].segnale ;
							
							
							
							System.out.printf("["+potenza+"]");

							
							
							
							
								
								if ( i == labirinto.N -1  ) {
									
									System.out.printf("\n") ;
									
								}
							  
									
								
							  }
							}	
			
			
			
					System.out.printf("\n") ;
					
			
			
					
			
			
			
			
		
//*********************************************Inizio simulazione************************************************************************+


System.out.println("\nInizio ricerca\n ");

automa.inizia_ricerca(labirinto, obiettivo);

//a questo punto i dati sono disponibili su datastream 



EventQueue.invokeLater(new Runnable() {
	public void run() {
		try {
			Graphics frame = new Graphics(labirinto,obiettivo);
			frame.setVisible(true);
		
			
				
		
		
		
		
		
		} catch (Exception e) {
		
			
		}
	}
});

	}


}
