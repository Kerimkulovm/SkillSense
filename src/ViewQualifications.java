import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;

public class ViewQualifications  extends JPanel{

    private JButton search_button;

    private JTextField tableID_text,
    nameRus_text;

    private JLabel tableID_label;

    private BufferedImage logo_image;

    private JComboBox departmentRus_box,
            shiftRus_box,
            positionRus_box,
            terminatedStatus_box;

    private DefaultComboBoxModel shiftsModelBox;

    public ViewQualifications(){

        try {
            logo_image = ImageIO.read(new File("textures/logo/kumtor_logo.jpg"));
        } catch (IOException ex) {

        }

        setLayout(null);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JLabel titleEng = new JLabel("<html><big>Информация о квалификации сотрудника</big><br /></html>");
        titleEng.setBounds(160, 0, 400, 100);
        titleEng.setFont(new Font("Helvetica", Font.BOLD, 20));
        titleEng.setForeground(Color.WHITE);
        this.add(titleEng);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel searchEmployee_panel = new JPanel();
        searchEmployee_panel.setBackground(Color.white);
        searchEmployee_panel.setLayout(new BoxLayout(searchEmployee_panel, BoxLayout.X_AXIS));
        searchEmployee_panel.setBorder(new TitledBorder(new LineBorder(Color.orange), "Поиск сотрудника"));
        searchEmployee_panel.setBounds(20, 120, 450, 50);
        this.add(searchEmployee_panel);

        JPanel tableID_panel = new JPanel();
        tableID_label = new JLabel(" Табельный номер:  ");
        tableID_label.setForeground(Color.RED);
        tableID_panel.setBackground(Color.WHITE);
        tableID_panel.add(tableID_label);
        searchEmployee_panel.add(tableID_label);

        tableID_text = new JTextField();
        tableID_text.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1, true));
        tableID_panel.add(tableID_text);
        searchEmployee_panel.add(tableID_text);

        search_button = new JButton("Поиск");
        search_button.setForeground(Color.RED);
        search_button.setBackground(Color.WHITE);
        tableID_panel.add(search_button);
        searchEmployee_panel.add(search_button);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel employeeInfo_panel = new JPanel();
        employeeInfo_panel.setBackground(Color.white);
        employeeInfo_panel.setLayout(new BoxLayout(employeeInfo_panel, BoxLayout.X_AXIS));
        employeeInfo_panel.setBorder(new TitledBorder(new LineBorder(Color.orange), "Персональная Информация"));
        employeeInfo_panel.setBounds(20, 175, 450, 155);
        this.add(employeeInfo_panel);

        JPanel infoLabels = new JPanel();
        JPanel inputPanel = new JPanel();

        infoLabels.setLayout(new BoxLayout(infoLabels, BoxLayout.Y_AXIS));
        infoLabels.setBackground(Color.WHITE);
        employeeInfo_panel.add(infoLabels);

        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBackground(Color.WHITE);
        employeeInfo_panel.add(inputPanel);

        JPanel photoPanel = new JPanel();
        photoPanel.setBackground(Color.WHITE);
        photoPanel.setBounds(480, 120, 210, 260);
        photoPanel.setLayout(new BorderLayout());
        photoPanel.setBorder(new TitledBorder(new LineBorder(Color.orange), "Фото"));
        this.add(photoPanel);

        JPanel transportAccessPanel = new JPanel();
        transportAccessPanel.setBackground(Color.WHITE);
        transportAccessPanel.setBounds(20, 335,450,310);
        transportAccessPanel.setLayout(new BorderLayout());
        transportAccessPanel.setBorder(new LineBorder(Color.orange));
        this.add(transportAccessPanel);

        JPanel equipmentPanel = new JPanel();
        equipmentPanel.setBackground(Color.WHITE);
        equipmentPanel.setBounds(480, 390, 210, 200);
        equipmentPanel.setLayout(new BorderLayout());
        equipmentPanel.setBorder(new LineBorder(Color.orange));
        this.add(equipmentPanel);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel nameRus_panel = new JPanel();
        JLabel nameRus_label = new JLabel("Имя и Фамилия: ");
        nameRus_panel.add(nameRus_label);
        nameRus_panel.setBackground(Color.WHITE);
        nameRus_panel.setForeground(Color.BLACK);
        infoLabels.add(nameRus_panel);

        nameRus_text = new JTextField();
        nameRus_text.setEnabled(false);
        nameRus_text.setForeground(Color.BLACK);
        nameRus_text.setDisabledTextColor(Color.BLACK);
        nameRus_panel.add(nameRus_text);
        inputPanel.add(nameRus_text);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel positionRus_panel = new JPanel();
        JLabel positionRus_label = new JLabel("Должность: ");
        positionRus_panel.add(positionRus_label);
        positionRus_panel.setBackground(Color.WHITE);
        infoLabels.add(positionRus_panel);

        positionRus_box = new JComboBox();
        positionRus_box.setBackground(Color.WHITE);
        positionRus_box.setEnabled(false);
        try {

            String positionRus_query = "SELECT * FROM dbo.EnTitles";
            Statement positionRus_statement = MineOperations.conn.createStatement();
            ResultSet positionRus_result = positionRus_statement.executeQuery(positionRus_query);

            while(positionRus_result.next())
            {
                String addPositionRusItem = positionRus_result.getString("RusTitle");
                positionRus_box.addItem(addPositionRusItem);
            }
        }
        catch (SQLException ignored){

        }
        positionRus_panel.add(positionRus_box);
        inputPanel.add(positionRus_box);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel departmentRus_panel = new JPanel();
        JLabel departmentRus_label = new JLabel("Отдел: ");
        departmentRus_panel.add(departmentRus_label);
        departmentRus_panel.setBackground(Color.WHITE);
        infoLabels.add(departmentRus_panel);

        departmentRus_box = new JComboBox();
        departmentRus_box.setBackground(Color.WHITE);
        departmentRus_box.setEnabled(false);

        try{

            String departmentRus_query = "SELECT * FROM dbo.Departments";
            Statement departmentRus_statement = MineOperations.conn.createStatement();
            ResultSet departmentRus_result = departmentRus_statement.executeQuery((departmentRus_query));

            while(departmentRus_result.next()){
                String addDepartmentRus = departmentRus_result.getString("DepartmentNameRu");
                departmentRus_box.addItem(addDepartmentRus);
            }
        }

        catch (SQLException ex){
            ex.printStackTrace();
        }

        departmentRus_panel.add(departmentRus_box);
        inputPanel.add(departmentRus_box);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel shiftEng_panel = new JPanel();
        JLabel shift_label = new JLabel("Смена: ");
        shiftEng_panel.add(shift_label);
        shiftEng_panel.setBackground(Color.WHITE);
        infoLabels.add(shiftEng_panel);

        String[] shifts = new String[]{"Нет данных","A   Crew","B   Crew","C   Crew","D   Crew"};
        shiftsModelBox = new DefaultComboBoxModel(shifts);
        shiftRus_box = new JComboBox(shiftsModelBox);
        shiftRus_box.setBackground(Color.WHITE);
        shiftRus_box.setEnabled(false);
        shiftEng_panel.add(shiftRus_box);
        inputPanel.add(shiftRus_box);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel terminated_panel = new JPanel();
        JLabel terminated_label = new JLabel("Статус: ");
        terminated_panel.add(terminated_label);
        terminated_panel.setBackground(Color.WHITE);
        infoLabels.add(terminated_panel);

        String[] status = new String[]{"Работает","Уволен"};
        terminatedStatus_box = new JComboBox(status);
        terminatedStatus_box.setBackground(Color.WHITE);
        terminatedStatus_box.setEnabled(false);
        terminated_panel.add(terminatedStatus_box);
        inputPanel.add(terminatedStatus_box);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //Initialize table
        JTable transportQualifications_table = new JTable(new TrucksTableModel());
        transportQualifications_table.setRowHeight(20);
        transportQualifications_table.setBackground(Color.WHITE);

        //Set Column Width
        TableColumn column = null;
        for (int i = 0; i < 4; i++) {
            column = transportQualifications_table.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(100); //third column is bigger
            } else {
                column.setPreferredWidth(50);
            }
        }

        //Cell Selection
        String.format("Lead Selection: %d, %d. ",
                transportQualifications_table.getSelectionModel().getLeadSelectionIndex(),
                transportQualifications_table.getColumnModel().getSelectionModel().getLeadSelectionIndex());

        JScrollPane tQ_sctollPane = new JScrollPane(transportQualifications_table);
        transportQualifications_table.setFillsViewportHeight(true);
        transportAccessPanel.add(tQ_sctollPane);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

    protected void paintComponent(Graphics g){
        super.paintComponent(g); // paint children

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 900, 750);

        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, 900, 100);

        g.drawImage(logo_image, 0, 0, 150, 100, this);
    }

}
//String[] mineTransport_arr = new String[]{"CAT-785C", "CAT-789C", "CAT-789D" , "CAT D10R/T",
//        "CAT 834H/K", "CAT 16 H/M", "CAT 24M", "CAT 330", "CAT 374", "CAT 992K", "CAT M320 D2",
//        "CAT 998K", "Liebherr R 9350", "Hitachi-3600-6", "ДСК","Трал CAT 777B", "Liebherr R 9350-411"};

class TrucksTableModel extends AbstractTableModel {
    private String[] columnNames = {"Транспорт","Учеба","Одобрен","Квалифицирован"};
    private Object[][] data = {{"CAT-785C", new Boolean(false), new Boolean(false), new Boolean(false)},
            {"CAT-789C", new Boolean(false), new Boolean(false), new Boolean(false) },
            {"CAT-789D", new Boolean(false), new Boolean(false), new Boolean(false)},
            {"CAT D10R/T", new Boolean(false), new Boolean(false), new Boolean(false)},
            {"CAT 834H/K", new Boolean(false), new Boolean(false), new Boolean(false)},
            {"CAT 16 H/M", new Boolean(false), new Boolean(false), new Boolean(false)},
            {"CAT 24M", new Boolean(false), new Boolean(false), new Boolean(false)},
            {"CAT 330", new Boolean(false), new Boolean(false), new Boolean(false)},
            {"CAT 374", new Boolean(false), new Boolean(false), new Boolean(false)},
            {"CAT 992K", new Boolean(false), new Boolean(false), new Boolean(false)},
            {"CAT M320 D2", new Boolean(false), new Boolean(false), new Boolean(false)},
            {"CAT 998K", new Boolean(false), new Boolean(false), new Boolean(false)},
            {"Liebherr R 9350", new Boolean(false), new Boolean(false), new Boolean(false)},
            {"Hitachi-3600-6", new Boolean(false), new Boolean(false), new Boolean(false)},
            {"ДСК", new Boolean(false), new Boolean(false), new Boolean(false)},
            {"Трал CAT 777B", new Boolean(false), new Boolean(false), new Boolean(false)},
            {"Liebherr R 9350-411", new Boolean(false), new Boolean(false), new Boolean(false)}};

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        if (col < 1) {
            return false;
        } else {
            return true;
        }
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }

    static class DateRenderer extends DefaultTableCellRenderer {
        DateFormat formatter;
        public DateRenderer() { super(); }

        public void setValue(Object value) {
            if (formatter==null) {
                formatter = DateFormat.getDateInstance();
            }
            setText((value == null) ? "" : formatter.format(value));
        }
    }
}

