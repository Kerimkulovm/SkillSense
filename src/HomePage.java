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

public class HomePage extends JPanel {


    private JLabel title_label;
    private JLabel gorniy_label;

    private JPanel buttons_panel;

    private JButton viewFiles_button;
    private JButton viewSchedule_button;
    private JButton editSRT_button;
    private JButton classificationCourse_button;
    private JButton employeeInfo_button;
    private JButton operations_button;
    private JButton dailyEditorial_button;
    private BufferedImage logo_image, bg_image;

    public HomePage()
    {
        setLayout(null);

        try {
            logo_image = ImageIO.read(Objects.requireNonNull(getClass().getResource("resources/logo/logo1.1.png")));
            bg_image = ImageIO.read(Objects.requireNonNull(getClass().getResource("resources/logo/bg.jpg")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        buttons_panel = new JPanel();
        buttons_panel.setBackground(new Color(255,0,0,0));
        buttons_panel.setBounds(30, 120, 500, 500);
        buttons_panel.setLayout(new GridLayout(7, 1,5,5));
        this.add(buttons_panel);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        employeeInfo_button = new JButton("<html> <big> Просмотр Данных о Сотрудниках </big><br />(Просмотреть/Изменить/Данные о Сотрудниках)</html>");
        buttons_panel.add(employeeInfo_button);
        employeeInfo_button.setHorizontalAlignment(SwingConstants.LEFT);
        employeeInfo_button.setBackground(Color.WHITE);
        employeeInfo_button.setFont(Font.getFont("Lena"));
        employeeInfo_button.setBorder(new RoundedBorder(20));
        employeeInfo_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MineOperations.card.show(MineOperations.cardPane,"Employee Info");
                EmployeeInfo.positionRus_box = EmployeeInfo.databaseQueries.loadJobTitlesBox(EmployeeInfo.positionRus_box);
                EmployeeInfo.supervisor_box = EmployeeInfo.databaseQueries.loadSupervisorBox(EmployeeInfo.supervisor_box);
                EmployeeInfo.departmentRus_box = EmployeeInfo.databaseQueries.loadDepartmentsBox(EmployeeInfo.departmentRus_box);
                EmployeeInfo.crewRus_box = EmployeeInfo.databaseQueries.loadCrewBox(EmployeeInfo.crewRus_box);
            }
        });


        operations_button = new JButton("<html> <big>  Ввод Ежедневной Информации </big><br />(Ввод ежедневной информации о сотрудниках и данных по ТБ)</html>");
        buttons_panel.add(operations_button);
        operations_button.setHorizontalAlignment(SwingConstants.LEFT);
        operations_button.setBackground(Color.WHITE);
        operations_button.setFont(Font.getFont("Lena"));
        operations_button.setBorder(new RoundedBorder(20));
        operations_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MineOperations.card.show(MineOperations.cardPane, "Operations Daily");
                //OperationsDaily.CourseName_box = OperationsDaily.databaseQueries.loadCourseNameBox(OperationsDaily.CourseName_box);
                //OperationsDaily.trainer_box = OperationsDaily.databaseQueries.loadTrainerBox(OperationsDaily.trainer_box);
            }
        });

        viewFiles_button = new JButton("<html> <big> Квалификации Сотрудников  </big> <br /> (Обзор квалификации по сменам и часов работ по оборудованию) </html>");
        buttons_panel.add(viewFiles_button);
        viewFiles_button.setHorizontalAlignment(SwingConstants.LEFT);
        viewFiles_button.setBackground(Color.WHITE);
        viewFiles_button.setFont(Font.getFont("Lena"));
        viewFiles_button.setBorder(new RoundedBorder(20));
        viewFiles_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MineOperations.card.show(MineOperations.cardPane,"View Qualifications");
                ViewQualifications.coursesList = ViewQualifications.databaseQueries.loadCourses();
                ViewQualifications.courses_tableModel.fireTableDataChanged();
            }
        });


        viewSchedule_button = new JButton("<html> <big> Ежегодное Обучение </big> <br /> (Ввод данных по ТБ) </html>");
        buttons_panel.add(viewSchedule_button);
        viewSchedule_button.setHorizontalAlignment(SwingConstants.LEFT);
        viewSchedule_button.setBackground(Color.WHITE);
        viewSchedule_button.setFont(Font.getFont("Lena"));
        viewSchedule_button.setBorder(new RoundedBorder(20));
        viewSchedule_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MineOperations.card.show(MineOperations.cardPane, "Enter SRT");
                //EnterSRT.SRTName_box = EnterSRT.databaseQueries.loadSRTNameBox(EnterSRT.SRTName_box);
                //EnterSRT.trainer_box = EnterSRT.databaseQueries.loadTrainerBox(EnterSRT.trainer_box);
            }
        });

        editSRT_button = new JButton("<html> <big> Часы Ежегодного Обучения  </big> <br /> (Управление часами ежегодного обучения) </html>");
        buttons_panel.add(editSRT_button);
        editSRT_button.setHorizontalAlignment(SwingConstants.LEFT);
        editSRT_button.setBackground(Color.WHITE);
        editSRT_button.setFont(Font.getFont("Lena"));
        editSRT_button.setBorder(new RoundedBorder(20));
        editSRT_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MineOperations.card.show(MineOperations.cardPane,"SRTEditorial");
            }
        });

        dailyEditorial_button = new JButton("<html> <big> Ежедневные Часы </big> <br /> (Управление ежедневными часами сотдрудников) </html>");
        buttons_panel.add(dailyEditorial_button);
        dailyEditorial_button.setHorizontalAlignment(SwingConstants.LEFT);
        dailyEditorial_button.setBackground(Color.WHITE);
        dailyEditorial_button.setFont(Font.getFont("Lena"));
        dailyEditorial_button.setBorder(new RoundedBorder(20));
        dailyEditorial_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MineOperations.card.show(MineOperations.cardPane,"DaysEditorial");
            }
        });

        classificationCourse_button = new JButton("<html> <big> Классификаторы </big> <br /> (Управление классификаторами курсов, инструкторов) </html>");
        buttons_panel.add(classificationCourse_button);
        classificationCourse_button.setHorizontalAlignment(SwingConstants.LEFT);
        classificationCourse_button.setBackground(Color.WHITE);
        classificationCourse_button.setFont(Font.getFont("Lena"));
        classificationCourse_button.setBorder(new RoundedBorder(20));
        classificationCourse_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MineOperations.card.show(MineOperations.cardPane,"Classificatory");
            }
        });
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 900, 750);

        g.drawImage(bg_image,0,0,this);
        g.drawImage(logo_image,20,20,this);
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





