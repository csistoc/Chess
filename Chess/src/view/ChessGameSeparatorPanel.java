package view;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class ChessGameSeparatorPanel extends JPanel {
	
	private static final long serialVersionUID = 4584765638472939490L;

	public ChessGameSeparatorPanel() {
		super();
        add(new JSeparator(SwingConstants.HORIZONTAL));
	}
}
