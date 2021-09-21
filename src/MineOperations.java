import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MineOperations extends JFrame {

    public static JFrame application_frame;

    public static final int FRAME_WIDTH = 900;
    public static final int FRAME_HEIGHT = 750;

    public static HomePagePanel homePagePanel = new HomePagePanel();
    public static EmployeeInfoPanel employeeInfoPanel = new EmployeeInfoPanel();
    public static EnterMenuPanel enterMenuPanel = new EnterMenuPanel();
    public static OperationsDailyPanel operationsDailyPanel = new OperationsDailyPanel();

    public static CardLayout card;
    public static JPanel cardPane;

    public static void main(String[] args) {
        launchMineOperationsTraining();
    }

    private static void launchMineOperationsTraining() {

        application_frame = new JFrame();
        application_frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        application_frame.setTitle("Mine Operations Training");
        application_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardPane = new JPanel();
        card = new CardLayout();
        cardPane.setLayout(card);
        //cardPane.add(loginPanel,"Login Page");
        cardPane.add(homePagePanel,"Home Page");
        cardPane.add(enterMenuPanel, "Enter Data");
        cardPane.add(employeeInfoPanel, "Employee Info");
        cardPane.add(operationsDailyPanel, "Operations Daily");

        application_frame.add(cardPane);
        application_frame.setVisible(true);
    }

    public static class HomePagePanel extends JPanel {

        private BufferedImage logo_image;
        private BufferedImage safe_image;

        private JLabel title_label;
        private JLabel mineOperations_label;
        private JLabel gorniy_label;

        private JPanel buttons_panel;

        private JButton enterData_button;
        private JButton viewFiles_button;
        private JButton viewSchedule_button;
        private JButton reports_button;
        private JButton addList_button;
        private JButton exit_button;

        public HomePagePanel() {

            try {
                logo_image = ImageIO.read(new File("textures/logo/kumtor_logo.jpg"));
                safe_image = ImageIO.read(new File("textures/logo/safe_logo.jpg"));
            } catch (IOException ex) {

            }

            setLayout(null);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            title_label = new JLabel("Home Page");
            title_label.setBounds(160, 0, 500, 100);
            title_label.setBackground(Color.WHITE);
            title_label.setForeground(Color.WHITE);
            title_label.setFont(new Font("Kumtor", Font.BOLD, 40));
            this.add(title_label);

            mineOperations_label = new JLabel("<html>Mine Operations<br>Training</html>");
            mineOperations_label.setBounds(550, 50, 300, 200);
            mineOperations_label.setForeground(new Color(72, 107, 88));
            mineOperations_label.setFont(new Font("Kumtor", Font.BOLD, 30));
            this.add(mineOperations_label);

            gorniy_label = new JLabel("<html>Горный Оператор-<br>Обучение</html>");
            gorniy_label.setBounds(550, 120, 300, 200);
            gorniy_label.setForeground(new Color(72, 107, 88));
            gorniy_label.setFont(new Font("Kumtor", Font.BOLD, 20));
            this.add(gorniy_label);

            buttons_panel = new JPanel();
            buttons_panel.setBackground(Color.white);
            buttons_panel.setBounds(30, 120, 500, 500);
            buttons_panel.setLayout(new GridLayout(6, 1));
            this.add(buttons_panel);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            enterData_button = new JButton("<html> <big> Enter/Data Files </big><br /> Ввод/Просмотр Общих Данных </html>");
            buttons_panel.add(enterData_button);
            enterData_button.setHorizontalAlignment(SwingConstants.LEFT);
            enterData_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    card.show(cardPane,"Enter Data");
                }
            });

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

            exit_button = new JButton("<html> <big> EXIT DATABASE </big> <br /> Выход из программы </html> ");
            exit_button.setForeground(Color.RED);
            buttons_panel.add(exit_button);
            exit_button.setHorizontalAlignment(SwingConstants.LEFT);
            exit_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    application_frame.dispatchEvent(new WindowEvent(application_frame, WindowEvent.WINDOW_CLOSING));
                }
            });
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(Color.WHITE);
            g.fillRect(0, 0, 900, 700);

            g.setColor(Color.ORANGE);
            g.fillRect(0, 0, 900, 100);

            g.drawImage(logo_image, 0, 0, 150, 100, this);
            g.drawImage(safe_image, 560, 270, 300, 300, this);
        }
    }

    public static class EnterMenuPanel extends JPanel {

        private BufferedImage logo_image;
        private BufferedImage safe_image;

        private JLabel mineOperations_label;
        private JLabel gorniy_label;

        private JPanel buttons_panel;

        private JButton employeesInfo_button;
        private JButton operations_button;
        private JButton srt_button;
        private JButton return_button;

        public EnterMenuPanel() {

            try {
                logo_image = ImageIO.read(new File("textures/logo/kumtor_logo.jpg"));
                safe_image = ImageIO.read(new File("textures/logo/safe_logo.jpg"));

            } catch (IOException ex) {

            }

            setLayout(null);

            /////////////////////////////////////////////////////////////////////////////////////////////////////////

            JLabel titleMenu_label = new JLabel("Enter Menu");
            titleMenu_label.setBounds(160, 0, 400, 100);
            titleMenu_label.setBackground(Color.WHITE);
            titleMenu_label.setForeground(Color.WHITE);
            titleMenu_label.setFont(new Font("Kumtor", Font.BOLD, 40));
            this.add(titleMenu_label);

            mineOperations_label = new JLabel("<html>Mine Operations<br>Training</html>");
            mineOperations_label.setBounds(550, 50, 300, 200);
            mineOperations_label.setForeground(new Color(72, 107, 88));
            mineOperations_label.setFont(new Font("Kumtor", Font.BOLD, 30));
            this.add(mineOperations_label);

            gorniy_label = new JLabel("<html>Горный Оператор-<br>Обучение</html>");
            gorniy_label.setBounds(550, 120, 300, 200);
            gorniy_label.setForeground(new Color(72, 107, 88));
            gorniy_label.setFont(new Font("Kumtor", Font.BOLD, 20));
            this.add(gorniy_label);

            /////////////////////////////////////////////////////////////////////////////////////////////////////////

            buttons_panel = new JPanel();
            buttons_panel.setBackground(Color.WHITE);
            buttons_panel.setBounds(30, 120, 500, 500);
            buttons_panel.setLayout(new GridLayout(4, 1));
            this.add(buttons_panel);

            /////////////////////////////////////////////////////////////////////////////////////////////////////////

            employeesInfo_button = new JButton("<html><big>Employees Info.</big><br />Ввод информации о работниках</html>");
            buttons_panel.add(employeesInfo_button);
            employeesInfo_button.setHorizontalAlignment(SwingConstants.LEFT);
            employeesInfo_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    card.show(cardPane,"Employee Info");
                }
            });

            operations_button = new JButton("<html><big>Mine Operations daily</big><br />Ввод ежедневной информации</html>");
            buttons_panel.add(operations_button);
            operations_button.setHorizontalAlignment(SwingConstants.LEFT);
            operations_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    card.show(cardPane,"Operations Daily");
                }
            });

            srt_button = new JButton("<html><big>Enter SRT</big><br />Ввод данных по ТБ</html>");
            buttons_panel.add(srt_button);
            srt_button.setHorizontalAlignment(SwingConstants.LEFT);

            return_button = new JButton("<html><big>Return to Main Switchboard</big><br />Возврат в главное меню</html>");
            buttons_panel.add(return_button);
            return_button.setForeground(Color.red);
            return_button.setHorizontalAlignment(SwingConstants.LEFT);
            return_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    card.previous(cardPane);
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

    public static class EmployeeInfoPanel extends JPanel {
        private JLabel tableID_label;

        private JTextField nameEng_text;
        private JTextField nameRus_text;
        private JTextField reportsTo_text;
        private JTextField jobClass_text;
        private JTextField jobLevel_text;
        private JTextField hireDate_text;
        private JTextField lastOr_text;
        private JTextField firstAid_text;
        private JTextField pdcs_text;
        private JTextField tableID_text;

        private JButton searchButton;
        private JButton add_button;
        private JButton save_button;
        private JButton edit_button;
        private JButton cancel_button;
        private JButton delete_button;

        private JComboBox location_box;
        private JComboBox departmentEng_box;
        private JComboBox departmentRus_box;
        private JComboBox shiftEng_box;
        private final JComboBox shiftRus_box;
        private JComboBox positionEng_box;
        private JComboBox positionRus_box;

        private BufferedImage logo_image;

        public EmployeeInfoPanel() {
            try {
                logo_image = ImageIO.read(new File("textures/logo/kumtor_logo.jpg"));
            } catch (IOException ex) {

            }

            setLayout(null);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JLabel titleEng = new JLabel("Employee Information");
            titleEng.setBounds(160, 0, 400, 100);
            titleEng.setBackground(Color.WHITE);
            titleEng.setForeground(Color.WHITE);
            titleEng.setFont(new Font("Kumtor", Font.BOLD, 30));
            this.add(titleEng);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            JPanel searchEmployee_panel = new JPanel();
            searchEmployee_panel.setBackground(Color.white);
            searchEmployee_panel.setLayout(new GridLayout(3,2));
            searchEmployee_panel.setBorder(new TitledBorder(new LineBorder(Color.orange), "Search Employee"));
            searchEmployee_panel.setBounds(20, 120, 450, 90);
            this.add(searchEmployee_panel);

            JPanel tableID_panel = new JPanel();
            tableID_label = new JLabel("Табельный номер или Ф.И.О | Employee ID: ");
            tableID_label.setForeground(Color.RED);
            tableID_panel.add(tableID_label);
            tableID_panel.setBackground(Color.WHITE);
            searchEmployee_panel.add(tableID_label);

            tableID_text = new JTextField();
            tableID_text.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1, true));
            tableID_panel.add(tableID_text);
            searchEmployee_panel.add(tableID_text);

            searchButton = new JButton("Search");
            searchButton.setForeground(Color.RED);
            tableID_panel.add(searchButton);
            searchEmployee_panel.add(searchButton);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JPanel employeeInfo_panel = new JPanel();
            employeeInfo_panel.setBackground(Color.white);
            employeeInfo_panel.setLayout(new BoxLayout(employeeInfo_panel, BoxLayout.X_AXIS));
            employeeInfo_panel.setBorder(new TitledBorder(new LineBorder(Color.orange), "Personal Information"));
            employeeInfo_panel.setBounds(20, 250, 450, 350);
            this.add(employeeInfo_panel);

            JPanel infoLabels = new JPanel();
            JPanel inputPanel = new JPanel();

            infoLabels.setLayout(new BoxLayout(infoLabels, BoxLayout.Y_AXIS));
            infoLabels.setBackground(Color.WHITE);
            employeeInfo_panel.add(infoLabels);

            inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
            inputPanel.setBackground(Color.WHITE);
            employeeInfo_panel.add(inputPanel);

            JPanel lastOrientationInfoPanel = new JPanel();
            lastOrientationInfoPanel.setBackground(Color.WHITE);
            lastOrientationInfoPanel.setBounds(20, 520, 450, 130);
            lastOrientationInfoPanel.setLayout(new BoxLayout(lastOrientationInfoPanel, BoxLayout.X_AXIS));
            lastOrientationInfoPanel.setBorder(new TitledBorder(new LineBorder(Color.orange), "Last Orientation Information"));
            this.add(lastOrientationInfoPanel);

            JPanel lastOrientLabels = new JPanel();
            JPanel lastOrientTexts = new JPanel();

            lastOrientLabels.setLayout(new BoxLayout(lastOrientLabels, BoxLayout.Y_AXIS));
            lastOrientLabels.setBackground(Color.WHITE);
            lastOrientationInfoPanel.add(lastOrientLabels);

            lastOrientTexts.setLayout(new BoxLayout(lastOrientTexts, BoxLayout.Y_AXIS));
            lastOrientTexts.setBackground(Color.WHITE);
            lastOrientationInfoPanel.add(lastOrientTexts);

            JPanel photoPanel = new JPanel();
            photoPanel.setBackground(Color.WHITE);
            photoPanel.setBounds(480, 120, 390, 450);
            photoPanel.setLayout(new BoxLayout(photoPanel, BoxLayout.X_AXIS));
            photoPanel.setBorder(new TitledBorder(new LineBorder(Color.orange), "Photo"));
            this.add(photoPanel);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JPanel nameEng_panel = new JPanel();
            JLabel nameEng_label = new JLabel("Full Name: ");
            nameEng_panel.add(nameEng_label);
            nameEng_panel.setBackground(Color.WHITE);
            infoLabels.add(nameEng_panel);

            nameEng_text = new JTextField();
            nameEng_text.setEnabled(false);
            nameEng_panel.add(nameEng_text);
            inputPanel.add(nameEng_text);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JPanel nameRus_panel = new JPanel();
            JLabel nameRus_label = new JLabel("Имя и Фамилия: ");
            nameRus_panel.add(nameRus_label);
            nameRus_panel.setBackground(Color.WHITE);
            infoLabels.add(nameRus_panel);

            nameRus_text = new JTextField();
            nameRus_text.setEnabled(false);
            nameRus_panel.add(nameRus_text);
            inputPanel.add(nameRus_text);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JPanel positionEng_panel = new JPanel();
            JLabel positionEng_label = new JLabel("Position:");
            positionEng_panel.add(positionEng_label);
            positionEng_panel.setBackground(Color.WHITE);
            infoLabels.add(positionEng_panel);

            String[] positionEng_list = new String[]{"","Water-supply Facility Operator","777B Driver","785C Driver","789C Driver","Admin. Mine Operations,",
            "Assistant Coordinator","Backhoe Coordinator","BathCPlant Coordinator","Blast Trainer","Blaster 1","Blaster 2",
            "Blaster 3", "Boiler Operator","Bus Driver","Bus Foreman","Car washer","Chief Engineer","CI Specialist","Compactor operator",
            "Construction Site Supervisor","Contract Administrator","Contract Foreman","Cook","Crane Operator","Crusher Lead Hand",
            "Dewatering Coordinator","Dewatering Technician","Dispatcher Mine","Dozer Operator","Drill Helper","Drill Lead Hand","Drill Trainer",
            "Drill/Blast Foreman","Drill/Blast Coordinator","Driller 1","Driller 2","Driver","E/W General Foreman","Earth works Foreman","Electrician",
            "Emulsion Plant Manager","Emulsion Plant Operator","Engineer","Engineering Manager","Environmental Engineer","Fitter","Fleet Shop Coordinator",
            "Foreman","Fuel Farm Lead Hand","Fuel Station Operator","Fuel Truck Operator","Gen Set Operator","General Mine Foreman","Geologist","Grader Operator",
            "Gravel Crusher Helper","Gravel Crusher Operator","Greaser","HD Equipment Maint.Coordinator","Instrumentation Foreman","Instrumentation Technician",
            "Jr. Foreman","Labourer","Loader Operator","Lowbed Driver","Mack Driver","Magazine Supervisor","Mechanic","Mill Crusher Helper","Mill Crusher Leadhand",
            "Mill Crusher Operator","Millwright","Mine Foreman","Mine Manager","Mine Trainer","Mine Training Coordinator","Mine Training Foreman","NTD Specialist",
            "Operations Superintendant","Other","Passenger Traffic Coordinator","Planning Engineer","Production Manager","R.T.D Operator","Rigger","Safety Engineer",
            "Safety Engineer Assistant","Safety Technician","Sampler","Security Coordinator","Security Officer","Security Superintendant","Senior Foreman",
            "Senior Roch Mechanics Eng.","Shaftman","Shovel Operator","SSSP Foreman","Strokeeper","Student","Surveyor","Technician","Track Dozer Operator","Trainer",
            "Translator","Turner","VP, Operations","Warehouse Supervisor","Welder","Intern"};
            positionEng_box = new JComboBox(positionEng_list);
            positionEng_box.setEnabled(false);
            positionEng_box.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    positionRus_box.setSelectedIndex(positionEng_box.getSelectedIndex());
                }
            });
            positionEng_panel.add(positionEng_box);
            inputPanel.add(positionEng_box);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JPanel positionRus_panel = new JPanel();
            JLabel positionRus_label = new JLabel("Должность: ");
            positionRus_panel.add(positionRus_label);
            positionRus_panel.setBackground(Color.WHITE);
            infoLabels.add(positionRus_panel);

            String[] positionRus_list = new String[]{"","Оператор водостанции","777В Водитель","785С Водитель","789С Водитель","Админ. Горного отдела",
            "Ассистент Координатор","Оператор экскаватора","Координатор РБУ","Инструктор взрывник","Взрывник 1","Взрывник 2","Взрывник 3","Оператор бойлерной",
            "Вахтовщик","Мастер вахтовщиков","Автомойщик","Главный инженер","Спец. по непрерывн.соверш.","Оператор компактора","Прораб","Админ. по контрактам",
            "Мастер подрядчиков","Повар","Оператор крана","Опер.фаб.дроб.","Координатор водоотлива","Техник по водоотводу","Диспетчер Горный","Оператор Бульдозера",
            "Пом.бур","Бригадир бурильщиков","Инструктор Буров.","Мастер БВР","Начальник отд. БВР","Бурильщик 1","Бурильщик 2","Водитель","Ст. Мастер Земельного Отдела",
            "Пом. Мастера Зем. Отдела","Электрик","Менеджер эмульсионного з-да","Оператор эмульсионного з-да","Инженер","Менеджер Инж. Одела","Эколог","Слесарь",
            "Координатор Нижнего гаража","Мастер","Мастер заправочной станции","Оператор заправочной станции","Оператор передвежной АЗС","Оператор генераора","Главный гор. мастер",
            "Геолог","Оператор грейдера","Помощник оператора","Оператор Гравийной Дробилки","Смазчик","Координатор ТО тяж. техники","Мастер КИПиА","Слесарь КИПиП",
            "Младший Мастер","Разнорабочий","Оператор Погрузчика","Водитель Трала","Водитель Mack","Зав. складом BB","Механик","Пом. Оператора Фабрики","Бригадир Конусной Дробилки","Оператор Фабричной Дробилки",
            "Монтажник","Горный Мастер","Менеджер Горного Отделения","Инструктор Производ. Обучения","Координатор Отдела Обучения","Мастер Отдела Обучения",
            "Дефектоскопист","Начальник Карьера","Разное","Координатор ПП","Плановик","Начальник Произодсьва","Оператор Колес. Будльдозера","Стропольщик","Инженер ТБ",
            "Ассистент Инженера ТБ","Техник Отдела ТБ","Пробоотборщик","Координатор СБ","Сотрудник СБ","Нач. Отдела Безопасности","Старший Мастер","Старший Геомеханик",
            "Проходчик","Оператор Экскаватора","Мастер СССР","Кладовщик","Студент","Маркшейдер","Тех. Специалист","Оператор Гусен. Бульдозера","Инструктор","Переводчик/Администратор",
            "Токарь","Вице-Президент по Произодству","Зав. Склад","Сварщик","Стажер"};
            positionRus_box = new JComboBox(positionRus_list);
            positionRus_box.setEnabled(false);
            positionRus_box.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    positionEng_box.setSelectedIndex(positionRus_box.getSelectedIndex());
                }
            });
            positionRus_panel.add(positionRus_box);
            inputPanel.add(positionRus_box);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JPanel reportsTo_panel = new JPanel();
            JLabel reportsTo_label = new JLabel("Начальник | Reports to: ");
            reportsTo_panel.add(reportsTo_label);
            reportsTo_panel.setBackground(Color.WHITE);
            infoLabels.add(reportsTo_panel);

            reportsTo_text = new JTextField();
            reportsTo_text.setEnabled(false);
            reportsTo_panel.add(reportsTo_text);
            inputPanel.add(reportsTo_text);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JPanel departmentEng_panel = new JPanel();
            JLabel departmentEng_label = new JLabel("Department: ");
            departmentEng_panel.add(departmentEng_label);
            departmentEng_panel.setBackground(Color.WHITE);
            infoLabels.add(departmentEng_panel);

            String[] departmentsEng_list = new String[]{"","Mine Maintenance","Mill Maintenance", "Mill","Dewatering Department","Engineering",
            "Administration","Other","Mine Operations","Site Services Special Projects","Earth Works","Drill and Blast","Environment","Contractor",
            "Exploration","UnderGround","Mine Training","Safety","Administration","IS&T","Senior Management","Security","Warehouse","BORUSAN","TURKUAZ MACHIERY",
            "KROSINK","ABT","KYRGYZ ALTYN","Tarylga Service","HELPER ltd","Infrastructure & Projects","Issyk-Kul Resurs","Tamga trans","Ishterman","Dar servis",
            "Ar-Ton","Epiroc","Daian Trans","Stalker","Rapsodia+","Djety-Oguz trans"};
            departmentEng_box = new JComboBox(departmentsEng_list);
            departmentEng_box.setEnabled(false);
            departmentEng_box.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    departmentRus_box.setSelectedIndex(departmentEng_box.getSelectedIndex());
                }
            });
            departmentEng_panel.add(departmentEng_box);
            inputPanel.add(departmentEng_box);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JPanel departmentRus_panel = new JPanel();
            JLabel departmentRus_label = new JLabel("Отдел: ");
            departmentRus_panel.add(departmentRus_label);
            departmentRus_panel.setBackground(Color.WHITE);
            infoLabels.add(departmentRus_panel);

            String[] departmentRus_list = new String[]{"","Тех. обслуживание Карьера", "Тех. обслуживание Фабрики","Операторы Фабрики","Отдел по Водоотводу",
            "Инженерный","Обучение в Администрации","Разные","Горный отдел","Спец. проект по Обслуж. Рудника", "Земельный Отдел", "Буровзрывной",
            "Охрана Окружающей Среды","Подрядчики","Геологоразведка","Подземка","Отдел Обучения Гор. Операций", "Отдел ТБ", "Администрация", "ИСиТ", "Высшее Руководство",
            "Служба Безопасности" , "Склад", "БОРУСАН", "ТУРКУАЗ МАШИНЕРИ","КРОСИНК","АВТ","КЫРГЫЗ АЛТЫН","Тарылга Сервис","Хелпер","Инфраструктура и Проекты","Ысык-Кол ресурс",
            "Тамга транс", "Иштерман","Дар сервис","Ар-Тон","Эпирок","Даян транс","Сталкер","Рапсодия+","Джети-Огуз транс"};
            departmentRus_box = new JComboBox(departmentRus_list);
            departmentRus_box.setEnabled(false);
            departmentRus_box.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    departmentEng_box.setSelectedIndex(departmentRus_box.getSelectedIndex());
                }
            });
            departmentRus_panel.add(departmentRus_box);
            inputPanel.add(departmentRus_box);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JPanel shiftEng_panel = new JPanel();
            JLabel shift_label = new JLabel("Shift: ");
            shiftEng_panel.add(shift_label);
            shiftEng_panel.setBackground(Color.WHITE);
            infoLabels.add(shiftEng_panel);

            String[] shiftEng_list = new String[]{"","A Crew","B Crew","C Crew","D Crew","D/B A Crew","D/B B Crew","D/B C Crew","D/B D Crew","E/W A/B Crew",
            "E/W C/D Crew","Engineering","SSSP","CONTRACTOR","OTHER CREW","ADMINISTRATION","ENVIRONMENT","Underground Portal","Mine Maintenance","Exploration","Mill",
            "SAFETY","SECURITY"};
            shiftEng_box = new JComboBox(shiftEng_list);
            shiftEng_box.setEnabled(false);
            shiftEng_box.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    shiftRus_box.setSelectedIndex(shiftEng_box.getSelectedIndex());
                }
            });
            shiftEng_panel.add(shiftEng_box);
            inputPanel.add(shiftEng_box);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JPanel shiftRus_panel = new JPanel();
            JLabel shiftRus_label = new JLabel("Смена: ");
            shiftRus_panel.add(shiftRus_label);
            shiftRus_panel.setBackground(Color.WHITE);
            infoLabels.add(shiftRus_panel);

            String[] shiftRus_list = new String[]{"","Бриг. А","Бриг. Б","Бриг. С","Бриг. Д","Б/В Бриг. А","Б/В Бриг. Б","Б/В Бриг. С","Б/В Бриг. Д","З/О Бриг. АБ",
            "З/О Бриг. СД","Инженерный","СССР","Подрядчие","Другие Бригад.","АДМИНИСТРАЦИЯ","ЭКОЛОГИЯ","Подземка","Тех. Обслуж","Геологоразведка","Фабрика","ТБ","СБ"};
            shiftRus_box = new JComboBox(shiftRus_list);
            shiftRus_box.setEnabled(false);
            shiftRus_box.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    shiftEng_box.setSelectedIndex(shiftRus_box.getSelectedIndex());
                }
            });
            shiftRus_panel.add(shiftRus_box);
            inputPanel.add(shiftRus_box);


            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JPanel jobClass_panel = new JPanel();
            JLabel jobClass_label = new JLabel("Класс. | Job Class: ");
            jobClass_panel.add(jobClass_label);
            jobClass_panel.setBackground(Color.WHITE);
            infoLabels.add(jobClass_panel);

            jobClass_text = new JTextField();
            jobClass_text.setEnabled(false);
            jobClass_panel.add(jobClass_text);
            inputPanel.add(jobClass_text);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JPanel jobLevel_panel = new JPanel();
            JLabel jobLevel_label = new JLabel("Уровень | Job Level: ");
            jobLevel_panel.add(jobLevel_label);
            jobLevel_panel.setBackground(Color.WHITE);
            infoLabels.add(jobLevel_panel);

            jobLevel_text = new JTextField();
            jobLevel_text.setEnabled(false);
            jobLevel_panel.add(jobLevel_text);
            inputPanel.add(jobLevel_text);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JPanel hireDate_panel = new JPanel();
            JLabel hireDate_label = new JLabel("Дата наема | Hire Date: ");
            hireDate_panel.add(hireDate_label);
            hireDate_panel.setBackground(Color.WHITE);
            infoLabels.add(hireDate_panel);

            hireDate_text = new JTextField();
            hireDate_text.setEnabled(false);
            hireDate_panel.add(hireDate_text);
            inputPanel.add(hireDate_text);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JPanel lastOr_panel = new JPanel();
            JLabel lastOr_label = new JLabel("Ориен. гор.дела | Last Safety Orientation: ");
            lastOr_panel.add(lastOr_label);
            lastOr_panel.setBackground(Color.WHITE);
            lastOrientLabels.add(lastOr_panel);

            lastOr_text = new JTextField();
            lastOr_text.setEnabled(false);
            lastOr_panel.add(lastOr_text);
            lastOrientTexts.add(lastOr_text);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JPanel firstAid_panel = new JPanel();
            JLabel firstAid_label = new JLabel("9001 Первая Помощь | First Aid: ");
            firstAid_panel.add(firstAid_label);
            firstAid_panel.setBackground(Color.WHITE);
            lastOrientLabels.add(firstAid_panel);

            firstAid_text = new JTextField();
            firstAid_text.setEnabled(false);
            firstAid_panel.add(firstAid_text);
            lastOrientTexts.add(firstAid_text);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JPanel pdcs_panel = new JPanel();
            JLabel pdcs_label = new JLabel("7201 - ПДСЧ, ПСП, СУОТ: ");
            pdcs_panel.add(pdcs_label);
            pdcs_panel.setBackground(Color.WHITE);
            lastOrientLabels.add(pdcs_panel);

            pdcs_text = new JTextField();
            pdcs_text.setEnabled(false);
            pdcs_panel.add(pdcs_text);
            lastOrientTexts.add(pdcs_text);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JPanel location_panel = new JPanel();
            JLabel location_label = new JLabel("Местоположение | Location: ");
            location_panel.add(location_label);
            location_panel.setBackground(Color.WHITE);
            lastOrientLabels.add(location_panel);

            String[] cities = new String[]{"", "Бишкек", "Балыкчы", "Каракол"};
            location_box = new JComboBox(cities);
            location_box.setEnabled(false);
            location_panel.add(location_box);
            lastOrientTexts.add(location_box);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JPanel buttons_panel = new JPanel(new GridLayout());
            buttons_panel.setBackground(Color.WHITE);
            buttons_panel.setBounds(480, 580, 400, 70);
            buttons_panel.setLayout(new GridLayout(1, 5));
            buttons_panel.setBorder(new TitledBorder(new LineBorder(Color.orange), "Control Panel"));
            this.add(buttons_panel);

            delete_button = new JButton("Delete");
            delete_button.setForeground(Color.RED);
            delete_button.setEnabled(false);
            buttons_panel.add(delete_button);

            edit_button = new JButton("Edit");
            edit_button.setForeground(Color.BLUE);
            edit_button.setEnabled(false);
            buttons_panel.add(edit_button);
            edit_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    searchButton.setEnabled(false);
                    add_button.setEnabled(false);
                    edit_button.setEnabled(false);
                    delete_button.setEnabled(false);
                    tableID_label.setForeground(Color.BLACK);
                }
            });

            add_button = new JButton("New");
            add_button.setForeground(Color.GREEN);
            buttons_panel.add(add_button);
            add_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    edit_button.setEnabled(false);
                    delete_button.setEnabled(false);
                    add_button.setEnabled(false);

                    tableID_text.setText("");

                    nameEng_text.setEnabled(true);
                    nameEng_text.setText("");

                    nameRus_text.setEnabled(true);
                    nameRus_text.setText("");

                    reportsTo_text.setEnabled(true);
                    reportsTo_text.setText("");

                    departmentEng_box.setEnabled(true);
                    departmentRus_box.setEnabled(true);

                    positionEng_box.setEnabled(true);
                    positionRus_box.setEnabled(true);

                    shiftEng_box.setEnabled(true);
                    shiftRus_box.setEnabled(true);

                    jobClass_text.setEnabled(true);
                    jobClass_text.setText("");

                    jobLevel_text.setEnabled(true);
                    jobLevel_text.setText("");

                    hireDate_text.setEnabled(true);
                    hireDate_text.setText("");

                    lastOr_text.setEnabled(true);
                    lastOr_text.setText("");

                    firstAid_text.setEnabled(true);
                    firstAid_text.setText("");

                    pdcs_text.setEnabled(true);
                    pdcs_text.setText("");

                    location_box.setEnabled(true);

                    searchButton.setEnabled(false);
                    tableID_label.setForeground(Color.BLACK);
                }
            });

            save_button = new JButton("Save");
            save_button.setForeground(Color.BLACK);
            save_button.setEnabled(false);
            buttons_panel.add(save_button);

            cancel_button = new JButton("Cancel");
            cancel_button.setForeground(Color.BLACK);
            buttons_panel.add(cancel_button);
            cancel_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tableID_label.setForeground(Color.RED);

                    location_box.setEnabled(false);
                    save_button.setEnabled(false);
                    edit_button.setEnabled(false);

                    add_button.setEnabled(true);
                    searchButton.setEnabled(true);

                    tableID_text.setText("");

                    nameEng_text.setEnabled(false);
                    nameEng_text.setText("");

                    nameRus_text.setEnabled(false);
                    nameRus_text.setText("");

                    reportsTo_text.setEnabled(false);
                    reportsTo_text.setText("");

                    departmentEng_box.setEnabled(false);
                    departmentEng_box.setSelectedIndex(0);

                    departmentRus_box.setEnabled(false);
                    departmentRus_box.setSelectedIndex(0);

                    positionEng_box.setEnabled(false);
                    positionEng_box.setSelectedIndex(0);

                    positionRus_box.setEnabled(false);
                    positionRus_box.setSelectedIndex(0);

                    shiftEng_box.setEnabled(false);
                    shiftEng_box.setSelectedIndex(0);

                    shiftRus_box.setEnabled(false);
                    shiftRus_box.setSelectedIndex(0);

                    jobClass_text.setEnabled(false);
                    jobClass_text.setText("");

                    jobLevel_text.setEnabled(false);
                    jobLevel_text.setText("");

                    hireDate_text.setEnabled(false);
                    hireDate_text.setText("");

                    lastOr_text.setEnabled(false);
                    lastOr_text.setText("");

                    firstAid_text.setEnabled(false);
                    firstAid_text.setText("");

                    pdcs_text.setEnabled(false);
                    pdcs_text.setText("");
                }
            });

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            JButton exit_button = new JButton("Exit | Выход");
            exit_button.setBounds(730, 60, 150, 30);
            exit_button.setForeground(Color.RED);
            add(exit_button);
            exit_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    card.show(cardPane,"Enter Data");
                }
            });
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g); // paint children

            g.setColor(Color.WHITE);
            g.fillRect(0, 0, 900, 700);

            g.setColor(Color.ORANGE);
            g.fillRect(0, 0, 900, 100);

            g.drawImage(logo_image, 0, 0, 150, 100, this);
        }
    }

    public static class OperationsDailyPanel extends JPanel{

        private BufferedImage logo_image;

        private JLabel tableID_label;

        private JTextField tableID_text;
        private JTextField nameRus_text;

        private JButton searchButton;

        public OperationsDailyPanel(){

            try {
                logo_image = ImageIO.read(new File("textures/logo/kumtor_logo.jpg"));
            } catch (IOException ex) {

            }


            setLayout(null);

            JLabel titleEng = new JLabel("<html><big>Mine Operations Daily Time Form</big><br /> Ввод ежедневной информации</html>");
            titleEng.setBounds(160, 0, 600, 100);
            titleEng.setBackground(Color.WHITE);
            titleEng.setForeground(Color.WHITE);
            titleEng.setFont(new Font("Kumtor", Font.BOLD, 20));
            this.add(titleEng);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            JPanel searchEmployee_panel = new JPanel();
            searchEmployee_panel.setBackground(Color.white);
            searchEmployee_panel.setLayout(new BoxLayout(searchEmployee_panel, BoxLayout.X_AXIS));
            searchEmployee_panel.setBorder(new TitledBorder(new LineBorder(Color.orange), "Search Employee"));
            searchEmployee_panel.setBounds(20, 120, 450, 50);
            this.add(searchEmployee_panel);

            JPanel tableID_panel = new JPanel();
            tableID_label = new JLabel("Табельный номер | Employee ID: ");
            tableID_label.setForeground(Color.RED);
            tableID_panel.add(tableID_label);
            tableID_panel.setBackground(Color.WHITE);
            searchEmployee_panel.add(tableID_label);

            tableID_text = new JTextField(6);
            ((AbstractDocument) tableID_text.getDocument()).setDocumentFilter(new DocumentFilter() {
                final Pattern regEx = Pattern.compile("\\d*");

                @Override
                public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                    Matcher matcher = regEx.matcher(text);
                    if (!matcher.matches()) {
                        return;
                    }
                    super.replace(fb, offset, length, text, attrs);
                }
            });

            tableID_text.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1, true));
            tableID_panel.add(tableID_text);
            searchEmployee_panel.add(tableID_text);

            searchButton = new JButton("Search");
            searchButton.setForeground(Color.RED);
            tableID_panel.add(searchButton);
            searchEmployee_panel.add(searchButton);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JPanel dayHoursInfoPanel = new JPanel();
            dayHoursInfoPanel.setBackground(Color.WHITE);
            dayHoursInfoPanel.setBounds(20, 370, 450, 180);
            dayHoursInfoPanel.setLayout(new BoxLayout(dayHoursInfoPanel, BoxLayout.X_AXIS));
            dayHoursInfoPanel.setBorder(new TitledBorder(new LineBorder(Color.orange), "Day Hours | Ежед. часы"));


            this.add(dayHoursInfoPanel);

            JPanel dayHoursLabels = new JPanel();
            JPanel dayHoursTexts = new JPanel();

            dayHoursLabels.setLayout(new BoxLayout(dayHoursLabels, BoxLayout.Y_AXIS));
            dayHoursLabels.setBackground(Color.WHITE);
            dayHoursInfoPanel.add(dayHoursLabels);

            dayHoursTexts.setLayout(new BoxLayout(dayHoursTexts, BoxLayout.Y_AXIS));
            dayHoursTexts.setBackground(Color.WHITE);
            dayHoursInfoPanel.add(dayHoursTexts);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JPanel employeeInfo_panel = new JPanel();
            employeeInfo_panel.setBackground(Color.white);
            employeeInfo_panel.setLayout(new BoxLayout(employeeInfo_panel, BoxLayout.X_AXIS));
            employeeInfo_panel.setBorder(new TitledBorder(new LineBorder(Color.orange), "Personal Information"));
            employeeInfo_panel.setBounds(20, 180, 450, 160);
            this.add(employeeInfo_panel);

            JPanel infoLabels = new JPanel();
            JPanel inputPanel = new JPanel();

            infoLabels.setLayout(new BoxLayout(infoLabels, BoxLayout.Y_AXIS));
            infoLabels.setBackground(Color.WHITE);
            employeeInfo_panel.add(infoLabels);

            inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
            inputPanel.setBackground(Color.WHITE);
            employeeInfo_panel.add(inputPanel);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JPanel nameEng_panel = new JPanel();
            JLabel nameEng_label = new JLabel("Full Name: ");
            nameEng_panel.add(nameEng_label);
            nameEng_panel.setBackground(Color.WHITE);
            infoLabels.add(nameEng_panel);

            JTextField nameEng_text = new JTextField();
            nameEng_text.setEnabled(false);
            nameEng_panel.add(nameEng_text);
            inputPanel.add(nameEng_text);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


            JPanel nameRus_panel = new JPanel();
            JLabel nameRus_label = new JLabel("Имя и Фамилия: ");
            nameRus_panel.add(nameRus_label);
            nameRus_panel.setBackground(Color.WHITE);
            infoLabels.add(nameRus_panel);

            nameRus_text = new JTextField();
            nameRus_text.setEnabled(false);
            nameRus_panel.add(nameRus_text);
            inputPanel.add(nameRus_text);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JPanel date_panel = new JPanel();
            JLabel date_label = new JLabel("Date | Дата:");
            date_panel.add(date_label);
            date_panel.setBackground(Color.WHITE);
            infoLabels.add(date_panel);

            JTextField date_text = new JTextField();
            date_text.setEnabled(false);
            date_panel.add(date_text);
            inputPanel.add(date_text);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JPanel course_panel = new JPanel();
            JLabel course_label = new JLabel("Course | Курс: ");
            course_panel.add(course_label);
            course_panel.setBackground(Color.WHITE);
            infoLabels.add(course_panel);

            JTextField course_text = new JTextField();
            course_text.setEnabled(false);
            course_panel.add(course_text);
            inputPanel.add(course_text);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JPanel instructor_panel = new JPanel();
            JLabel instructor_label = new JLabel("Instructor | Инструктор: ");
            instructor_panel.add(instructor_label);
            instructor_panel.setBackground(Color.WHITE);
            infoLabels.add(instructor_panel);

            JTextField instructor_text = new JTextField();
            instructor_text.setEnabled(false);
            instructor_panel.add(instructor_text);
            inputPanel.add(instructor_text);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JPanel expHours_panel = new JPanel();
            JLabel expHours_label = new JLabel("Exp Hours :");
            expHours_panel.add(expHours_label);
            expHours_panel.setBackground(Color.WHITE);
            dayHoursLabels.add(expHours_panel);

            JTextField expHours_text = new JTextField();
            expHours_text.setEnabled(false);
            expHours_panel.add(expHours_text);
            dayHoursTexts.add(expHours_text);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JPanel pracHours_panel = new JPanel();
            JLabel pracHours_label = new JLabel("Practical Hours | Прак. часы: ");
            pracHours_panel.add(pracHours_label);
            pracHours_panel.setBackground(Color.WHITE);
            dayHoursLabels.add(pracHours_panel);

            JTextField pracHours_text = new JTextField();
            pracHours_text.setEnabled(false);
            pracHours_panel.add(pracHours_text);
            dayHoursTexts.add(pracHours_text);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JPanel fieldHours_panel = new JPanel();
            JLabel fieldHours_label = new JLabel("Field Hours | С тренером: ");
            fieldHours_panel.add(fieldHours_label);
            fieldHours_panel.setBackground(Color.WHITE);
            dayHoursLabels.add(fieldHours_panel);

            JTextField fieldHours_text = new JTextField();
            fieldHours_text.setEnabled(false);
            fieldHours_panel.add(fieldHours_text);
            dayHoursTexts.add(fieldHours_text);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JPanel theoryHours_panel = new JPanel();
            JLabel theoryHours_label = new JLabel("Theory Hours | Теория: ");
            theoryHours_panel.add(theoryHours_label);
            theoryHours_panel.setBackground(Color.WHITE);
            dayHoursLabels.add(theoryHours_panel);

            JTextField theoryHours_text = new JTextField();
            theoryHours_text.setEnabled(false);
            theoryHours_panel.add(theoryHours_text);
            dayHoursTexts.add(theoryHours_text);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JPanel mark_panel = new JPanel();
            JLabel mark_label = new JLabel("Mark | Оценка: ");
            mark_panel.add(mark_label);
            mark_panel.setBackground(Color.WHITE);
            dayHoursLabels.add(mark_panel);

            JTextField mark_text = new JTextField();
            mark_text.setEnabled(false);
            mark_panel.add(mark_text);
            dayHoursTexts.add(mark_text);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JPanel dayTotal_panel = new JPanel();
            JLabel dayTotal_label = new JLabel("Day Total | Кол. дней: ");
            dayTotal_panel.add(dayTotal_label);
            dayTotal_panel.setBackground(Color.WHITE);
            dayHoursLabels.add(dayTotal_panel);

            JTextField dayTotal_text = new JTextField();
            dayTotal_text.setEnabled(false);
            dayTotal_panel.add(dayTotal_text);
            dayHoursTexts.add(dayTotal_text);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JButton exit_button = new JButton("Exit | Выход");
            exit_button.setBounds(730, 60, 150, 30);
            exit_button.setForeground(Color.RED);
            add(exit_button);
            exit_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    card.show(cardPane,"Enter Data");
                }
            });
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g); // paint children

            g.setColor(Color.WHITE);
            g.fillRect(0, 0, 900, 700);

            g.setColor(Color.ORANGE);
            g.fillRect(0, 0, 900, 100);

            g.drawImage(logo_image, 0, 0, 150, 100, this);
        }

    }
}
