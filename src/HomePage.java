/*
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HomePage{

    public static final int FRAME_WIDTH = 900;
    public static final int FRAME_HEIGHT = 700;

    private static void HomePanel(){
        JFrame homePage_frame = new JFrame();
        homePage_frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        homePage_frame.setTitle("Home Page");
        homePage_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        HomePagePanelDraft home_panel = new HomePagePanelDraft();
        homePage_frame.add(home_panel);
        homePage_frame.setVisible(true);
    }
}

class HomePagePanelDraft extends JPanel implements ActionListener{

    private BufferedImage logo_image;
    private BufferedImage safe_image;

    private JLabel title_label;
    private JLabel mineOperations_label;
    private JLabel gorniy_label;

    private  JPanel buttons_panel;

    private  JButton enterData_button;
    private  JButton viewFiles_button;
    private  JButton viewSchedule_button;
    private  JButton reports_button;
    private  JButton addList_button;
    private  JButton exit_button;


    public HomePagePanelDraft(){

        JFrame topFrame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);

        try{
            logo_image = ImageIO.read(new File("textures/logo/kumtor_logo.jpg"));
            safe_image = ImageIO.read(new File("textures/logo/safe_logo.jpg"));
        } catch (IOException ex) {

        }

        setLayout(null);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        title_label = new JLabel("Home Page");
        title_label.setBounds(160,0,500,100);
        title_label.setBackground(Color.WHITE);
        title_label.setForeground(Color.WHITE);
        title_label.setFont(new Font("Kumtor",Font.BOLD,40));
        this.add(title_label);

        mineOperations_label = new JLabel("<html>Mine Operations<br>Training</html>");
        mineOperations_label.setBounds(550,50,300,200);
        mineOperations_label.setForeground(new Color(72,107,88));
        mineOperations_label.setFont(new Font("Kumtor",Font.BOLD,30));
        this.add(mineOperations_label);

        gorniy_label = new JLabel("<html>Горный Оператор-<br>Обучение</html>");
        gorniy_label.setBounds(550,120,300,200);
        gorniy_label.setForeground(new Color(72,107,88));
        gorniy_label.setFont(new Font("Kumtor",Font.BOLD,20));
        this.add(gorniy_label);


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        buttons_panel = new JPanel();
        buttons_panel.setBackground(Color.white);
        buttons_panel.setBounds(30,120,500,500);
        buttons_panel.setLayout(new GridLayout(6,1));
        this.add(buttons_panel);

        enterData_button = new JButton("<html> <big> Enter/Data Files </big><br /> Ввод/Просмотр Общих Данных </html>");
        buttons_panel.add(enterData_button);
        enterData_button.setHorizontalAlignment(SwingConstants.LEFT);

        viewFiles_button = new JButton("<html> <big> View Files by </big> <br /> Просмотр данных по оборудованию </html>");
        buttons_panel.add(viewFiles_button);
        viewFiles_button.setHorizontalAlignment(SwingConstants.LEFT);

        viewSchedule_button = new JButton("<html> <big> View/Schedule SRT </big> <br /> Повторное планирование по ТБ </html>");
        buttons_panel.add(viewSchedule_button);
        viewSchedule_button.setHorizontalAlignment(SwingConstants.LEFT);

        reports_button = new JButton("<html> <big> Reports </big> <br /> Отчеты </html>");
        buttons_panel.add(reports_button);
        reports_button.setHorizontalAlignment(SwingConstants.LEFT);

        addList_button = new JButton("<html> <big> Add List </big> <br /> Лист включения инструкторов итд. </html>");
        buttons_panel.add(addList_button);
        addList_button.setHorizontalAlignment(SwingConstants.LEFT);

        exit_button= new JButton("<html> <big> EXIT DATABASE </big> <br /> Выход из программы </html> ");
        exit_button.setForeground(Color.RED);
        buttons_panel.add(exit_button);
        exit_button.setHorizontalAlignment(SwingConstants.LEFT);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    protected void paintComponent(Graphics gs){
        super.paintComponent(gs);

        gs.setColor(Color.WHITE);
        gs.fillRect(0,0,900,700);

        gs.setColor(Color.ORANGE);
        gs.fillRect(0,0,900,100);

        gs.drawImage(logo_image,0,0,150,100,this);
        gs.drawImage(safe_image,560,270,300,300,this);
    }

    public void actionPerformed(ActionEvent e){
        repaint();
    }
}
*/