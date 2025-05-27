import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class MineOperations extends JFrame {

    //Connecting to Database
    //public static String url = "jdbc:sqlserver://localhost:1433;databaseName=MineOperationsTestDB; username=MineTraining;password=qazwsx";
    //public static String url = "jdbc:sqlserver://localhost:1433;databaseName=SkillSense; username=MineTraining;password=qazwsx";


    //public static String url = "jdbc:sqlserver://kyrkumms05.kumtor.kg:1433;databaseName=MineTrainingDB; username=mt;password=weKnowNothing01";
    public static Connection conn;
    static {
        try {
            //conn = DriverManager.getConnection(url);
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/SkillSense", "postgres", "741852" );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static JFrame application_frame;
    public static final int FRAME_WIDTH = 900;
    public static final int FRAME_HEIGHT = 750;


    public static JFrame login_frame;
    public static final int LOGIN_WIDTH = 300;
    public static final int LOGIN_HEIGHT = 180;


    public static HomePage homePagePanel = new HomePage();

    private static final LoginWin LoginPanel = new LoginWin();
    private static final EmployeeInfo employeeInfoPanel = new EmployeeInfo();
    private static final OperationsDaily operationsDailyPanel = new OperationsDaily();
    private static final EnterSRT enterSRTPanel = new EnterSRT();
    private static final ViewQualifications viewQualificationsPanel = new ViewQualifications();
    private static final Courses coursesPanel = new Courses();
    private static final Instructors instructorsPanel = new Instructors();
    private static final Classificatory classificatoryPanel = new Classificatory();
    private static final Positions positionsPanel = new Positions();
    private static final Departments departmentsPanel = new Departments();
    private static final Crews crewsPanel = new Crews();
    private static final SRTClassifier SRTClassifierPanel = new SRTClassifier();
    private static final Supervisors supervisorsPanel = new Supervisors();
    private static final SRTHoursEditorial SRTEditorialPanel = new SRTHoursEditorial();
    private static final DailyEditorial daysEditorialPanel = new DailyEditorial();


    public static CardLayout card;
    public static JPanel cardPane;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //launchMineOperationsTraining();

        openLoginWin();
        //connectToDatabase();
    }

    public static void launchMineOperationsTraining() {

        application_frame = new JFrame();
        application_frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        application_frame.setTitle("Mine Operations Training");
        application_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardPane = new JPanel();
        card = new CardLayout();

        cardPane.setLayout(card);

        cardPane.add(homePagePanel,"Home Page");
        cardPane.add(employeeInfoPanel, "Employee Info");
        cardPane.add(operationsDailyPanel, "Operations Daily");
        cardPane.add(enterSRTPanel,"Enter SRT");
        cardPane.add(viewQualificationsPanel,"View Qualifications");
        cardPane.add(classificatoryPanel,"Classificatory");
        cardPane.add(coursesPanel, "Courses");
        cardPane.add(instructorsPanel,"Instructors");
        cardPane.add(positionsPanel, "Positions");
        cardPane.add(departmentsPanel, "Departments");
        cardPane.add(crewsPanel,"Crews");
        cardPane.add(SRTClassifierPanel, "SRT");
        cardPane.add(supervisorsPanel,"Supervisors");
        cardPane.add(SRTEditorialPanel, "SRTEditorial");
        cardPane.add(daysEditorialPanel, "DaysEditorial");
        //cardPane.add(LoginPanel,"Login");
        application_frame.add(cardPane);
        application_frame.setVisible(true);
    }


    private static void openLoginWin() {
 System.out.println ("11111111");
        login_frame = new JFrame();
        login_frame.setSize(LOGIN_WIDTH, LOGIN_HEIGHT);
        login_frame.setTitle("Login Window");
        login_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardPane = new JPanel();
        card = new CardLayout();

        cardPane.setLayout(card);
        cardPane.add(LoginPanel,"Login");
        login_frame.add(cardPane);
        login_frame.setVisible(true);


    }

    private static void connectToDatabase() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    }
}
