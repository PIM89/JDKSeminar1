import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Authorization extends JFrame {

    private static final int WINDOW_HEIGHT = 150;
    private static final int WINDOW_WIDTH = 300;

    private JLabel info = new JLabel("Соединение не установлено!", SwingConstants.CENTER);
    private JButton buttonOk = new JButton("Ok");

    Authorization(Chat chat) {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(chat);
        setTitle("Проверка данных");
        JPanel jPanel = new JPanel(new GridLayout(2, 1));
        jPanel.add(info);

        JPanel jPanelButton = new JPanel();
        jPanelButton.add(buttonOk);

        jPanel.add(jPanelButton);
        add(jPanel);

        buttonOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }
}
