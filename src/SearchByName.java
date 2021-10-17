import org.w3c.dom.ls.LSOutput;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SearchByName extends JPanel {

    private BufferedImage logo_image;

    private JLabel tableID_label;

    private JTextField tableFIO_text;
    private JTextField nameRus_text;

    private JButton search_by_name_button;
    static JTable fio_table;
    DefaultTableModel model = null;
    JTable table = null;

    public SearchByName()
    {
        try {
            logo_image = ImageIO.read(new File("textures/logo/kumtor_logo.jpg"));
        } catch (IOException ex) {

        }


        setLayout(null);

        JLabel titleEng = new JLabel("<html><big>Search by name</big><br /> Поиск по имени</html>");
        titleEng.setBounds(160, 0, 600, 100);
        titleEng.setBackground(Color.WHITE);
        titleEng.setForeground(Color.WHITE);
        titleEng.setFont(new Font("Kumtor", Font.BOLD, 20));
        this.add(titleEng);


        JButton exit_button = new JButton("Выход");
        exit_button.setBounds(720, 60, 150, 30);
        exit_button.setBackground(Color.RED);
        exit_button.setForeground(Color.WHITE);
        add(exit_button);
        exit_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MineOperations.card.show(MineOperations.cardPane,"Employee Info");

            }
        });

        String[] columnNames = {"FullNameRu", "EmployeeID"};

        //model.setColumnIdentifiers(columnNames);

        /*fio_table = new JTable();
        fio_table.setModel(model);
        fio_table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        fio_table.setFillsViewportHeight(true);
        JScrollPane scroll = new JScrollPane(fio_table);
        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
*/





        JPanel searchByName_panel = new JPanel();
        searchByName_panel.setBackground(Color.white);
        searchByName_panel.setLayout(new BoxLayout(searchByName_panel, BoxLayout.X_AXIS));
        searchByName_panel.setBorder(new TitledBorder(new LineBorder(Color.orange), "Поиск сотрудника"));
        searchByName_panel.setBounds(20, 120, 450, 50);
        this.add(searchByName_panel);

        JPanel searchTable_panel = new JPanel();
        searchTable_panel.setBackground(Color.white);
        searchTable_panel.setLayout(new BoxLayout(searchTable_panel, BoxLayout.X_AXIS));
        searchTable_panel.setBorder(new TitledBorder(new LineBorder(Color.orange), "Результаты поиска"));
        searchTable_panel.setBounds(20, 200, 550, 450);
        this.add(searchTable_panel);

        JPanel tableID_panel = new JPanel();
        tableID_label = new JLabel(" Фамилия или имя:  ");
        tableID_label.setForeground(Color.RED);
        tableID_panel.setBackground(Color.WHITE);
        tableID_panel.add(tableID_label);
        searchByName_panel.add(tableID_label);

        tableFIO_text = new JTextField();
        tableFIO_text.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1, true));
        tableID_panel.add(tableFIO_text);
        searchByName_panel.add(tableFIO_text);


        search_by_name_button = new JButton("Поиск");
        search_by_name_button.setForeground(Color.RED);
        search_by_name_button.setBackground(Color.WHITE);
        tableID_panel.add(search_by_name_button);
        searchByName_panel.add(search_by_name_button);
        search_by_name_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("dgsfg");
                if (tableFIO_text.getText().equals("") )
                    JOptionPane.showMessageDialog(MineOperations.cardPane,"Пожалуйста введите фамилию или имя");
                else {

                    try {
                        searchTable_panel.removeAll();

                        Statement searchStatement = MineOperations.conn.createStatement();
                        String query_text =
                                "SELECT FullNameRu, EmployeeID  FROM dbo.Employees " +
                                        "WHERE EmployeeID is not null and FullNameRu like N'%" + tableFIO_text.getText() + "%' order by FullNameRu desc";
                        System.out.println(query_text);
                        ResultSet rs = searchStatement.executeQuery(query_text);

                        if (!rs.next()) JOptionPane.showMessageDialog(MineOperations.cardPane, "Нет данных");
                        else {

                            String columns[] = { "Num", "FullNameRu", "EmployeeID" };
                            String data[][] = new String[25][3];
                            String FullNameRu = "";
                            String EmployeeID = "";
                            int i = 0;
                            int m = 25;

                            while (rs.next()) {
                                if (i<m){
                                    FullNameRu = rs.getString("FullNameRu");
                                    EmployeeID = rs.getString("EmployeeID");

                                    data[i][0] = i+1 + "";
                                    data[i][1] = FullNameRu;
                                    data[i][2] = EmployeeID;
                                }
                                i++;

                            }

                            model = new DefaultTableModel(data, columns);
                            table = new JTable(model);
                            table.setShowGrid(true);
                            table.setShowVerticalLines(true);
                            JScrollPane pane = new JScrollPane(table);

                            searchTable_panel.add(pane);
                            revalidate();
                            repaint();

                        }

                    }  catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });


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
