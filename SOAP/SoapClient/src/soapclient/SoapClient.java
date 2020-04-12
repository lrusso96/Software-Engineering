package soapclient;

import it.sapienza.softeng.soapws.Student;
import it.sapienza.softeng.soapws.StudentEntry;
import it.sapienza.softeng.soapws.StudentMap;
import it.sapienza.softeng.soapws.WSImplService;
import it.sapienza.softeng.soapws.WSInterface;
import java.util.List;

public class SoapClient {

    public static void main(String[] args) {
        System.out.println(hello("World"));

        String[] names = {"Mick", "Michael"};
        
        for(String name : names) {
            Student student = new Student();
            student.setName(name);
            String ret = helloStudent(student);
            System.out.println("Received: " + ret);
        }
        
        List<StudentEntry> students = getStudents().getEntry();
        for (StudentEntry s : students)
            System.out.println(s.getStudent().getName());
    }
    
    private static WSInterface getInterface(){
        WSImplService service = new WSImplService();
        return service.getWSImplPort();
    }

    private static String helloStudent(Student student) {
        return getInterface().helloStudent(student);
    }

    private static String hello(String s) {
        return getInterface().hello(s);
    }

    private static StudentMap getStudents() {
        return getInterface().getStudents();
    }
    
}
