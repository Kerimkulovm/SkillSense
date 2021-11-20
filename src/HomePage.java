import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HomePage extends JPanel {

    private BufferedImage logo_image;
    private BufferedImage safe_image;

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

    public HomePage()
    {
        try {
            logo_image = ImageIO.read(new File("textures/logo/kumtor_logo.jpg"));
            safe_image = ImageIO.read(new File("textures/logo/safe_logo.jpg"));
        } catch (IOException ex) {

        }

        setLayout(null);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        title_label = new JLabel("Главная страница");
        title_label.setBounds(160, 0, 500, 100);
        title_label.setBackground(Color.WHITE);
        title_label.setForeground(Color.WHITE);
        title_label.setFont(new Font("Helvetica", Font.BOLD, 40));
        this.add(title_label);

        gorniy_label = new JLabel("<html>Горный Оператор-<br>Обучение</html>");
        gorniy_label.setBounds(550, 50, 300, 200);
        gorniy_label.setForeground(new Color(72, 107, 88));
        gorniy_label.setFont(new Font("Helvetica", Font.BOLD, 30));
        this.add(gorniy_label);

        buttons_panel = new JPanel();
        buttons_panel.setBackground(Color.white);
        buttons_panel.setBounds(30, 120, 500, 500);
        buttons_panel.setLayout(new GridLayout(7, 1,5,5));
        this.add(buttons_panel);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        employeeInfo_button = new JButton("<html> <big> Просмотр Данных о Сотрудниках </big><br />(Просмотреть/Изменить/Данные о Сотрудниках)</html>");
        buttons_panel.add(employeeInfo_button);
        employeeInfo_button.setHorizontalAlignment(SwingConstants.LEFT);
        employeeInfo_button.setBackground(Color.WHITE);
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
        operations_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MineOperations.card.show(MineOperations.cardPane, "Operations Daily");
                OperationsDaily.CourseName_box = OperationsDaily.databaseQueries.loadCourseNameBox(OperationsDaily.CourseName_box);
                OperationsDaily.trainer_box = OperationsDaily.databaseQueries.loadTrainerBox(OperationsDaily.trainer_box);
            }
        });

        viewFiles_button = new JButton("<html> <big> Квалификации Сотрудников  </big> <br /> (Обзор квалификации по сменам и часов работ по оборудованию) </html>");
        buttons_panel.add(viewFiles_button);
        viewFiles_button.setHorizontalAlignment(SwingConstants.LEFT);
        viewFiles_button.setBackground(Color.WHITE);
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
        viewSchedule_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MineOperations.card.show(MineOperations.cardPane, "Enter SRT");
                EnterSRT.CourseName_box = EnterSRT.databaseQueries.loadCourseNameBox(EnterSRT.CourseName_box);
                EnterSRT.trainer_box = EnterSRT.databaseQueries.loadTrainerBox(EnterSRT.trainer_box);
            }
        });


        classificationCourse_button = new JButton("<html> <big> Классификаторы </big> <br /> (Управление классификаторами курсов, инструкторов) </html>");
        buttons_panel.add(classificationCourse_button);
        classificationCourse_button.setHorizontalAlignment(SwingConstants.LEFT);
        classificationCourse_button.setBackground(Color.WHITE);
        classificationCourse_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MineOperations.card.show(MineOperations.cardPane,"Classificatory");
            }
        });

        editSRT_button = new JButton("<html> <big> Часы SRT </big> <br /> (Управление классификаторами курсов, инструкторов) </html>");
        buttons_panel.add(editSRT_button);
        editSRT_button.setHorizontalAlignment(SwingConstants.LEFT);
        editSRT_button.setBackground(Color.WHITE);
        editSRT_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MineOperations.card.show(MineOperations.cardPane,"SRTEditorial");
            }
        });

        dailyEditorial_button = new JButton("<html> <big> Ежедневные Часы </big> <br /> (Управление классификаторами курсов, инструкторов) </html>");
        buttons_panel.add(dailyEditorial_button);
        dailyEditorial_button.setHorizontalAlignment(SwingConstants.LEFT);
        dailyEditorial_button.setBackground(Color.WHITE);
        dailyEditorial_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MineOperations.card.show(MineOperations.cardPane,"DaysEditorial");
            }
        });
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 900, 750);

        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, 900, 100);

        g.drawImage(logo_image, 0, 0, 150, 100, this);
        g.drawImage(safe_image, 560, 270, 300, 300, this);
    }
}



