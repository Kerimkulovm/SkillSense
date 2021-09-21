import java.util.Date;

public class Employee {

    private int employeeID, //табельный номер
            jobClass,       //рабочий класс
            jobLevel;       //рабочий уровень

    private String nameEng, //имя и фамилия на английском
            nameRus,        //имя и фамилия на русском
            positionEng,    //позиция на английском
            positionRus,    //позиция на русском
            reportsTo,      //начальник
            department,     //департамени/отдел
            shift,          //смена
            location;       //логация drop-down-menu (Бишкек, Балыкчы, Каракол)

    private Date hireDate;  //дата приема на работу

    public Employee(){}

}
