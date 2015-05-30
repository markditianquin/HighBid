package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Portal for bidder registration.
 * 
 * @author Robbie Nichols
 * @version 5/29/15
 */
public class RegistrationPortal extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private static final int btnX = 150;
	private static final int btnY = 85;

	private RegisterChooser _chooser;
	
	private JLabel _label;
	private JButton _registerBtn;
	private JButton _editBtn;
	private JButton _backBtn;

	public RegistrationPortal(RegisterChooser theChooser) {
		setSize(MainFrame.WIDTH, MainFrame.HEIGHT);
		_chooser = theChooser;
		setLayout(null);
		setComponents();
		addComponents();
	}

	/**
	 * Setting for each component in this panel.
	 */
	private void setComponents() {
		_label = new JLabel("Bidder Registration");
		_label.setFont(new Font("Tahoma", 0, 70));
		_label.setBounds((int) ((MainFrame.WIDTH / 2) - 250), 200, 700, 100);
		_label.setForeground(Color.BLUE);

		_registerBtn = new JButton("Add");
		_registerBtn.setLocation((MainFrame.WIDTH / 3), 400);
		_registerBtn.setSize(btnX, btnY);
		_registerBtn.setFont(MainFrame.BUTTON_FONT);
		_registerBtn.addActionListener(this);

		_editBtn = new JButton("<html>Edit / Remove</html>");
		_editBtn.setLocation((MainFrame.WIDTH / 3) * 2, 400);
		_editBtn.setSize(btnX, btnY);
		_editBtn.setFont(MainFrame.BUTTON_FONT);
		_editBtn.addActionListener(this);

		_backBtn = new JButton("Back");
		_backBtn.setLocation((MainFrame.WIDTH / 2) - 550, 620);
		_backBtn.setSize(btnX, btnY);
		_backBtn.setFont(MainFrame.BUTTON_FONT);
		_backBtn.addActionListener(this);
	}

	/**
	 * Add the component to the panel.
	 */
	private void addComponents() {
		add(_label);
		add(_registerBtn);
		add(_editBtn);
		add(_backBtn);
	}

	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton) e.getSource();
		if (src == _registerBtn) {
			MainFrame.CLAYOUT.show(MainFrame.CONTAINER, "BidderReg");
		} else if (src == _editBtn) {
			_chooser.createList();;
			MainFrame.CLAYOUT.show(MainFrame.CONTAINER, "BidderChooser");
		} else {
			MainFrame.CLAYOUT.show(MainFrame.CONTAINER, "HomeScreen");
		}
	}
}
