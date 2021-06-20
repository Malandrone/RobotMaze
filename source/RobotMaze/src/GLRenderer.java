import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;


public class GLRenderer implements GLEventListener {

	
	
	public int k=0 ;     //istante iniziale nel tempo discreto 
	
	public Maze labirinto ;
	public Target obiettivo ; 
	
	

	
	
    @Override
    public void init(GLAutoDrawable drawable) { }

    @Override
    public void dispose(GLAutoDrawable drawable) { }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) { }


     
    public float convert (  int c , int N  ) {  //conversione coordinate nello standard della grafica
    	
    	float cF = (float) c ;
    	float NF = (float) N ;
    	
    	float v = (cF / ( NF/2F )) -1F - (1/NF)   ;
    	    			
    	return  v ; 
    	
    }
    
    
    @Override
    public void display(GLAutoDrawable drawable ) {     //visualizza le figure
        
    	
    	
    	GL2 gl = drawable.getGL().getGL2();

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

        gl.glBegin(GL2.GL_QUADS);
        
        
        //parametri labirinto  
        int N =  labirinto.N;
        float delta = (float) 1/N ; //passo di quantizzazione
        float mezzo = (float) N/ 2F ; 
        
        
        // disegno i vuoti
        gl.glColor3f(1 , 1, 1);  // colore standard RGB bianco
        for (int i = -N/2    ; i < N/2  ; i++) {                                 //-5:+5  nel caso N=10
            for (int j = -N/2 ; j <  N/2 ; j++) {
                 
                    gl.glVertex2d(i / mezzo , j / mezzo);                     //mezzo = 5.0 nel caso N=10
                    gl.glVertex2d(    (i / mezzo) + (2F*delta)    , j / mezzo);                //2F*delta= 0.2 , è il lato della cella nel caso N=10 
                    gl.glVertex2d(  (i / mezzo) + (2F*delta)   , (j / mezzo) + (2F*delta) );
                    gl.glVertex2d(i / mezzo, (j / mezzo) + (2F*delta)  );
                
            }
        }
        

        // disegno i muri
        gl.glColor3f(0 , 0, 0);  // colore standard RGB nero
        for (int i = -N/2 ; i < N/2  ; i++) {
            for (int j = -N/2 ; j < N/2  ; j++) {
                
            	if (  this.labirinto.posizioni[i+(N/2)][j+(N/2)].vuoto == false  ){ 
                
            	    gl.glVertex2d(i/mezzo  , j/mezzo );
                    gl.glVertex2d((i/mezzo)+ (2F*delta) , j/mezzo  );                                   //0.2 è il lato della cella nel caso N=10
                    gl.glVertex2d((i/mezzo)+(2F*delta)  , (j/mezzo)+(2F*delta)  );
                    gl.glVertex2d(i/mezzo  , (j/mezzo)+(2F*delta));
            	
               }
             }
         }

        
        
        
      
        
        
        // disegno l'obiettivo 
        int xo_record = obiettivo.x ;
        int yo_record = obiettivo.y ; 
      
        
        float xo  =  convert ( xo_record , N )      ;           //(xo_record/5.0F) -1.1F  ;   nel caso N=10
        float yo  =  convert ( yo_record , N  )       ;          //(yo_record/5.0F) -1.1F ;   nel caso N=10
        
        gl.glColor3f(1, 0, 0); //rosso
         
        
        gl.glVertex2d( (xo-delta) ,  yo);                      //+/- 0.1 nel caso N=10
        gl.glVertex2d(xo, (yo-delta));
        gl.glVertex2d((xo+delta) ,yo );
        gl.glVertex2d(xo , (yo+delta)); 
        
        
      
        
        
        
     // disegno l'automa 
        
        int xa_record = labirinto.datastream[k].x  ;
        int ya_record = labirinto.datastream[k].y  ; 
        
        float xa  = convert( xa_record , N  );          //(xa_record/5.0F) -1.1F  ; nel caso N=10
        float ya  = convert (ya_record , N  );         //(ya_record/5.0F) -1.1F ;  nel caso N=10
        
        gl.glColor3f(1, 1, 0); //giallo 
         
        
     
        
        int orientamento = labirinto.datastream[k].orientamento;
        
        switch(orientamento) {                             //+/- 0.1 nel caso N=10
        
        
        case 0 :
        	         gl.glVertex2d(xa, (ya+delta));
                     gl.glVertex2d((xa+delta) ,(ya-delta) );
                     gl.glVertex2d(xa , (ya -delta));
                     gl.glVertex2d((xa-delta) , (ya -delta));
                     break;	
        
        case 1 :
        	         gl.glVertex2d((xa-delta), (ya+delta));
        	         gl.glVertex2d(xa , (ya+delta) );
                     gl.glVertex2d((xa+delta) ,(ya+delta) );
                     gl.glVertex2d(xa, (ya -delta));
        	         break;	
        case 2 :
        			 gl.glVertex2d((xa+delta), (ya+delta));
        			 gl.glVertex2d((xa+delta) , ya );
        	         gl.glVertex2d((xa+delta) ,(ya-delta) );
        	         gl.glVertex2d((xa-delta), ya );   
        			 break;	
        			
        case  3 : 
        			 gl.glVertex2d((xa-delta), (ya+delta));
        	         gl.glVertex2d((xa+delta) ,ya );
        	         gl.glVertex2d((xa-delta), (ya -delta));
        	         gl.glVertex2d((xa-delta) , ya );
        			 break;	
        
        
        }
        
        
           if(xo==xa & yo==ya) {    //l'obiettivo diventa verde quando l'automa lo raggiunge
        	   
        	   gl.glColor3f(0, 1, 0); //verde
               
               
               gl.glVertex2d( (xo-delta) ,  yo);
               gl.glVertex2d(xo, (yo-delta));
               gl.glVertex2d((xo+delta) ,yo );
               gl.glVertex2d(xo , (yo+delta)); 
        	   
        	   return;
        	   
           }
        
       
           k++;
           
           gl.glEnd();

        
     
       
      
        
          
          
        
       
        try {
        	Thread.sleep(100);    //pausa in millisecondi (default 1000)
        } catch (InterruptedException e) {
        	
        	

        }
   
    
    
    }


}

