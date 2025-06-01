import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class MineOperations extends JFrame {

//   PgAdmin pass:741852

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




    private static final LoginWin LoginPanel = new LoginWin();
    public static HomePage homePagePanel ;
    private static  EmployeeInfo employeeInfoPanel ;
    private static  OperationsDaily operationsDailyPanel ;
    private static  EnterSRT enterSRTPanel ;
    private static  ViewQualifications viewQualificationsPanel ;
    private static  Courses coursesPanel ;
    private static  Instructors instructorsPanel ;
    private static  Classificatory classificatoryPanel ;
    private static  Positions positionsPanel ;
    private static  Users usersPanel ;
    private static  Departments departmentsPanel ;
    private static  Crews crewsPanel ;
    private static  SRTClassifier SRTClassifierPanel ;
    private static  Supervisors supervisorsPanel ;
    private static  SRTHoursEditorial SRTEditorialPanel ;
    private static  DailyEditorial daysEditorialPanel ;



    public static CardLayout card;
    public static JPanel cardPane;




    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        openLoginWin();
    }

    public static void launchMineOperationsTraining() {
        System.out.println("launchMineOperationsTraining");



        homePagePanel = new HomePage();
        employeeInfoPanel = new EmployeeInfo();
        operationsDailyPanel = new OperationsDaily();
        enterSRTPanel = new EnterSRT();
        viewQualificationsPanel = new ViewQualifications();
        coursesPanel = new Courses();
        instructorsPanel = new Instructors();
        classificatoryPanel = new Classificatory();
        positionsPanel = new Positions();
        usersPanel = new Users();
        departmentsPanel = new Departments();
        crewsPanel = new Crews();
        SRTClassifierPanel = new SRTClassifier();
        supervisorsPanel = new Supervisors();
        SRTEditorialPanel = new SRTHoursEditorial();
        daysEditorialPanel = new DailyEditorial();


        application_frame = new JFrame();
        application_frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        application_frame.setTitle("Mine Operations Training   " +  LoginWin.user.getLogin() + "    " + LoginWin.user.getRolename());
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
        cardPane.add(usersPanel, "Users");
        cardPane.add(departmentsPanel, "Departments");
        cardPane.add(crewsPanel,"Crews");
        cardPane.add(SRTClassifierPanel, "SRT");
        cardPane.add(supervisorsPanel,"Supervisors");
        cardPane.add(SRTEditorialPanel, "SRTEditorial");
        cardPane.add(daysEditorialPanel, "DaysEditorial");

        application_frame.add(cardPane);
        application_frame.setVisible(true);


    }


    private static void openLoginWin() {
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

   /* private static void connectToDatabase() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    }*/
}
