import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class SRTClassifier extends JPanel {

    private BufferedImage logo_image, bg_image;

    private JTable srt_table;
    private DefaultTableModel srt_tableModel;

    public DatabaseQueries databaseQueries = new DatabaseQueries();
    public String srtSelected;
    public String statusSelected;

    public List<String> srt_list = new ArrayList<>();

    public SRTClassifier(){

        try {
            logo_image = ImageIO.read(Objects.requireNonNull(getClass().getResource("resources/logo/Logo2.png")));
            bg_image = ImageIO.read(Objects.requireNonNull(getClass().getResource("resources/logo/bg.jpg")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        setLayout(null);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        JLabel titleEng = new JLabel("<html><big>Управление классификаторами 'Ежегодного Обучения'</big><br /></html>");
        titleEng.setBounds(160, 0, 600, 100);
        titleEng.setFont(Font.getFont("Lena"));
        titleEng.setForeground(Color.BLACK);
        this.add(titleEng);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        JTable SRT_panel = new JTable();
        SRT_panel.setBackground(Color.WHITE);
        SRT_panel.setBounds(20,120,650,500);
        SRT_panel.setLayout(new BorderLayout());
        this.add(SRT_panel);

        JPanel buttons_panel = new JPanel(new GridLayout(1,2,5,0));
        buttons_panel.setBackground(Color.WHITE);
        buttons_panel.setBounds(20,635,300,30);
        this.add(buttons_panel);

        JButton exit_button = new JButton("Выход");
        exit_button.setBounds(720, 60, 150, 30);
        exit_button.setBackground(Color.WHITE);
        exit_button.setForeground(Color.RED);
        exit_button.setFont(Font.getFont("Lena"));
        exit_button.setBorder(new RoundedBorder(10));
        add(exit_button);
        exit_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MineOperations.card.show(MineOperations.cardPane,"Classificatory");
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        buildTable();
        JScrollPane SRT_scrollPane = new JScrollPane(srt_table);
        SRT_scrollPane.setBackground(Color.WHITE);
        SRT_panel.add(SRT_scrollPane);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        if (LoginWin.user.getRoleid() == 1 || LoginWin.user.getRoleid() == 2) {
            JButton edit_button = new JButton("Изменить");
            edit_button.setForeground(Color.BLACK);
            edit_button.setBackground(Color.WHITE);
            edit_button.setFont(Font.getFont("Lena"));
            edit_button.setBorder(new RoundedBorder(10));
            buttons_panel.add(edit_button);
            edit_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (srtSelected == null){
                        JOptionPane.showMessageDialog(MineOperations.cardPane,"Необходимо выбрать обучение");
                    } else {
                        EditSRTFrame editSRTFrame = new EditSRTFrame();
                        editSRTFrame.setVisible(true);
                        editSRTFrame.pack();
                    }
                }
            });

            JButton create_button = new JButton("Создать");
            create_button.setBackground(Color.WHITE);
            create_button.setForeground(Color.BLACK);
            create_button.setBorder(new RoundedBorder(10));
            create_button.setFont(Font.getFont("Lena"));
            buttons_panel.add(create_button);
            create_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AddSRTFrame createSRTFrame = new AddSRTFrame();
                    createSRTFrame.setVisible(true);
                    createSRTFrame.pack();
                }
            });
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    private void buildTable(){

        srt_list = loadSRT(srt_list);

        srt_tableModel = new DefaultTableModel(srt_list.size(),3){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        DefaultTableCellRenderer SRT_cellRenderer = new DefaultTableCellRenderer();
        SRT_cellRenderer.setHorizontalAlignment(JLabel.CENTER);

        srt_table = new JTable(srt_tableModel);
        srt_table.setBorder(new LineBorder(Color.BLACK));
        srt_table.setBackground(Color.WHITE);
        srt_table.setRowHeight(20);

        JTableHeader SRT_header = srt_table.getTableHeader();
        SRT_header.setBorder(new LineBorder(Color.BLACK));
        SRT_header.setBackground(Color.WHITE);
        SRT_header.setFont(Font.getFont("Lena"));

        TableColumnModel SRT_columns = SRT_header.getColumnModel();

        TableColumn tabCol0 = SRT_columns.getColumn(0);
        tabCol0.setHeaderValue("ID SRT");
        tabCol0.setCellRenderer(SRT_cellRenderer);
        tabCol0.setPreferredWidth(50);

        TableColumn tabCol1 = SRT_columns.getColumn(1);
        tabCol1.setHeaderValue("SRT");
        tabCol1.setCellRenderer(SRT_cellRenderer);
        tabCol1.setPreferredWidth(50);

        TableColumn tabCol2 = SRT_columns.getColumn(2);
        tabCol2.setHeaderValue("Статус");
        tabCol2.setCellRenderer(SRT_cellRenderer);
        tabCol2.setPreferredWidth(50);

        for (int i = 0; i <  srt_table.getRowCount(); i++){
            srt_table.setValueAt(getSRTID(srt_list.get(i)),i,0);
            srt_table.setValueAt(srt_list.get(i),i,1);
            srt_table.setValueAt(getSRTActivity(srt_list.get(i)),i,2);
        }

        srt_table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1){
                    srtSelected = (String) srt_table.getValueAt(srt_table.getSelectedRow(),1);
                    statusSelected = (String) srt_table.getValueAt(srt_table.getSelectedRow(),2);

                }
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<String> loadSRT(List<String> inputList){

        inputList.clear();

        try {
            String SRT_query = "SELECT * FROM SafetyNames";
            Statement SRT_statement = MineOperations.conn.createStatement();
            ResultSet SRT_results = SRT_statement.executeQuery(SRT_query);

            if (SRT_results.next()){
                do{
                    String SRTName= SRT_results.getString("RusName");
                    inputList.add(SRTName);
                } while (SRT_results.next());
            }


        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return inputList;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public int getSRTID(String SRTName){

        int SRTID = 0;

        try {
            String SRT_query = "SELECT * FROM SafetyNames WHERE RusName  = N'" + SRTName +"'";
            Statement SRT_statement = MineOperations.conn.createStatement();
            ResultSet SRT_results = SRT_statement.executeQuery(SRT_query);

            if (SRT_results.next()){
                do{
                    int SRTId= SRT_results.getInt("ReviewNo");
                    SRTID = SRTId;
                } while (SRT_results.next());
            }


        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return SRTID;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String getSRTActivity(String SRTName){

        String status = null;
        int statusID = 0;

        try {
            String intsructors_query = "SELECT * FROM SafetyNames WHERE RusName = N'" + SRTName +"'";
            Statement SRT_statement = MineOperations.conn.createStatement();
            ResultSet SRT_results = SRT_statement.executeQuery(intsructors_query);

            if (SRT_results.next()){
                do{
                    int isActiveId= SRT_results.getInt("isActive");
                    statusID = isActiveId;
                } while (SRT_results.next());
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        }

        if (statusID == 0){
            status = "Неактивен";
        }  else {
            status = "Активен";
        }

        return status;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    protected void paintComponent(Graphics g){
        super.paintComponent(g); // paint children

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 900, 750);

        g.drawImage(bg_image,0,0,this);
        g.drawImage(logo_image, 0, 0, 150, 100, this);
    }


    private class AddSRTFrame extends JFrame{

        public AddSRTFrame(){

            this.setPreferredSize(new Dimension(400,120));
            this.setFocusableWindowState(true);
            this.setLayout(new GridLayout(2,1));
            this.setAutoRequestFocus(true);
            this.setTitle("Добавить инструктора");
            this.setLocation(200,200);

            CreateFrame();

        }

        private void CreateFrame(){

            JPanel SRTInfo_panel = new JPanel();
            SRTInfo_panel.setBackground(Color.WHITE);
            SRTInfo_panel.setLayout(new BorderLayout());
            this.add(SRTInfo_panel);

            JLabel SRT_label = new JLabel("  Наименование курса:  ");
            SRT_label.setForeground(Color.BLACK);
            SRTInfo_panel.add(SRT_label, BorderLayout.WEST);

            JTextField SRT_textField = new JTextField();
            SRTInfo_panel.add(SRT_textField, BorderLayout.CENTER);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JPanel buttons_panel = new JPanel();
            buttons_panel.setBackground(Color.WHITE);
            buttons_panel.setLayout(new GridLayout(1,2));
            this.add(buttons_panel);

            JButton save_button = new JButton("Сохранить");
            save_button.setBackground(Color.WHITE);
            buttons_panel.add(save_button);
            save_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (SRT_textField.getText() == null){
                        JOptionPane.showMessageDialog(MineOperations.cardPane,"Пожалуйста, введите наименование курса");
                    } else {
                        int maxIDSRT = 0;
                        String checkSRT_query = "SELECT * FROM SafetyNames WHERE RusName = N'" + SRT_textField.getText() +"'";
                        try {
                            Statement checkSRT_st = MineOperations.conn.createStatement();
                            ResultSet SRTExists_rs = checkSRT_st.executeQuery(checkSRT_query);
                            if (!SRTExists_rs.next()){

                                String insert_query = "INSERT INTO SafetyNames " +
                                        "( EngName, RusName, isActive) " +
                                        "VALUES ( ?, ? , 1)";

                                PreparedStatement ps = MineOperations.conn.prepareStatement(insert_query, PreparedStatement.RETURN_GENERATED_KEYS);
                                ps.setString(1, SRT_textField.getText());
                                ps.setString(2, SRT_textField.getText());
                                ps.executeUpdate();
                                JOptionPane.showMessageDialog(MineOperations.cardPane, "Курс успешно добавлен!!!");


                                ResultSet rs2 = ps.getGeneratedKeys();
                                if (rs2.next()) {
                                    maxIDSRT = rs2.getInt(1);
                                }


                                srt_tableModel.addRow(new Object[]{maxIDSRT,SRT_textField.getText(), "Активен" });
                                srt_tableModel.fireTableDataChanged();

                                dispose();
                                updateComboboxes();

                                insert_query = "INSERT INTO SafetyNames " +
                                        "( EngName, RusName, isActive) " +
                                        "VALUES ( '" + SRT_textField.getText() + "', '" + SRT_textField.getText() + "' , 1)";

                                String RusLog = "Добавлена запись в классификатор 'Ежегодные обучения':" + SRT_textField.getText();

                                DatabaseQueries.saveLogs(insert_query, RusLog, LoginWin.user.getId());

                            } else {
                                JOptionPane.showMessageDialog(MineOperations.cardPane,"Курс: " +
                                        SRT_textField.getText() + " уже существует");
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });

            JButton edit_button = new JButton("Отменить");
            edit_button.setBackground(Color.WHITE);
            buttons_panel.add(edit_button);
            edit_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////



        }

    }

    private class EditSRTFrame extends JFrame{



        public EditSRTFrame(){

            this.setPreferredSize(new Dimension(400,120));
            this.setFocusableWindowState(true);
            this.setLayout(new GridLayout(2,1));
            this.setAutoRequestFocus(true);
            this.setTitle("Изменить статус обучения");
            this.setLocation(200,200);

            CreateFrame();

        }

        private void CreateFrame(){

            JPanel SRTInfo_panel = new JPanel();
            SRTInfo_panel.setBackground(Color.WHITE);
            SRTInfo_panel.setLayout(new GridLayout(1,2));
            this.add(SRTInfo_panel);

            JLabel SRT_label = new JLabel(srtSelected, SwingConstants.CENTER);
            SRT_label.setForeground(Color.BLACK);
            SRTInfo_panel.add(SRT_label);

            String[] choice_string = {"Активен","Неактивен"};
            JComboBox isActive_box = new JComboBox(choice_string);
            SRTInfo_panel.add(isActive_box);
            isActive_box.setSelectedItem(statusSelected);
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            JPanel buttons_panel = new JPanel();
            buttons_panel.setBackground(Color.WHITE);
            buttons_panel.setLayout(new GridLayout(1,2));
            this.add(buttons_panel);

            JButton save_button = new JButton("Сохранить");
            save_button.setBackground(Color.WHITE);
            buttons_panel.add(save_button);
            save_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String update_query = "";
                    try{
                        int isActive_int = isActive_box.getSelectedItem() == "Неактивен" ? 0:1;
                        update_query = "UPDATE SafetyNames set isActive = " + isActive_int + "WHERE RusName = N'" + SRT_label.getText() + "'";
                        PreparedStatement updateSRTpst = MineOperations.conn.prepareStatement(update_query);
                        JOptionPane.showMessageDialog(MineOperations.cardPane,"Курс успешно обнавлен");
                        updateSRTpst.executeUpdate();

                        srt_tableModel.setValueAt(isActive_box.getSelectedItem(), srt_table.getSelectedRow(),2);

                        dispose();

                        updateComboboxes();

                    } catch (SQLException ex){
                        ex.printStackTrace();
                    }
                    String RusLog = "Изменена запись в классификаторе 'Ежегодные обучения'. Обучение " + SRT_label.getText()  + " = "  + isActive_box.getSelectedItem();

                    DatabaseQueries.saveLogs(update_query, RusLog, LoginWin.user.getId());
                }
            });

            JButton edit_button = new JButton("Отменить");
            edit_button.setBackground(Color.WHITE);
            buttons_panel.add(edit_button);
            edit_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
        }
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

    private void updateComboboxes() {
        EnterSRT.SRTName_box.removeAllItems();
        EnterSRT.SRTName_box = EnterSRT.databaseQueries.loadSRTNameBox(EnterSRT.SRTName_box);
    }

}
