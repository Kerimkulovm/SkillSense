import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class MineOperations extends JFrame {

    //Connecting to Database
    public static String url = "jdbc:sqlserver://localhost:1433;databaseName=MineOperationsTestDb; username=MineTraining;password=qazwsx";
    public static Connection conn;
    static {
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static JFrame application_frame;

    public static final int FRAME_WIDTH = 900;
    public static final int FRAME_HEIGHT = 750;

    public static HomePage homePagePanel = new HomePage();
    public static EmployeeInfo employeeInfoPanel;

    static {
        employeeInfoPanel = new EmployeeInfo();
    }

    public static EnterMenu enterMenuPanel = new EnterMenu();
    public static OperationsDaily operationsDailyPanel = new OperationsDaily();
    public static EnterSRT enterSRTPanel = new EnterSRT();
    public static ViewQualifications viewQualificationsPanel = new ViewQualifications();

    public static SearchByName SearchByNamePanel = new SearchByName();
    public static Courses CoursesPanel = new Courses();

    public static CardLayout card;
    public static JPanel cardPane;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        launchMineOperationsTraining();
        connectToDatabase();
    }

    private static void launchMineOperationsTraining() {

        application_frame = new JFrame();
        application_frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        application_frame.setTitle("Mine Operations Training");
        application_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardPane = new JPanel();
        card = new CardLayout();

        cardPane.setLayout(card);
        cardPane.add(homePagePanel,"Home Page");
        cardPane.add(enterMenuPanel, "Enter Data");
        cardPane.add(employeeInfoPanel, "Employee Info");
        cardPane.add(operationsDailyPanel, "Operations Daily");
        cardPane.add(enterSRTPanel,"Enter SRT");
        cardPane.add(viewQualificationsPanel,"View Qualifications");

        cardPane.add(SearchByNamePanel, "Search By Name");
        cardPane.add(CoursesPanel, "Courses");

        application_frame.add(cardPane);
        application_frame.setVisible(true);
    }
    private static void connectToDatabase() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    }
}
