package server;

import javax.jws.WebService;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;
import java.util.Map;

@WebService
public interface ServerInterface {
    String hello(String name);

    String helloStudent(Student student);

    String helloTeacher(Teacher teacher);

    @XmlJavaTypeAdapter(StudentMapAdapter.class)
    Map<Integer, Student> getStudents();

    List<Teacher> getTeachers();
}
