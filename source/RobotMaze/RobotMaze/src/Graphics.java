import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.FPSAnimator;

public class Graphics extends JFrame {

	
	
	GLWindow window;
	FPSAnimator animator;
	GLRenderer renderer;

	public Graphics(  Maze labirinto , Target obiettivo ) {
	
		
		setTitle("Control");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(1000, 600, 280, 120);  //posizione e dimensione controlli
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 6)); //posizione scacchiera
		setContentPane(contentPane);
		
		
		
		

		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				animator.start();
			}
		});
		contentPane.add(btnStart);

		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				animator.stop();
			}
		});
		contentPane.add(btnStop);

	   
		
		
		window = GLWindow.create(new GLCapabilities(GLProfile.getDefault()));
        window.setTitle("RobotMaze"); //titolo finestra scacchiera
        window.setSize(800, 600);  //dimensione finestra scacchiera   
        renderer = new GLRenderer(  );
        renderer.labirinto = labirinto;                                        //appena istanzio l'oggetto renderer gli passo il labirinto
        renderer.obiettivo = obiettivo ;                                      // e l'obiettivo
        
        window.addGLEventListener(renderer);
        animator = new FPSAnimator(window, 60, true);
        window.setVisible(true); // rendo la scacchiera visibile 		
		
		
		
	}

	
                                     

    public void btnStartActionPerformed(java.awt.event.ActionEvent evt) {                                         
        animator.start();
    }                                        

    public void btnStopActionPerformed(java.awt.event.ActionEvent evt) {                                        
        animator.stop();
    }                                       


	

}
