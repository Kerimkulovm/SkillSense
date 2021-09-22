import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EmployeeInfo extends JPanel {

    private final JLabel tableID_label;

    private final JTextField nameEng_text,
            nameRus_text,
            reportsTo_text,
            jobClass_text,
            jobLevel_text,
            hireDate_text,
            lastOr_text,
            firstAid_text,
            pdcs_text,
            tableID_text;

    private final JButton searchButton,
            add_button,
            save_button,
            edit_button,
            cancel_button,
            delete_button;

    private final JComboBox location_box,
            departmentEng_box,
            departmentRus_box,
            shiftEng_box,
            shiftRus_box,
            positionEng_box,
            positionRus_box;

    private BufferedImage logo_image;

    public EmployeeInfo()
    {
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
        employeeInfo_panel.setBounds(20, 175, 450, 350);
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
        lastOrientationInfoPanel.setBounds(20, 530, 450, 130);
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
        buttons_panel.setBounds(480, 590, 400, 70);
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
                save_button.setEnabled(true);

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
        save_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableID_text.getText().equals("") || nameRus_text.getText().equals("") || nameEng_text.getText().equals("")){
                    JOptionPane.showMessageDialog(MineOperations.cardPane,"Пожалуйста введите данные сотрудника");
                }
            }
        });

        cancel_button = new JButton("Cancel");
        cancel_button.setForeground(Color.BLACK);
        buttons_panel.add(cancel_button);
        cancel_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableID_label.setForeground(Color.RED);

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

                location_box.setEnabled(false);
                location_box.setSelectedIndex(0);

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
                MineOperations.card.show(MineOperations.cardPane,"Enter Data");
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
