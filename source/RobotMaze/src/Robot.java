public class Robot {  //contiene le conoscenze ed il motore inferenziale dell'automa 
 

	
	
	public int  x ;                 
	public int  y ;   

	
	public Mindcell memoria []  = new Mindcell [10000] ;    	
    public int riempimento_memoria ; 

    public boolean dati_sensore_profondità [] = new boolean[4] ;   // dati sui muri/vuoti  adiacenti [0]-nord [1]-sud 2-ovest 3-est
	                                                               //true = vuoto , false = muro
    public int orientamento ; // 0-nord 1-sud 2-ovest 3-est
    
    
	public float decisione [] = new float[4] ;  
	
	public int direzione ; //  0-nord 1-sud 2-ovest 3-est
	
    public int rotazioni [][] = new int [4][4] ;  //matrice delle rotazioni 
	
	public boolean obiettivo_raggiunto ;   // sensore target
	
	public float alpha ;    // guadagno di potenza
	public float beta ;     // guadagno di memoria
	
//*********************************************metodo costruttore*****************************************************************    
    
public Robot( int x , int y , float alpha , float beta ) {
	
	this.x = x ;
	this.y = y ; 
	this.alpha = alpha ;
	this.beta = beta ; 
	
	for(int i=0 ; i <  10000 ; i++){            // inizializzo l'array di oggetti Cell
				  
			
			this.memoria[i] = new Mindcell() ; 
		  
		}
		
this.riempimento_memoria = 0 ; 
	


for(int i=0 ; i<  4 ; i++){            // inizializzo l'array di oggetti boolean
	  
	
	this.dati_sensore_profondità[i] = false ; 
  
}


this.orientamento = 0 ;



for(int i=0 ; i<  4 ; i++){            // inizializzo l'array di float
	  
	
	this.decisione[i] = 0 ; 
  
}


this.direzione = 0 ; 



         // inizializzo la matrice delle rotazioni
	
		this.rotazioni[0][0] =  0  ;         //1a riga 
		this.rotazioni[0][1] = 180 ; 
		this.rotazioni[0][2] =  90 ; 
		this.rotazioni[0][3] = -90 ; 
		
		this.rotazioni[1][0] = 180 ; 
		this.rotazioni[1][1] =  0  ;         //2a riga
		this.rotazioni[1][2] = -90 ; 
		this.rotazioni[1][3] =  90 ; 
		
		this.rotazioni[2][0] = -90 ;        //2a riga
		this.rotazioni[2][1] =  90 ; 
		this.rotazioni[2][2] =  0  ; 
		this.rotazioni[2][3] = 180 ; 
		
		this.rotazioni[3][0] =  90 ;        //2a riga
		this.rotazioni[3][1] = -90 ; 
		this.rotazioni[3][2] = 180 ; 
		this.rotazioni[3][3] =  0  ; 
		 


		this.obiettivo_raggiunto = false ; 

}  



		




//************************************ metodi del motore inferenziale(macchina a stati) **********************************************





public void verifica_posizione () {
	
	
	boolean trovata = false ; 
	
	for (int i=0 ; i<memoria.length ; i++ ) {     //per tutte le celle in memoria  
		
	 if( memoria[i].x == this.x  &   memoria[i].y == this.y   ) {   //se son presenti le coordinate relativa alla posizione attuale 
		 
		 trovata = true ; 
		 memoria[i].esplorazioni ++ ;    //aumenta il numero di volte che è stata esplorata 
		 
		 
System.out.printf("la cella ("+x+","+y+")  è stata esplorata "+memoria[i].esplorazioni+" volte\n") ; //print di debug
		 break; 
		 
	  } 
	 
			
	}
	
	
if ( trovata == false) {	  //se non è stata trovata alcuna cella con le coordinate passate
	memoria[riempimento_memoria ].x = this.x ;         //aggiungi cella alla memoria 
	memoria[riempimento_memoria ].y = this.y ;
	memoria[riempimento_memoria].esplorazioni ++ ;
	riempimento_memoria ++ ; 
	 
System.out.printf("la cella ("+x+","+y+") non era stata ancora esplorata, questa è prima volta") ; //print di debug

}	 
	
	
}




public void verifica_obiettivo (Target obiettivo ) {
	

if (  this.x== obiettivo.x & this.y == obiettivo.y ) {
	
	this.obiettivo_raggiunto = true ; 
	
	System.out.println("\nObiettivo raggiunto!!! La posizione era: ("+this.x+" , "+this.y+")\n"  ) ; //print di debug

}	
	
	
	
}




public void cerca_vuoto ( Maze labirinto ) {  // accede alla struttura dati maze per sapere se le celle adiacenti sono muri o vuoti
	
	
	
int x_nord = x    ;          
int y_nord = y+1  ;         // y_nord <= N

int x_sud = x     ;
int y_sud = y-1   ; 

int x_ovest = x-1 ;
int y_ovest = y   ; 

int x_est = x+1   ;
int y_est = y     ;




// acquisisco dati del sensore nord

if ( y_nord > labirinto.N ) {   //se è la parete nord del labirinto allora è un muro 
	
	dati_sensore_profondità [0] = false ; // imposto valore misurato nord 	
	
}

else {
		
dati_sensore_profondità [0] = labirinto.posizioni[x_nord-1 ][ y_nord-1].vuoto ;

}


//acquisisco dati del sensore sud

if ( y_sud < 1 ) {   //se è la parete sud del labirinto allora è un muro 
	
	dati_sensore_profondità [1] = false ; // imposto valore misurato sud	
	
}

else {
		
dati_sensore_profondità [1] = labirinto.posizioni[x_sud-1 ][ y_sud-1].vuoto ;

}



//acquisisco dati del sensore ovest

if ( x_ovest < 1 ) {   //se è la parete ovest del labirinto allora è un muro 
	
	dati_sensore_profondità [2] = false ; // imposto valore misurato ovest	
	
}

else {
		
dati_sensore_profondità [2] = labirinto.posizioni[x_ovest-1 ][ y_ovest-1].vuoto ;

}




//acquisisco dati del sensore est

if ( x_est > labirinto.N ) {   //se è la parete est del labirinto allora è un muro 
	
	dati_sensore_profondità [3] = false ; // imposto valore misurato est	
	
}

else {
		
dati_sensore_profondità [3] = labirinto.posizioni[x_est-1 ][ y_est-1].vuoto ;

}


	
	
}




public float misura_potenza (Maze labirinto  , int xp , int yp) { // misura la potenza sul punto con le cordinate passate
	
float misurata = labirinto.posizioni[xp-1][yp-1].segnale ; 	



return misurata ; 


}




public int conta_esplorazioni( int xp , int yp ) {  //controlla nella memoria dell'automa se son presenti info sulla cella
	
		int esplorazioni = 0 ;
	
	
	for (int i=0 ; i<memoria.length ; i++ ) {     //per tutte le celle in memoria  
		
	 if( memoria[i].x == xp  &   memoria[i].y == yp   ) {   //se son presenti le coordinate ricevute in input 
		 

		 esplorazioni = memoria[i].esplorazioni ;    // estraggo il numero di volte che è stata esplorata 
		 	 
		 break; 
		 
	  } 
	 		
	}
	
		

return esplorazioni ; 

}



public void calcola_decisione (Maze labirinto) {
	
float p = 0 ;   //inizializzo variabili
int e = 0 ; 
	
for(int i=0 ; i < 4  ; i++){
	
	
    if(  dati_sensore_profondità[i]==false )   {            //se  la cella è un muro la decisione vale il minimo
    
        decisione[i]= -9999F ;
    	
    	
    }
	
    else {         //se invece la cella è un vuoto
    	
    
    	switch(i) {
    	
    	case 0 :    //decisione nord 
               p = misura_potenza( labirinto , this.x ,  this.y+1) ; 
               e = conta_esplorazioni (this.x, this.y+1);   	
               decisione[i] = (alpha*p) - (beta*e) ; 	
               
               if(decisione [i] <= -9999F ) {   //gestione overflow
            	   
            	   decisione[i] = -9998F;
               }
               
               break;

    	case 1 : //decisione sud
    		   p = misura_potenza( labirinto , this.x ,  this.y-1) ; 
               e = conta_esplorazioni (this.x, this.y-1);   	
               decisione[i] = (alpha*p) - (beta*e) ;
               
               if(decisione [i] <= -9999F ) {   //gestione overflow
            	   
            	   decisione[i] = -9998F;
               }
               break;
    	
    	case 2 : //decisione ovest 
    		   p = misura_potenza( labirinto , this.x-1 ,  this.y) ; 
               e = conta_esplorazioni (this.x-1, this.y);   	
               decisione[i] = (alpha*p) - (beta*e) ; 	
              
               if(decisione [i] <= -9999F ) {   //gestione overflow
            	   
            	   decisione[i] = -9998F;
               }
               break;
    		
    	case 3 : //decisione est 
    		   p = misura_potenza( labirinto , this.x+1 ,  this.y) ; 
               e = conta_esplorazioni (this.x+1, this.y);   	
               decisione[i] = (alpha*p) - (beta*e) ;
  
               if(decisione [i] <= -9999F ) {   //gestione overflow
            	   
            	   decisione[i] = -9998F;
               }
               break;
    		
    	
    	}
    	
    }
	
}

//a questo punto l'automa sa il peso delle decisioni nella 4 direzioni  devo scegliere l'indice max per stabilire la direzione


float max = -9999F ; // valore di inizializzazione

for (int i = 0 ; i<4 ; i++ ) {
	
	
   if( decisione[i] >= max ) {
	
	   max = decisione[i] ;  //aggiorno il nuovo valore max
	   this.direzione= i ;  //l'indice diventa la direzione 
	   
	}

}


int rotazione = rotazioni [orientamento][direzione] ;      //effettuo la rotazione

System.out.println( "\nRuoto di "+rotazione+"°\n"  ) ;   //print di debug

this.orientamento = direzione ; // aggiorno l'orientamento in seguito alla rotazione


}


public void avanza () {    //aggiorna la posizione dell'automa
	
	
	System.out.println( "\nAziono gli attuatori per il movimento\n"  ) ;
	
	
	
	switch (orientamento) {
	
	
	case 0 :        //nord
		this.y++ ;
		System.out.println( "Mi muovo a NORD, la mia nuova posizione è: ("+this.x+" , "+this.y+")\n"   ) ;
		break;
		
	case 1 :        //sud
		this.y--;
		System.out.println( "Mi muovo a SUD, la mia nuova posizione è: ("+this.x+" , "+this.y+")\n"  ) ;
		break;
	
	case 2 :	   //ovest
	    this.x--  ;
	    System.out.println( "Mi muovo a OVEST, la mia nuova posizione è: ("+this.x+" , "+this.y+")\n"  ) ;
		break;
	
	case 3 :      //est 
		this.x++; 
		System.out.println( "Mi muovo a EST, la mia nuova posizione è: ("+this.x+" , "+this.y+")\n"  ) ;
		break;
	
	
	}
	
		
	
}




public void registra_evento( Maze labirinto , int k ) {  //scrive dati automa al tempo k nella struttura dati Maze
	
	            
	labirinto.datastream[k].x = this.x ;
	labirinto.datastream[k].y =this.y ;  
	labirinto.datastream[k].orientamento = this.orientamento;  
	labirinto.datastream[k].accaduto = true ;  
	
	
}




public void inizia_ricerca( Maze labirinto , Target obiettivo )  {
	

 int k = 0;   //variabile tempo discreto
	
	while (this.obiettivo_raggiunto==false) {
	
	verifica_posizione() ; 
	
	
	registra_evento ( labirinto , k);   //registro evento 
	k++;
	
	verifica_obiettivo(obiettivo) ; 
	
	      if (  this.obiettivo_raggiunto==true ) {
	    	  
	    	  break; 
	    	  
	      }
	
	cerca_vuoto(labirinto );
	
	calcola_decisione(labirinto) ; 
	registra_evento ( labirinto , k);   //registro evento 
	k++;
	
	avanza() ; 
	
	

	
		

}




}


}
