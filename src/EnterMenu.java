import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EnterMenu extends JPanel {

    private BufferedImage logo_image, safe_image;

    public EnterMenu()
    {
        try {
            logo_image = ImageIO.read(new File("textures/logo/kumtor_logo.jpg"));
            safe_image = ImageIO.read(new File("textures/logo/safe_logo.jpg"));

        } catch (IOException ignored) {

        }

        setLayout(null);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel titleMenu_label = new JLabel("Enter Menu");
        titleMenu_label.setBounds(160, 0, 400, 100);
        titleMenu_label.setBackground(Color.WHITE);
        titleMenu_label.setForeground(Color.WHITE);
        titleMenu_label.setFont(new Font("Kumtor", Font.BOLD, 40));
        this.add(titleMenu_label);

        JLabel mineOperations_label = new JLabel("<html>Mine Operations<br>Training</html>");
        mineOperations_label.setBounds(550, 50, 300, 200);
        mineOperations_label.setForeground(new Color(72, 107, 88));
        mineOperations_label.setFont(new Font("Kumtor", Font.BOLD, 30));
        this.add(mineOperations_label);

        JLabel gorniy_label = new JLabel("<html>Горный Оператор-<br>Обучение</html>");
        gorniy_label.setBounds(550, 120, 300, 200);
        gorniy_label.setForeground(new Color(72, 107, 88));
        gorniy_label.setFont(new Font("Kumtor", Font.BOLD, 20));
        this.add(gorniy_label);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel buttons_panel = new JPanel();
        buttons_panel.setBackground(Color.WHITE);
        buttons_panel.setBounds(30, 120, 500, 500);
        buttons_panel.setLayout(new GridLayout(4, 1));
        this.add(buttons_panel);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////

        JButton employeesInfo_button = new JButton("<html><big>Employees Info.</big><br />Ввод информации о работниках</html>");
        buttons_panel.add(employeesInfo_button);
        employeesInfo_button.setHorizontalAlignment(SwingConstants.LEFT);
        employeesInfo_button.setBackground(Color.WHITE);
        employeesInfo_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MineOperations.card.show(MineOperations.cardPane,"Employee Info");
            }
        });

        JButton operations_button = new JButton("<html><big>Mine Operations daily</big><br />Ввод ежедневной информации</html>");
        buttons_panel.add(operations_button);
        operations_button.setHorizontalAlignment(SwingConstants.LEFT);
        operations_button.setBackground(Color.WHITE);
        operations_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MineOperations.card.show(MineOperations.cardPane,"Operations Daily");
            }
        });

        JButton srt_button = new JButton("<html><big>Enter SRT</big><br />Ввод данных по ТБ</html>");
        buttons_panel.add(srt_button);
        srt_button.setHorizontalAlignment(SwingConstants.LEFT);
        srt_button.setBackground(Color.WHITE);
        srt_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MineOperations.card.show(MineOperations.cardPane,"Enter SRT");
            }
        });

        JButton return_button = new JButton("<html><big>Return to Main Switchboard</big><br />Возврат в главное меню</html>");
        buttons_panel.add(return_button);
        return_button.setBackground(Color.WHITE);
        return_button.setForeground(Color.red);
        return_button.setHorizontalAlignment(SwingConstants.LEFT);
        return_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MineOperations.card.previous(MineOperations.cardPane);
            }
        });
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // paint children

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 900, 700);

        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, 900, 100);

        g.drawImage(logo_image, 0, 0, 150, 100, this);
        g.drawImage(safe_image, 560, 270, 300, 300, this);
    }
}
