
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Color;

public class GUI {
	
	private JFrame frame;

	ImageHandler imageHandler = new ImageHandler();
	
	JTextArea textPane;

	JButton card1;
	JButton card2;
	JButton card3;
	JButton card4;
	JButton card5;

	ArrayList<JButton> cardButtons = new ArrayList<JButton>();
	ArrayList<Card> playerHand = new ArrayList<>();
	private JScrollPane scrollPane;
	JScrollBar vertical;

	/**
	 * Create the application.
	 */
	public GUI() {



		initialize();

		cardButtons.add(card1);
		cardButtons.add(card2);
		cardButtons.add(card3);
		cardButtons.add(card4);
		cardButtons.add(card5);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 721, 418);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 694, 376);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		card1 = new JButton("");
		card1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Gameboard.playCard(playerHand.get(0));
				card1.setEnabled(false);
			}
		});
		card1.setBounds(0, 300, 130, 65);
		panel.add(card1);

		card2 = new JButton("");
		card2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Gameboard.playCard(playerHand.get(1));
				card2.setEnabled(false);
			}
		});
		card2.setBounds(140, 300, 130, 65);
		panel.add(card2);

		card3 = new JButton();
		
		
		card3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Gameboard.playCard(playerHand.get(2));
				card3.setEnabled(false);
			}
		});
		card3.setBounds(280, 300, 130, 65);
		panel.add(card3);

		card4 = new JButton("");
		card4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Gameboard.playCard(playerHand.get(3));
				card4.setEnabled(false);
			}
		});
		card4.setBounds(420, 300, 130, 65);
		panel.add(card4);

		card5 = new JButton("");
		card5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Gameboard.playCard(playerHand.get(4));
				card5.setEnabled(false);
			}
		});
		card5.setBounds(560, 300, 130, 65);
		panel.add(card5);

		scrollPane = new JScrollPane();

		
		scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
			public void adjustmentValueChanged(AdjustmentEvent e) {  
				e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
			}
		});
		
		scrollPane.setBounds(284, 11, 400, 261);

		panel.add(scrollPane);

		textPane = new JTextArea();
		scrollPane.setViewportView(textPane);
		textPane.setEditable(false);
		
		JPanel table = new JPanel();
		table.setBackground(new Color(0, 100, 0));
		table.setBounds(0, 11, 270, 261);
		panel.add(table);
		table.setLayout(null);
		
		JPanel enemyCardPane = new JPanel();
		
		enemyCardPane.setBounds(100, 11, 59, 79);
		table.add(enemyCardPane);
		
		JPanel playerCardPane = new JPanel();
		playerCardPane.setBounds(100, 171, 59, 79);
		table.add(playerCardPane);
		
		

		frame.setVisible(true);
		
	}

	public void setCardNames(ArrayList<Card> cards) {
		for (int i = 0; i < cards.size(); i++) {
			String name = cards.get(i).toString();
			cardButtons.get(i).setText(name);
			

		}
	}

	public void setPlayerHand(Card card) {
		playerHand.add(card);
		System.out.println("PLAYER HAND IN GUI: "+playerHand);
	}

	public void setAllActive() {
		for (int i = 0; i < cardButtons.size(); i++) {
			cardButtons.get(i).setEnabled(true);
		}
	}

	public void setAllInactive() {
		for (int i = 0; i < cardButtons.size(); i++) {
			cardButtons.get(i).setEnabled(true);
		}
	}

	public void appendText(String s) {

		textPane.append(s);

	}

}
