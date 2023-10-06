import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Chat extends JFrame {
    private static final int WINDOW_HEIGHT = 555;
    private static final int WINDOW_WIDTH = 507;
    private JTextField loginTextField = new JTextField();
    private JPasswordField passwdTextField = new JPasswordField();
    private JTextField socketTextField = new JTextField();
    private JTextArea chatTextField = new JTextArea();
    private JTextArea typedTextField = new JTextArea(2, 0);

    private JLabel loginLabel = new JLabel("Введите логин: ");
    private JLabel passwdLabel = new JLabel("Введите пароль: ");
    private JLabel socketLabel = new JLabel("Введите IP-адреса сервера: ");
    private JLabel connectionStatus = new JLabel("Не подключен к серверу", SwingConstants.CENTER);
    private JButton sendAuthorizationButton = new JButton("Подключиться");
    private JButton sendMsgButton = new JButton("Отправить");
    private Authorization authorization;
    private Log log;
    private Date date;
    private SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM HH:mm:ss", Locale.UK);

    public Chat() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("Super Chat");

        authorization = new Authorization(this);
        log = new Log();
        date = new Date();

        JPanel jPanelAuthorization = new JPanel(new GridLayout(4, 2));
        jPanelAuthorization.add(loginLabel);
        jPanelAuthorization.add(loginTextField);
        jPanelAuthorization.add(passwdLabel);
        jPanelAuthorization.add(passwdTextField);
        jPanelAuthorization.add(socketLabel);
        jPanelAuthorization.add(socketTextField);
        jPanelAuthorization.add(sendAuthorizationButton);
        jPanelAuthorization.add(connectionStatus);

        JPanel jPanelTypedTextField = new JPanel(new GridLayout(2, 1));
        typedTextField.setLineWrap(true);
        typedTextField.setWrapStyleWord(true);
        JPanel typedTextPanel = new JPanel(new GridLayout(1, 0));
        typedTextPanel.add(new JScrollPane(typedTextField));

        jPanelTypedTextField.add(typedTextPanel);
        jPanelTypedTextField.add(sendMsgButton);

        add(jPanelAuthorization, BorderLayout.NORTH);

        add(new JScrollPane(chatTextField), BorderLayout.CENTER);
        add(jPanelTypedTextField, BorderLayout.SOUTH);

        sendAuthorizationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (connectionStatus.getText().equals("Не подключен к серверу") && checkDataAuthorization()) {
                    connectionStatus.setText("Подключение активно");
                    connectionStatus.setBackground(Color.GREEN);
                    connectionStatus.setOpaque(true);
                    sendAuthorizationButton.setText("Отключиться");
                    chatTextField.setText(log.loadLog());
                } else if (connectionStatus.getText().equals("Подключение активно")) {
                    connectionStatus.setText("Не подключен к серверу");
                    connectionStatus.setOpaque(false);
                    sendAuthorizationButton.setText("Подключиться");
                } else {
                    authorization.setVisible(true);
                }
            }
        });

        sendMsgButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMsg();
            }
        });

        typedTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMsg();
                    e.consume();
                    typedTextField.setText(null);
                }
            }
        });
    }

    private boolean checkDataAuthorization() {
        if (loginTextField.getText().isEmpty() || passwdTextField.getPassword().length == 0
                || socketTextField.getText().isEmpty()) return false;
        else return true;
    }

    private void sendMsg() {
        if (!connectionStatus.getText().equals("Подключение активно") || !checkDataAuthorization()) {
            authorization.setVisible(true);
            typedTextField.setText(null);
            return;
        }
        String msg = formatForDateNow.format(date) + " " + loginTextField.getText() + ": " + typedTextField.getText();
        log.logging(msg);
        chatTextField.setText(log.loadLog());
        typedTextField.setText(null);
    }
}
