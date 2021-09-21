/*
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class EmployeeInfoPanel extends JPanel implements ActionListener {

    private JLabel tableID_label;

    private final JTextField nameEng_text;
    private final JTextField nameRus_text;
    private final JTextField positionEng_text;
    private final JTextField positionRus_text;
    private final JTextField reportsTo_text;
    private final JTextField department_text;
    private final JTextField shift_text;
    private final JTextField jobClass_text;
    private final JTextField jobLevel_text;
    private final JTextField hireDate_text;
    private final JTextField lastOr_text;
    private final JTextField firstAid_text;
    private final JTextField pdcs_text;
    private final JTextField tableID_text;

    private final JButton searchButton;
    private JButton add_button;
    private JButton save_button;
    private JButton edit_button;
    private final JButton cancel_button;
    private final JButton delete_button;

    private final JComboBox location_box;

    private BufferedImage logo_image;

    public EmployeeInfoPanel() {

        try{
            logo_image = ImageIO.read(new File("textures/logo/kumtor_logo.jpg"));
        } catch (IOException ex) {

        }

        setLayout(null);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel titleEng = new JLabel("Employee Information");
        titleEng.setBounds(160,0,400,100);
        titleEng.setBackground(Color.WHITE);
        titleEng.setForeground(Color.WHITE);
        titleEng.setFont(new Font("Kumtor",Font.BOLD,30));
        this.add(titleEng);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        JPanel searchEmployee_panel = new JPanel();
        searchEmployee_panel.setBackground(Color.white);
        searchEmployee_panel.setLayout(new BoxLayout(searchEmployee_panel, BoxLayout.X_AXIS));
        searchEmployee_panel.setBorder(new TitledBorder(new LineBorder(Color.orange),"Search Employee"));
        searchEmployee_panel.setBounds(20,120, 450,50);
        this.add(searchEmployee_panel);

        JPanel tableID_panel = new JPanel();
        tableID_label = new JLabel("Табельный номер | Employee ID: ");
        tableID_label.setForeground(Color.RED);
        tableID_panel.add(tableID_label);
        tableID_panel.setBackground(Color.WHITE);
        searchEmployee_panel.add(tableID_label);

        tableID_text = new JTextField(6);
        ((AbstractDocument)tableID_text.getDocument()).setDocumentFilter(new DocumentFilter(){
            final Pattern regEx = Pattern.compile("\\d*");
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                Matcher matcher = regEx.matcher(text);
                if(!matcher.matches()){
                    return;
                }
                super.replace(fb, offset, length, text, attrs);
            }
        });
        tableID_text.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1,true));
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
        employeeInfo_panel.setBorder(new TitledBorder(new LineBorder(Color.orange),"Personal Information"));
        employeeInfo_panel.setBounds(20,180, 450,300);
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
        lastOrientationInfoPanel.setBounds(20,490,450,130);
        lastOrientationInfoPanel.setLayout(new BoxLayout(lastOrientationInfoPanel, BoxLayout.X_AXIS));
        lastOrientationInfoPanel.setBorder(new TitledBorder(new LineBorder(Color.orange),"Last Orientation Information"));
        this.add(lastOrientationInfoPanel);

        JPanel lastOrientLabels = new JPanel();
        JPanel lastOrientTexts = new JPanel();

        lastOrientLabels.setLayout(new BoxLayout(lastOrientLabels, BoxLayout.Y_AXIS));
        lastOrientLabels.setBackground(Color.WHITE);
        lastOrientationInfoPanel.add(lastOrientLabels);

        lastOrientTexts.setLayout(new BoxLayout(lastOrientTexts, BoxLayout.Y_AXIS));
        lastOrientTexts.setBackground(Color.WHITE);
        lastOrientationInfoPanel.add(lastOrientTexts);
        //

        JPanel photoPanel = new JPanel();
        photoPanel.setBackground(Color.WHITE);
        photoPanel.setBounds(480,120,400,450);
        photoPanel.setLayout(new BoxLayout(photoPanel, BoxLayout.X_AXIS));
        photoPanel.setBorder(new TitledBorder(new LineBorder(Color.orange),"Photo"));
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

        positionEng_text = new JTextField();
        positionEng_text.setEnabled(false);
        positionEng_panel.add(positionEng_text);
        inputPanel.add(positionEng_text);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel positionRus_panel = new JPanel();
        JLabel positionRus_label = new JLabel("Должность: ");
        positionRus_panel.add(positionRus_label);
        positionRus_panel.setBackground(Color.WHITE);
        infoLabels.add(positionRus_panel);

        positionRus_text = new JTextField();
        positionRus_text.setEnabled(false);
        positionRus_panel.add(positionRus_text);
        inputPanel.add(positionRus_text);

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

        JPanel department_panel = new JPanel();
        JLabel department_label = new JLabel("Отдел | Department: ");
        department_panel.add(department_label);
        department_panel.setBackground(Color.WHITE);
        infoLabels.add(department_panel);

        department_text = new JTextField();
        department_text.setEnabled(false);
        department_panel.add(department_text);
        inputPanel.add(department_text);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel shift_panel = new JPanel();
        JLabel shift_label = new JLabel("Смена | Shift: ");
        shift_panel.add(shift_label);
        shift_panel.setBackground(Color.WHITE);
        infoLabels.add(shift_panel);

        shift_text = new JTextField();
        shift_text.setEnabled(false);
        shift_panel.add(shift_text);
        inputPanel.add(shift_text);

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

        String[] cities = new String[]{"","Бишкек", "Балыкчы","Каракол"};
        location_box = new JComboBox(cities);
        location_box.setEnabled(false);
        location_panel.add(location_box);
        lastOrientTexts.add(location_box);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel buttons_panel = new JPanel(new GridLayout());
        buttons_panel.setBackground(Color.WHITE);
        buttons_panel.setBounds(480,570,400,50);
        buttons_panel.setLayout(new GridLayout(1,5));
        buttons_panel.setBorder(new TitledBorder(new LineBorder(Color.orange),"Control Panel"));
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

                positionEng_text.setEnabled(true);
                positionEng_text.setText("");

                positionRus_text.setEnabled(true);
                positionRus_text.setText("");

                reportsTo_text.setEnabled(true);
                reportsTo_text.setText("");

                department_text.setEnabled(true);
                department_text.setText("");

                shift_text.setEnabled(true);
                shift_text.setText("");

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

                positionEng_text.setEnabled(false);
                positionEng_text.setText("");

                positionRus_text.setEnabled(false);
                positionRus_text.setText("");

                reportsTo_text.setEnabled(false);
                reportsTo_text.setText("");

                department_text.setEnabled(false);
                department_text.setText("");

                shift_text.setEnabled(false);
                shift_text.setText("");

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
        exit_button.setBounds(730, 60, 150,30);
        exit_button.setForeground(Color.RED);
        add(exit_button);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // paint children

        g.setColor(Color.WHITE);
        g.fillRect(0,0,900,700);

        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, 900, 100);

        g.drawImage(logo_image,0,0,150,100,this);
    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}*/