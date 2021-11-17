import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Classificatory extends JPanel {

    private BufferedImage logo_image;

    private JLabel title_label;

    private JPanel buttons_panel;

    public Classificatory(){

        try {
            logo_image = ImageIO.read(new File("textures/logo/kumtor_logo.jpg"));
        } catch (IOException ex){
            ex.printStackTrace();
        }

        setLayout(null);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        title_label = new JLabel("<html><big>Управление классификаторами</big><br />(Курсы, Инструктора, Позиции, SRT)</html>");
        title_label.setBounds(160, 0, 500, 100);
        title_label.setForeground(Color.WHITE);
        title_label.setFont(new Font("Helvetica", Font.BOLD, 20));
        this.add(title_label);


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        buttons_panel = new JPanel();
        buttons_panel.setBackground(Color.white);
        buttons_panel.setBounds(30, 120, 500, 500);
        buttons_panel.setLayout(new GridLayout(6, 1));
        this.add(buttons_panel);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JButton courses_button = new JButton("<html> <big> Курсы </big><br />(Просмотреть/Изменить/Данные о Курсах)</html>");
        buttons_panel.add(courses_button);
        courses_button.setHorizontalAlignment(SwingConstants.LEFT);
        courses_button.setBackground(Color.WHITE);
        courses_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MineOperations.card.show(MineOperations.cardPane,"Courses");
            }
        });

        JButton instructors_button = new JButton("<html> <big> Инструкторы </big><br />(Просмотреть/Изменить/Данные об Инструкторах)</html>");
        buttons_panel.add(instructors_button);
        instructors_button.setHorizontalAlignment(SwingConstants.LEFT);
        instructors_button.setBackground(Color.WHITE);
        instructors_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MineOperations.card.show(MineOperations.cardPane,"Instructors");
            }
        });

        JButton positions_button = new JButton("<html> <big> Должности </big><br />(Просмотреть/Изменить/Данные о Должностях)</html>");
        buttons_panel.add(positions_button);
        positions_button.setHorizontalAlignment(SwingConstants.LEFT);
        positions_button.setBackground(Color.WHITE);
        positions_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MineOperations.card.show(MineOperations.cardPane,"Positions");
            }
        });


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JButton exit_button = new JButton("Выход");
        exit_button.setBounds(720, 60, 150, 30);
        exit_button.setBackground(Color.RED);
        exit_button.setForeground(Color.WHITE);
        add(exit_button);
        exit_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MineOperations.card.show(MineOperations.cardPane,"Home Page");
            }
        });
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // paint children

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 900, 750);

        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, 900, 100);

        g.drawImage(logo_image, 0, 0, 150, 100, this);
    }
}
