import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

public class OperationsDaily extends JPanel {

    private final JLabel tableID_label;

    private final JTextField
            nameRus_text,
            theoryHours_text,
            practHours_text,
            fieldHours_text,
            expHours_text,
            tableID_text;

    private final JButton
            search_button,
            surnameSearch_button,
            save_button,
            cancel_button;

    public static JComboBox
            CourseName_box,
            trainer_box;

    JDatePickerImpl Date_dtp = null;

    private BufferedImage logo_image;
    private JPanel photoPanel;

    private Integer courseId=0;
    private Integer instructorId=0;


    public static DatabaseQueries databaseQueries = new DatabaseQueries();

    public OperationsDaily(){

        try {
            logo_image = ImageIO.read(new File("textures/logo/kumtor_logo.jpg"));
        } catch (IOException ignored) {

        }

        setLayout(null);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel titleEng = new JLabel("<html><big>Ввод ежедневной информации</big></html>");
        titleEng.setBounds(160, 0, 500, 100);
        titleEng.setBackground(Color.WHITE);
        titleEng.setForeground(Color.WHITE);
        titleEng.setFont(new Font("Kumtor", Font.BOLD, 20));
        this.add(titleEng);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JButton exit_button = new JButton("Выход");
        exit_button.setBounds(730, 60, 150, 30);
        exit_button.setBackground(Color.WHITE);
        exit_button.setForeground(Color.RED);
        add(exit_button);
        exit_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MineOperations.card.show(MineOperations.cardPane,"Home Page");
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        JPanel searchEmployee_panel = new JPanel();
        searchEmployee_panel.setBackground(Color.white);
        searchEmployee_panel.setLayout(new BoxLayout(searchEmployee_panel, BoxLayout.X_AXIS));
        searchEmployee_panel.setBorder(new TitledBorder(new LineBorder(Color.orange), "Поиск сотрудника"));
        searchEmployee_panel.setBounds(20, 120, 500, 50);
        this.add(searchEmployee_panel);

        JPanel tableID_panel = new JPanel();
        tableID_label = new JLabel(" Табельный номер:  ");
        tableID_label.setForeground(Color.RED);
        tableID_panel.setBackground(Color.WHITE);
        tableID_panel.add(tableID_label);
        searchEmployee_panel.add(tableID_label);

        surnameSearch_button = new JButton("Ф.И.О.");
        surnameSearch_button.setForeground(Color.RED);
        surnameSearch_button.setBackground(Color.WHITE);
        tableID_panel.add(surnameSearch_button);
        searchEmployee_panel.add(surnameSearch_button);
        surnameSearch_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchBySurname searchBySurname = new SearchBySurname(tableID_text.getText());
                searchBySurname.pack();
                searchBySurname.setVisible(true);
            }
        });

        tableID_text = new JTextField();
        tableID_text.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1, true));
        tableID_panel.add(tableID_text);
        searchEmployee_panel.add(tableID_text);

        search_button = new JButton("Поиск");
        search_button.setForeground(Color.RED);
        search_button.setBackground(Color.WHITE);
        tableID_panel.add(search_button);
        searchEmployee_panel.add(search_button);
        search_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableID_text.getText().equals("") )
                    JOptionPane.showMessageDialog(MineOperations.cardPane,"Пожалуйста, введите табельный номер");
                else {

                    databaseQueries.queryEmployeeData(tableID_text.getText());
                    nameRus_text.setText(databaseQueries.getEmployeeName());
                    enableComboText(trainer_box).setSelectedItem((databaseQueries.getSuperVisorName()));
                    enableComboText(CourseName_box).setSelectedItem(databaseQueries.getJobName());



                    if (databaseQueries.getPhotoLabel() != null){
                        photoPanel.removeAll();
                        photoPanel.add(databaseQueries.getPhotoLabel());
                        revalidate();
                        repaint();
                    } else {
                        photoPanel.removeAll();
                        revalidate();
                        repaint();
                    }

                    //LastDate_dtp.getJFormattedTextField().setText(databaseQueries.getLastSafetyOr());
                    setVisible(true);
                }
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel employeeInfo_panel = new JPanel();
        employeeInfo_panel.setBackground(Color.white);
        employeeInfo_panel.setLayout(new BoxLayout(employeeInfo_panel, BoxLayout.X_AXIS));
        employeeInfo_panel.setBorder(new TitledBorder(new LineBorder(Color.orange), "Информация по курсам"));
        employeeInfo_panel.setBounds(20, 175, 500, 250);
        this.add(employeeInfo_panel);

        JPanel infoLabels = new JPanel();
        JPanel inputPanel = new JPanel();

        infoLabels.setLayout(new BoxLayout(infoLabels, BoxLayout.Y_AXIS));
        infoLabels.setBackground(Color.WHITE);
        employeeInfo_panel.add(infoLabels);

        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBackground(Color.WHITE);
        employeeInfo_panel.add(inputPanel);

        photoPanel = new JPanel();
        photoPanel.setBackground(Color.WHITE);
        photoPanel.setBounds(530, 120, 210, 230);
        photoPanel.setLayout(new BorderLayout());
        photoPanel.setBorder(new TitledBorder(new LineBorder(Color.orange), "Фото"));
        this.add(photoPanel);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel nameRus_panel = new JPanel();
        JLabel nameRus_label = new JLabel("Имя и Фамилия: ");
        nameRus_panel.add(nameRus_label);
        nameRus_panel.setBackground(Color.WHITE);
        nameRus_panel.setForeground(Color.BLACK);
        infoLabels.add(nameRus_panel);

        nameRus_text = new JTextField();
        nameRus_text.setForeground(Color.BLACK);
        nameRus_text.setDisabledTextColor(Color.BLACK);
        nameRus_text.setEnabled(false);
        nameRus_panel.add(nameRus_text);
        inputPanel.add(nameRus_text);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel CourseName_panel = new JPanel();
        JLabel CourseName_label = new JLabel("Курс: ");
        CourseName_panel.add(CourseName_label);
        CourseName_panel.setBackground(Color.WHITE);
        infoLabels.add(CourseName_panel);

        CourseName_box = new JComboBox();
        CourseName_box.setBackground(Color.WHITE);
        CourseName_box = databaseQueries.loadCourseNameBox(CourseName_box);
        CourseName_panel.add(CourseName_box);
        inputPanel.add(CourseName_box);
        CourseName_box.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                Date_dtp.getJFormattedTextField().setText("");
                JComboBox c = (JComboBox) e.getSource();
                DatabaseQueries.Item item = (DatabaseQueries.Item) c.getSelectedItem();
                courseId = item.getId();
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel trainer_panel = new JPanel();
        JLabel trainer_label = new JLabel("Инструктор: ");
        trainer_panel.add(trainer_label);
        trainer_panel.setBackground(Color.WHITE);
        infoLabels.add(trainer_panel);

        trainer_box = new JComboBox();
        trainer_box.setBackground(Color.WHITE);
        trainer_box = databaseQueries.loadTrainerBox(trainer_box);
        trainer_panel.add(trainer_box);
        inputPanel.add(trainer_box);
        trainer_box.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                JComboBox c = (JComboBox) e.getSource();
                DatabaseQueries.Item item = (DatabaseQueries.Item) c.getSelectedItem();
                System.out.println(item.getId() + " : " + item.getDescription());
                instructorId = item.getId();
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        JPanel Date_panel = new JPanel();
        JLabel Date_label = new JLabel("Дата: ");
        Date_panel.add(Date_label);
        Date_panel.setBackground(Color.WHITE);
        infoLabels.add(Date_panel);

        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        Date_dtp = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        Date_panel.add(Date_dtp);
        inputPanel.add(Date_dtp);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel theoryHours_panel = new JPanel();
        JLabel theoryHours_label = new JLabel("Теория: ");
        theoryHours_panel.add(theoryHours_label);
        theoryHours_panel.setBackground(Color.WHITE);
        infoLabels.add(theoryHours_panel);


        theoryHours_text = new JTextField();
        theoryHours_text.setForeground(Color.BLACK);
        theoryHours_text.setDisabledTextColor(Color.BLACK);
        theoryHours_text.setText("0");
        theoryHours_panel.add(theoryHours_text);
        inputPanel.add(theoryHours_text);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel fieldHours_panel = new JPanel();
        JLabel fieldHours_label = new JLabel("Работа с тренером: ");
        fieldHours_panel.add(fieldHours_label);
        fieldHours_panel.setBackground(Color.WHITE);
        infoLabels.add(fieldHours_panel);

        fieldHours_text = new JTextField();
        fieldHours_text.setForeground(Color.BLACK);
        fieldHours_text.setDisabledTextColor(Color.BLACK);
        fieldHours_text.setText("0");
        fieldHours_panel.add(fieldHours_text);
        inputPanel.add(fieldHours_text);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel practHours_panel = new JPanel();
        JLabel practHours_label = new JLabel("Практ. часы: ");
        practHours_panel.add(practHours_label);
        practHours_panel.setBackground(Color.WHITE);
        infoLabels.add(practHours_panel);

        practHours_text = new JTextField();
        practHours_text.setForeground(Color.BLACK);
        practHours_text.setDisabledTextColor(Color.BLACK);
        practHours_text.setText("0");
        practHours_panel.add(practHours_text);
        inputPanel.add(practHours_text);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel expHours_panel = new JPanel();
        JLabel expHours_label = new JLabel("Опыт. часы: ");
        expHours_panel.add(expHours_label);
        expHours_panel.setBackground(Color.WHITE);
        infoLabels.add(expHours_panel);

        expHours_text = new JTextField();
        expHours_text.setForeground(Color.BLACK);
        expHours_text.setDisabledTextColor(Color.BLACK);
        expHours_text.setText("0");
        expHours_panel.add(expHours_text);
        inputPanel.add(expHours_text);


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Панель Управления
        JPanel buttons_panel = new JPanel(new GridLayout());
        buttons_panel.setBackground(Color.WHITE);
        buttons_panel.setBounds(20, 450, 500, 30);
        buttons_panel.setLayout(new GridLayout(1, 2));
        buttons_panel.setBorder(new TitledBorder(new LineBorder(Color.orange)));
        this.add(buttons_panel);



        save_button = new JButton("Сохранить");
        save_button.setBackground(Color.WHITE);
        save_button.setForeground(Color.BLACK);
        save_button.setFont(new Font("Helvetica", Font.BOLD, 10));
        buttons_panel.add(save_button);
        save_button.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {

                if (tableID_text.getText().equals("") || nameRus_text.getText().equals("")) {
                    JOptionPane.showMessageDialog(MineOperations.cardPane, "Пожалуйста, введите данные сотрудника");
                }else if (courseId == 0 || instructorId == 0) {
                    JOptionPane.showMessageDialog(MineOperations.cardPane, "Пожалуйста, заполните поля 'Курс' и 'Инструктор'.");
                }else if ((practHours_text.getText().equals("0") || practHours_text.getText().equals(""))
                        && (theoryHours_text.getText().equals("0") || theoryHours_text.getText().equals(""))
                        && (fieldHours_text.getText().equals("0") || fieldHours_text.getText().equals(""))
                        && (expHours_text.getText().equals("0") || expHours_text.getText().equals(""))) {
                    JOptionPane.showMessageDialog(MineOperations.cardPane, "Пожалуйста, заполните часы корректно.");
                }else if (Date_dtp.getJFormattedTextField().getText().equals("")){
                    JOptionPane.showMessageDialog(MineOperations.cardPane, "Пожалуйста, заполните дату.");
                } else {



                    boolean res = false;
                    res = databaseQueries.saveOperationDaily(tableID_text.getText(), courseId, instructorId, Date_dtp.getJFormattedTextField().getText(),
                            Integer.parseInt(theoryHours_text.getText()), Integer.parseInt(fieldHours_text.getText()),
                            Integer.parseInt(practHours_text.getText()), Integer.parseInt(expHours_text.getText()));

                    if (res) JOptionPane.showMessageDialog(MineOperations.cardPane, "Запись сохранена успешно!");

                    clearFields();
                }
            }
        });

        cancel_button = new JButton("Сброс");
        cancel_button.setBackground(Color.RED);
        cancel_button.setForeground(Color.WHITE);
        cancel_button.setFont(new Font("Helvetica", Font.BOLD, 10));
        buttons_panel.add(cancel_button);
        cancel_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
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

    private JComboBox enableComboText(JComboBox inputCombobox){
        inputCombobox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public void paint(Graphics g) {
                setForeground(Color.BLACK);
                super.paint(g);
            }
        });
        return inputCombobox;
    }



    private void clearFields(){

        surnameSearch_button.setEnabled(true);

        tableID_label.setForeground(Color.RED);
        tableID_text.setText("");
        nameRus_text.setText("");
        theoryHours_text.setText("0");
        practHours_text.setText("0");
        fieldHours_text.setText("0");
        expHours_text.setText("0");
        Date_dtp.getJFormattedTextField().setText("");

        photoPanel.removeAll();
        revalidate();
        repaint();
    }



    private class SearchBySurname extends JFrame{

        private JTable listOfEmployees_table;

        private List<String> employeeNames_list = new ArrayList<>();
        private List<Integer> employeeID_list = new ArrayList<>();

        private JPanel pageTitlePanel, tablePanel, backgroundPanel;

        private JLabel foundEmployees_JLabel;

        private int numOfRows = 1, numOfColumns = 2;

        public SearchBySurname(String surname){

            findSurnames(surname);
            buildFrame();
            createTable();

            this.setPreferredSize(new Dimension(400, 600));
            this.setFocusableWindowState(true);
            this.setAutoRequestFocus(true);
            this.setLocation(900,40);
            this.setLayout(null);
        }

        private void buildFrame(){

            pageTitlePanel = new JPanel();
            pageTitlePanel.setBounds(0,0,400,50);
            pageTitlePanel.setBackground(Color.WHITE);
            pageTitlePanel.setVisible(true);
            this.add(pageTitlePanel);

            foundEmployees_JLabel = new JLabel("Найденные Сотрудники");
            foundEmployees_JLabel.setFont(new Font("Helvetica",Font.BOLD, 20));
            pageTitlePanel.add(foundEmployees_JLabel);

            tablePanel = new JPanel();
            tablePanel.setBackground(Color.WHITE);
            tablePanel.setBounds(25,50,350,500);
            tablePanel.setLayout(new BorderLayout());
            tablePanel.setBorder(new LineBorder(Color.BLACK));
            tablePanel.setVisible(true);
            this.add(tablePanel);

            backgroundPanel = new JPanel();
            backgroundPanel.setBounds(0,0,400,600);
            backgroundPanel.setBackground(Color.WHITE);
            this.add(backgroundPanel);
        }

        private void createTable(){

            DefaultTableModel listOfEmployees_model = new DefaultTableModel(numOfRows ,numOfColumns){
                public boolean isCellEditable(int row, int column){
                    return false;
                }
            };
            DefaultTableCellRenderer centerRederer = new DefaultTableCellRenderer();
            centerRederer.setHorizontalAlignment(JLabel.CENTER);

            listOfEmployees_table = new JTable(listOfEmployees_model);
            listOfEmployees_table.setBorder(new LineBorder(Color.BLACK));
            listOfEmployees_table.setBackground(Color.WHITE);
            listOfEmployees_table.setRowHeight(25);
            listOfEmployees_table.setVisible(true);

            JTableHeader listHeader= listOfEmployees_table.getTableHeader();
            listHeader.setBorder(new LineBorder(Color.BLACK));
            listHeader.setBackground(Color.WHITE);
            listHeader.setFont(new Font("Helvetica", Font.BOLD,12));

            TableColumnModel listOfEmployees_columns = listHeader.getColumnModel();

            TableColumn tabCol0 = listOfEmployees_columns.getColumn(0);
            tabCol0.setHeaderValue("Табельный No");
            tabCol0.setCellRenderer(centerRederer);
            tabCol0.setPreferredWidth(10);

            for (int i = 0; i < numOfRows; i++){
                listOfEmployees_table.setValueAt(employeeID_list.get(i),i,0);
            }

            TableColumn tabCol1 = listOfEmployees_columns.getColumn(1);
            tabCol1.setCellRenderer(centerRederer);
            tabCol1.setHeaderValue("Ф.И.О");

            for (int i = 0; i < numOfRows; i++){
                listOfEmployees_table.setValueAt(employeeNames_list.get(i),i,1);
            }


            listOfEmployees_table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 1){
                        int index1 = listOfEmployees_table.getSelectedRow();//Get the selected row
                        System.out.println(listOfEmployees_table.getValueAt(index1, 0));
                        tableID_text.setText(listOfEmployees_table.getValueAt(index1,0).toString());
                    }
                }
            });

            tablePanel.add(new JScrollPane(listOfEmployees_table));
        }

        private void findSurnames(String surname){

            if (surname == null){
                JOptionPane.showMessageDialog(MineOperations.cardPane,"Пожалуйста введите фамилию или имя");
            } else {
                databaseQueries.findBySurname(surname);
                employeeNames_list = databaseQueries.getListOfSurnames();
                employeeID_list = databaseQueries.getListOfID();
                numOfRows = employeeNames_list.size();
                System.out.println(numOfRows);
            }
        }
    }
}
