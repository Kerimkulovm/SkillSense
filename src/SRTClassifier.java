import javax.imageio.ImageIO;
import javax.swing.*;
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

public class SRTClassifier extends JPanel {

    private BufferedImage logo_image;

    private JTable srt_table;
    private DefaultTableModel srt_tableModel;

    public DatabaseQueries databaseQueries = new DatabaseQueries();
    public String srtSelected;

    public List<String> srt_list = new ArrayList<>();

    public SRTClassifier(){

        try {
            logo_image = ImageIO.read(new File("textures/logo/kumtor_logo.jpg"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        setLayout(null);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        JLabel titleEng = new JLabel("<html><big>Управление классификаторами 'SRT'</big><br /></html>");
        titleEng.setBounds(160, 0, 400, 100);
        titleEng.setFont(new Font("Helvetica", Font.BOLD, 20));
        titleEng.setForeground(Color.WHITE);
        this.add(titleEng);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        JTable SRT_panel = new JTable();
        SRT_panel.setBackground(Color.WHITE);
        SRT_panel.setBounds(20,120,650,500);
        SRT_panel.setLayout(new BorderLayout());
        this.add(SRT_panel);

        JPanel buttons_panel = new JPanel(new GridLayout());
        buttons_panel.setBackground(Color.WHITE);
        buttons_panel.setBounds(20,635,300,30);
        this.add(buttons_panel);

        JButton exit_button = new JButton("Выход");
        exit_button.setBounds(720, 60, 150, 30);
        exit_button.setBackground(Color.RED);
        exit_button.setForeground(Color.WHITE);
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
        JButton edit_button = new JButton("Изменить");
        edit_button.setForeground(Color.BLACK);
        edit_button.setBackground(Color.WHITE);
        buttons_panel.add(edit_button);
        edit_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditSRTFrame editSRTFrame = new EditSRTFrame();
                editSRTFrame.setVisible(true);
                editSRTFrame.pack();
            }
        });

        JButton create_button = new JButton("Создать");
        create_button.setBackground(Color.WHITE);
        create_button.setForeground(Color.BLACK);
        buttons_panel.add(create_button);
        create_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddSRTFrame createSRTFrame = new AddSRTFrame();
                createSRTFrame.setVisible(true);
                createSRTFrame.pack();
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    private void buildTable(){

        srt_list = databaseQueries.loadSRT(srt_list);

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
        SRT_header.setFont(new Font("Helvetica", Font.BOLD,12));

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
            srt_table.setValueAt(databaseQueries.getSRTID(srt_list.get(i)),i,0);
            srt_table.setValueAt(srt_list.get(i),i,1);
            srt_table.setValueAt(databaseQueries.getSRTActivity(srt_list.get(i)),i,2);
        }

        srt_table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1){
                    srtSelected = (String) srt_table.getValueAt(srt_table.getSelectedRow(),1);
                    System.out.println(srtSelected);
                }
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

            JLabel SRT_label = new JLabel("  Ф.И.О Инструктора:  ");
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
                        JOptionPane.showMessageDialog(MineOperations.cardPane,"Пожалуйста, введите имя инструктора");
                    } else {
                        int maxIDSRT = 0;
                        String checkSRT_query = "SELECT * FROM dbo.SafetyNames WHERE course = N'" + SRT_textField.getText() +"'";
                        try {
                            Statement checkSRT_st = MineOperations.conn.createStatement();
                            ResultSet SRTExists_rs = checkSRT_st.executeQuery(checkSRT_query);
                            if (!SRTExists_rs.next()){
                                String maxSRTId_query = "SELECT max (ReviewNo) as ReviewNo FROM dbo.SafetyNames";
                                try{
                                    Statement maxSRTId_st = MineOperations.conn.createStatement();
                                    ResultSet maxSRTd_rs = maxSRTId_st.executeQuery(maxSRTId_query);
                                    if (maxSRTd_rs.next()){
                                        maxIDSRT = maxSRTd_rs.getInt(1);
                                        System.out.println(maxIDSRT);
                                    }
                                } catch (SQLException ex){
                                    ex.printStackTrace();
                                }

                                maxIDSRT++;

                                String insert_query = "INSERT INTO dbo.SafetyNames " +
                                        "(ReviewNo, ReviewName, course, isActive) " +
                                        "VALUES (" + maxIDSRT + ", N'" + SRT_textField.getText() + "', N'" +  SRT_textField.getText() + "', 1)";
                                System.out.println(insert_query);

                                PreparedStatement insertSRTr = MineOperations.conn.prepareStatement(insert_query);
                                insertSRTr.executeUpdate();
                                JOptionPane.showMessageDialog(MineOperations.cardPane, "Инструктор успешно добавлен");


                                srt_tableModel.addRow(new Object[]{maxIDSRT,SRT_textField.getText(), "Активен" });
                                srt_tableModel.fireTableDataChanged();

                                dispose();

                            } else {
                                JOptionPane.showMessageDialog(MineOperations.cardPane,"SRT: " +
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
            this.setTitle("Добавить инструктора");
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
                    try{
                        int isActive_int = isActive_box.getSelectedItem() == "Неактивен" ? 0:1;
                        String update_query = "UPDATE dbo.SafetyNames set isActive = " + isActive_int + "WHERE course = N'" + SRT_label.getText() + "'";
                        PreparedStatement updateSRTpst = MineOperations.conn.prepareStatement(update_query);
                        JOptionPane.showMessageDialog(MineOperations.cardPane,"Инструктор успешно обнавлен");
                        updateSRTpst.executeUpdate();

                        srt_tableModel.setValueAt(isActive_box.getSelectedItem(), srt_table.getSelectedRow(),2);

                        dispose();

                    } catch (SQLException ex){
                        ex.printStackTrace();
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
        }
    }
}
