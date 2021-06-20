import java.lang.Math ;
public class Maze{  //contiene i dati del labirinto tra cui un array di Cell
	
	
	
	
	
	public int N  ;   //numero celle totali del labirinto 
	public int V ;   // numero celle vuote 
	
	public Cell posizioni[] []  = new Cell [100][100] ;  	// dichiaro una matrice di 100*100 celle   
 
	public Record datastream [] = new Record [108000] ;     // istanzio un oggetto per lo streaming fino a 3 ore
	

	
	
	

	public Maze ( ) {  //costruttore 
	 	
		this.N = 100 ;   // la dimensione è inizializzata a 100
		this.V = 0 ;     // le celle vuote sono inizializzate a 0 
		
				
		for(int i=0 ; i<  N-1 ; i++){            // inizializzo l'array di oggetti Cell
		
		  for ( int j=0 ; j< N-1 ; j++) {
			
			this.posizioni[i][j]= new Cell() ; 
		  }
		}
				
	
		
		  for ( int k=0 ; k< 10000 ; k++) {
				
				this.datastream[k] = new Record() ;
			  }
	
	
	
	}  
	
	
	
	
	public void set_global( int dimensione , int celle_vuote ) {   //imposta le info globali sul labirinto
	 	
		this.N = dimensione ; 
		this.V = celle_vuote ; 
								
				
	} 



	







public void calcola_potenza ( Target sorgente , int xp , int yp ){   //calcola la potenza irradiata in un singolo punto xp, yp

	
	int x0 = sorgente.x ;
	int y0 = sorgente.y ;
	
    
	
	float d = calcola_distanza ( sorgente , xp , yp  ) ; 
	
	
	if (d== 0 ) {
		
		d= 0.9999F; 
		
	}
	
	
	
	
	
	if ( x0 == xp ) {            // sorgente e punto campo sulla stessa ascissa (m infinito)
							
	
	if ( y0 > yp  ) {           // sorgente "sopra" punto campo
		
		
				
		float L = 0 ;     //inizializzo coefficiente di attenuazione
		
		for ( int y = yp ; y <= y0 ; y++  ) {
																											
      
			 if (this.posizioni[x0-1 ][ y-1].vuoto == true ) {    //se nella cella tra sorgente e punto c'è un vuoto
				 
				 L= L + 1 ;                                      //l'attenuazione vale 1
			
				 
			 }
		
			 else {                                           //altrimenti vale 3 ( attenuazione da muro)
				 
				 L= L+ 3 ; 
				 
			 }
			 
			 				
		}
					
	// a questo punto ho calcolato l'attenuazione complessiva nel caso  ( x0=xp , y0 > yp )
	
	
		
   
	
		  
    float P = (float) (10F*Math.log(   (sorgente.segnale)/ (Math.pow(d ,2 ))   ) -    Math.log(L));   //  Potenza ricevuta in dB 
		
		
	if ( P <  0 ) {
		
		P = 0 ; 
	}
		
	 		
	
	this.posizioni[xp-1][yp-1].segnale = P ;      //scrivo il valore nella struttura dati Maze 
		
		
		
	
		
	}
	
	else {                    //sorgente "sotto" punto campo
		
		
		

		float L = 0 ;     //inizializzo coefficiente di attenuazione
		
		for ( int y = y0 ; y <= yp ; y++  ) {
																											
      
			 if (this.posizioni[x0-1 ][ y-1].vuoto == true ) {
				 
				 L= L + 1 ; 
			
				 
			 }
		
			 else {
				 
				 L= L+ 3 ; 
				 
			 }
			 
			 				
		}
					
	// a questo punto ho calcolato l'attenuazione complessiva nel caso  ( x0=xp , y0 < yp )
	
		
		
		
		
		 float P = (float) (10F*Math.log(   (sorgente.segnale)/ (Math.pow(d ,2 ))   ) -    Math.log(L));   //  Potenza ricevuta in dB 
		
	if ( P <  0 ) {
		
		P = 0 ; 
	}
		
	 		
	
	this.posizioni[xp-1][yp-1].segnale = P ;      //scrivo il valore nella struttura dati Maze 
		
		
		
		
		
		
		
	}
	
		
	
	}	
	
	else {               //         sorgente e punto campo non allineati 

		float m = (float) (y0 - yp ) / (x0- xp) ;  //coefficiente angolare
			
		
			
		
		
		
		
		
		if ( x0 > xp ) {  
			
		
			
			
			float L = 0 ;     //inizializzo coefficiente di attenuazione
			
			for ( int x = xp ; x <= x0 ; x++  ) {
				
																								
					   
	      
				int y = (int) Math.round (  m*(x - xp) )   + yp ;    // ordinata troncata  
				
				
				
				 if (this.posizioni[x-1 ][ y-1].vuoto == true ) {
					 
					 L= L + 1 ; 
				
					 
				 }
			
				 else {
					 
					 L= L+ 3 ; 
					 
				 }
				 
				 				
			}
						
		// a questo punto ho calcolato l'attenuazione complessiva nel caso  x0 > xp
		
				
			
		
			
	
	 float P = (float) (10F*Math.log(   (sorgente.segnale)/ (Math.pow(d ,2 ))   ) -    Math.log(L));   //  Potenza ricevuta in dB 
			
		if ( P <  0 ) {
			
			P = 0 ; 
		}
			
		 		
		
		this.posizioni[xp-1][yp-1].segnale = P ;      //scrivo il valore nella struttura dati Maze 
		
		}
		
		
		
		if (x0 < xp ) {   
			

			
			
			float L = 0 ;     //inizializzo coefficiente di attenuazione
			
			for ( int x = x0 ; x <= xp ; x++  ) {
				
																								
				int y = (int) Math.round(  m*(x -x0) )  + y0  ;	   // ordinata troncata
	      
				
			      
				
				 if (this.posizioni[x-1 ][ y-1].vuoto == true ) {
					 
					 L= L + 1 ; 
				
					 
				 }
			
				 else {
					 
					 L= L+ 3 ; 
					 
				 }
				 
				 				
			}
						
		// a questo punto ho calcolato l'attenuazione complessiva nel caso x0 < xp 
		
		
			
		
	 float P = (float) (10F*Math.log(   (sorgente.segnale)/ (Math.pow(d ,2 ))   ) -    Math.log(L));   //  Potenza ricevuta in dB 
			
		if ( P <  0 ) {
			
			P = 0 ; 
		}
			
		 		
		
		this.posizioni[xp-1][yp-1].segnale = P ;      //scrivo il valore nella struttura dati Maze 
		
			
			
				
		}
		
		
		
	}
	

	
}	








public float calcola_distanza ( Target sorgente , int xp , int yp ){   //calcola la distanza fra la sorgente ed un singolo punto xp, yp

	
	int x0 = sorgente.x ;
	int y0 = sorgente.y ;

	
	double d = Math.sqrt(   Math.pow(  ( (x0-xp)   ) ,2  )  +   Math.pow( ( (y0-yp)  ) , 2  )         );
	
	float distanza = (float) Math.ceil(d) ; 
	
	
return distanza ;




}







	

}
