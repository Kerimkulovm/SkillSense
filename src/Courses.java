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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Courses extends JPanel {

    private BufferedImage logo_image;


    private JPanel coursesList_panel;
    private JTable listOfCourses_table;
    private int numOfRows = 1, numOfColumns = 2;
    private List<Integer> coursesNum_list = new ArrayList<>();
    private List<String> coursesName_list = new ArrayList<>();
    private List<Integer> coursesActive_list = new ArrayList<>();

    private JButton  add_button = null;

    public Courses()
    {
        try {
            logo_image = ImageIO.read(new File("textures/logo/kumtor_logo.jpg"));
        } catch (IOException ex) {

        }


        setLayout(null);

        JLabel titleEng = new JLabel("<html><big>Управление классификаторами 'Курсы'</big></html>");
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
                MineOperations.card.show(MineOperations.cardPane,"Home Page");

            }
        });





        coursesList_panel = new JPanel();
        coursesList_panel.setBackground(Color.WHITE);
        coursesList_panel.setBounds(20, 120, 550, 500);
        coursesList_panel.setLayout(new BorderLayout());
        coursesList_panel.setBorder(new LineBorder(Color.BLACK));
        coursesList_panel.setVisible(true);
        this.add(coursesList_panel);

        createTable();


        JPanel buttons_panel = new JPanel(new GridLayout());
        buttons_panel.setBackground(Color.WHITE);
        buttons_panel.setBounds(20, 635, 500, 30);
        buttons_panel.setLayout(new GridLayout(1, 5));
        buttons_panel.setBorder(new TitledBorder(new LineBorder(Color.orange)));
        this.add(buttons_panel);





        add_button = new JButton("Создать");
        add_button.setBackground(Color.GREEN);
        add_button.setForeground(Color.BLACK);
        add_button.setFont(new Font("Helvetica", Font.BOLD, 10));
        buttons_panel.add(add_button);
        add_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });



    }

    private void createTable(){

        try{
            Statement searchStatement = MineOperations.conn.createStatement();
            String query_text = "select coarseNo, Course, isActive from Courses where course is not null order by CoarseNo";

            System.out.println(query_text);
            ResultSet rs = searchStatement.executeQuery(query_text);

            if (rs.next()){

                do {

                    int courseNum = rs.getInt("coarseNo");
                    coursesNum_list.add(courseNum);

                    String courseName = rs.getString("Course");
                    coursesName_list.add(courseName);

                    int isActive = rs.getInt("isActive");
                    coursesActive_list.add(isActive);

                }while (rs.next());
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

        numOfRows = coursesName_list.size();



        DefaultTableModel listOfCourses_model = new DefaultTableModel(numOfRows ,numOfColumns);


        DefaultTableCellRenderer centerRederer = new DefaultTableCellRenderer();
        centerRederer.setHorizontalAlignment(JLabel.CENTER);

        listOfCourses_table = new JTable(listOfCourses_model);
        listOfCourses_table.setBorder(new LineBorder(Color.BLACK));
        listOfCourses_table.setBackground(Color.WHITE);
        listOfCourses_table.setRowHeight(25);
        listOfCourses_table.setVisible(true);

        JTableHeader listHeader= listOfCourses_table.getTableHeader();
        listHeader.setBorder(new LineBorder(Color.BLACK));
        listHeader.setBackground(Color.WHITE);
        listHeader.setFont(new Font("Helvetica", Font.BOLD,12));

        TableColumnModel listOfCourses_columns = listHeader.getColumnModel();

        TableColumn tabCol0 = listOfCourses_columns.getColumn(0);
        tabCol0.setHeaderValue("Курс");
        tabCol0.setCellRenderer(centerRederer);
        tabCol0.setPreferredWidth(10);

        for (int i = 0; i < numOfRows; i++){
            listOfCourses_table.setValueAt(coursesNum_list.get(i),i,0);
        }


        for (int i = 0; i < numOfRows; i++){
            listOfCourses_table.setValueAt(coursesName_list.get(i),i,0);
        }

        TableColumn tabCol1 = listOfCourses_columns.getColumn(1);
        tabCol1.setCellRenderer(centerRederer);
        tabCol1.setHeaderValue("Активный");

        for (int i = 0; i < numOfRows; i++){
            String v = coursesActive_list.get(i) == 0 ? "Нет" : "Да";
            listOfCourses_table.setValueAt(v ,i,1);
        }



        coursesList_panel.add(new JScrollPane(listOfCourses_table));
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
