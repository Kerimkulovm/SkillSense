import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class LoginWin extends JPanel {




    public static CardLayout card;
    public static DatabaseQueries databaseQueries = new DatabaseQueries();
    public static objectUser user = new objectUser();




    public LoginWin(){

        try {
            BufferedImage logo_image = ImageIO.read(Objects.requireNonNull(getClass().getResource("resources/logo/Logo2.png")));
            BufferedImage bg_image = ImageIO.read(Objects.requireNonNull(getClass().getResource("resources/logo/bg.jpg")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }



        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel titleEng = new JLabel("<html><big>Добро пожаловать!</big><br /></html>");
        titleEng.setBounds(120, 30, 300, 150);
        titleEng.setFont(Font.getFont("Lena"));
        titleEng.setForeground(Color.BLACK);
        this.add(titleEng);


////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel Logfield_panel = new JPanel(new GridLayout(1,2));
        Logfield_panel.setVisible(true);

        Logfield_panel.setBounds(15,30,300,50);
        this.add(Logfield_panel);

        JLabel login_label = new JLabel("Логин: ");
        login_label.setFont(Font.getFont("Lena"));
        Logfield_panel.add(login_label, BorderLayout.CENTER);


        JTextField login = new JTextField(10);
        login.setBackground(Color.WHITE);
        login.setFont(Font.getFont("Lena"));
        Logfield_panel.add(login);



        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        JPanel Pswfield_panel = new JPanel(new GridLayout(1,2));
        Pswfield_panel.setVisible(true);

        Pswfield_panel.setBounds(15,800,300,50);
        this.add(Pswfield_panel);

        JLabel psw_label = new JLabel("Пароль: ");
        psw_label.setFont(Font.getFont("Lena"));
        Pswfield_panel.add(psw_label);



        JPasswordField psw = new JPasswordField(10);
        //psw.setBackground(Color.WHITE);
        psw.setFont(Font.getFont("Lena"));
        Pswfield_panel.add(psw);


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        JPanel buttons_panel = new JPanel(new GridLayout(1,2,5,0));
        buttons_panel.setBackground(Color.WHITE);
        buttons_panel.setBounds(20,250,100,30);
        this.add(buttons_panel);



        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JButton enter_button = new JButton("Войти");
        enter_button.setForeground(Color.BLACK);
        enter_button.setBackground(Color.WHITE);
        enter_button.setFont(Font.getFont("Lena"));
        enter_button.setBorder(new RoundedBorder(10));
        buttons_panel.add(enter_button);
        enter_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (login.getText()=="" || login.getText().equals("") || psw.getText()=="" || psw.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Заполните логин или пароль!");
                }else
                if (loginChecking(login, psw)) {

                    MineOperations.launchMineOperationsTraining();


                    Container frame = enter_button.getParent();
                    do
                        frame = frame.getParent();
                    while (!(frame instanceof JFrame));
                    ((JFrame) frame).dispose();

                }else {
                    JOptionPane.showMessageDialog(null, "Неправильный логин или пароль!");
                }


            }
        });

        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                    psw.requestFocus();

            }
        });

        psw.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enter_button.doClick();
            }
        });



        JButton exit_button = new JButton("Закрыть");
        exit_button.setBackground(Color.WHITE);
        exit_button.setForeground(Color.BLACK);
        exit_button.setBorder(new RoundedBorder(10));
        exit_button.setFont(Font.getFont("Lena"));
        buttons_panel.add(exit_button);
        exit_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    Container frame = exit_button.getParent();
                    do
                        frame = frame.getParent();
                    while (!(frame instanceof JFrame));
                    ((JFrame) frame).dispose();
            }
        });
    }


    private boolean loginChecking(JTextField l, JPasswordField p) {

        user = databaseQueries.checkLoginPswDB(l.getText(), p.getText());

        if (user != null)   return true;
        else return false;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////




    private static class RoundedBorder implements Border {

        private int radius;


        RoundedBorder(int radius) {
            this.radius = radius;
        }


        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
        }


        public boolean isBorderOpaque() {
            return true;
        }


        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width-1, height-1, radius, radius);
        }
    }



}
