package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

@SuppressWarnings("serial")
class ListView<T> extends JLabel implements ListCellRenderer<T> {
    private static final Color HIGHLIGHT_COLOR = new Color(0, 100, 200);

    public ListView() {
            setOpaque(true);
            setIconTextGap(5);
            setFont(new Font("»ªÎÄ¿¬Ìå", Font.PLAIN, 18));
    }

    @SuppressWarnings("rawtypes")
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
           
            ListItem item = (ListItem)value;
            this.setIcon(item.getIcon());
            this.setText(item.getText());
   
            if (isSelected) {
                  setBackground(HIGHLIGHT_COLOR);
                  setForeground(Color.white);
            } else {
                  setBackground(Color.white);
                  setForeground(Color.black);
            }
        return this;
    }
}
