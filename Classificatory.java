import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Classificatory extends JPanel {

    private BufferedImage logo_image, bg_image;

    private JLabel title_label;

    private JPanel buttons_panel;

    public Classificatory(){

        try {
            logo_image = ImageIO.read(Objects.requireNonNull(getClass().getResource("resources/logo/Logo2.png")));
            bg_image = ImageIO.read(Objects.requireNonNull(getClass().getResource("resources/logo/bg.jpg")));
        } catch (IOException ex){
            ex.printStackTrace();
        }

        setLayout(null);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        title_label = new JLabel("<html><big>Управление классификаторами</big><br />(Курсы, Инструктора, Позиции, Отделы, SRT)</html>");
        title_label.setBounds(160, 0, 500, 100);
        title_label.setForeground(Color.BLACK);
        title_label.setFont(Font.getFont("Lena"));
        this.add(title_label);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        buttons_panel = new JPanel();
        buttons_panel.setBackground(Color.WHITE);
        buttons_panel.setBounds(30, 120, 500, 500);
        buttons_panel.setLayout(new GridLayout(7, 1, 5, 5));
        this.add(buttons_panel);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JButton courses_button = new JButton("<html> <big> Курсы </big><br />(Просмотреть/Изменить/Данные о Курсах)</html>");
        buttons_panel.add(courses_button);
        courses_button.setHorizontalAlignment(SwingConstants.LEFT);
        courses_button.setBackground(Color.WHITE);
        courses_button.setBorder(new RoundedBorder(10));
        courses_button.setFont(Font.getFont("Lena"));
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
        instructors_button.setFont(Font.getFont("Lena"));
        instructors_button.setBorder(new RoundedBorder(10));
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
        positions_button.setBorder(new RoundedBorder(10));
        positions_button.setFont(Font.getFont("Lena"));
        positions_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MineOperations.card.show(MineOperations.cardPane,"Positions");
            }
        });

        JButton departments_button = new JButton("<html> <big> Отделы </big><br />(Просмотреть/Изменить/Данные об Отделах)</html>");
        buttons_panel.add(departments_button);
        departments_button.setHorizontalAlignment(SwingConstants.LEFT);
        departments_button.setBackground(Color.WHITE);
        departments_button.setFont(Font.getFont("Lena"));
        departments_button.setBorder(new RoundedBorder(10));
        departments_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MineOperations.card.show(MineOperations.cardPane,"Departments");
            }
        });

        JButton crews_button = new JButton("<html> <big> Смены </big><br />(Просмотреть/Изменить/Данные о Сменах)</html>");
        buttons_panel.add(crews_button);
        crews_button.setHorizontalAlignment(SwingConstants.LEFT);
        crews_button.setBackground(Color.WHITE);
        crews_button.setBorder(new RoundedBorder(10));
        crews_button.setFont(Font.getFont("Lena"));
        crews_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MineOperations.card.show(MineOperations.cardPane,"Crews");
            }
        });

        JButton SRT_button = new JButton("<html> <big> SRT </big><br />(Просмотреть/Изменить/Данные о SRT)</html>");
        buttons_panel.add(SRT_button);
        SRT_button.setHorizontalAlignment(SwingConstants.LEFT);
        SRT_button.setBackground(Color.WHITE);
        SRT_button.setFont(Font.getFont("Lena"));
        SRT_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MineOperations.card.show(MineOperations.cardPane,"SRT");
            }
        });

        JButton supervisors_button = new JButton("<html> <big> Руководители </big><br />(Просмотреть/Изменить/Данные о Руководителях)</html>");
        buttons_panel.add(supervisors_button);
        supervisors_button.setHorizontalAlignment(SwingConstants.LEFT);
        supervisors_button.setBackground(Color.WHITE);
        supervisors_button.setFont(Font.getFont("Lena"));
        supervisors_button.setBorder(new RoundedBorder(10));
        supervisors_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MineOperations.card.show(MineOperations.cardPane,"Supervisors");
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JButton exit_button = new JButton("Выход");
        exit_button.setBounds(720, 60, 150, 30);
        exit_button.setBackground(Color.WHITE);
        exit_button.setForeground(Color.RED);
        exit_button.setBorder(new RoundedBorder(10));
        exit_button.setFont(Font.getFont("Lena"));
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

        g.drawImage(bg_image,0,0,this);
        g.drawImage(logo_image, 0, 0, 150, 100, this);
    }

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
