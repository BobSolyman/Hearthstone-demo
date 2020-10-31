package engine;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Collection;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;







public class GameView extends JFrame implements ActionListener {

	public GameView () 
 	{
		ImageIcon i = new ImageIcon ("Board.PNG");
		this.setSize(i.getIconWidth(),i.getIconHeight());
		this.setLayout(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		JLabel board = new JLabel ("",i,JLabel.CENTER) ;

		
		board.setBounds(0,0,i.getIconWidth(),i.getIconHeight());
		this.add(board);
		
		
		
		this.revalidate();
		this.repaint();
		
 	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	
} // end of class 
