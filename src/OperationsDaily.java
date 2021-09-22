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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OperationsDaily extends JPanel {

    private BufferedImage logo_image;

    private JLabel tableID_label;

    private JTextField tableID_text;
    private JTextField nameRus_text;

    private JButton searchButton;

    public OperationsDaily()
    {
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

        JPanel employeeInfo_panel = new JPanel();
        employeeInfo_panel.setBackground(Color.white);
        employeeInfo_panel.setLayout(new BoxLayout(employeeInfo_panel, BoxLayout.X_AXIS));
        employeeInfo_panel.setBorder(new TitledBorder(new LineBorder(Color.orange), "Personal Information"));
        employeeInfo_panel.setBounds(20, 180, 450, 210);
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

        JPanel dayHoursInfoPanel = new JPanel();
        dayHoursInfoPanel.setBackground(Color.WHITE);
        dayHoursInfoPanel.setBounds(20, 400, 450, 180);
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

        JPanel crew_panel = new JPanel();
        JLabel crew_label = new JLabel("Department | Отдел: ");
        crew_panel.add(crew_label);
        crew_panel.setBackground(Color.WHITE);
        infoLabels.add(crew_panel);

        JTextField crew_text = new JTextField();
        crew_text.setEnabled(false);
        crew_panel.add(crew_text);
        inputPanel.add(crew_text);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel shift_panel = new JPanel();
        JLabel shift_label = new JLabel("Shift | Смена :");
        shift_panel.add(shift_label);
        shift_panel.setBackground(Color.WHITE);
        infoLabels.add(shift_panel);

        JTextField shift_text = new JTextField();
        shift_text.setEnabled(false);
        shift_panel.add(shift_text);
        inputPanel.add(shift_text);

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
