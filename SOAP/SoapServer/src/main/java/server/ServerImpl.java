package server;

import javax.jws.WebService;
import java.util.*;

@WebService(endpointInterface = "server.ServerInterface")
public class ServerImpl implements ServerInterface {
    private Map<Integer, Student> students = new LinkedHashMap<Integer, Student>();
    private List<Teacher> teachers = new ArrayList<Teacher>();

    public String hello(String name) {
        return "Hello " + name;
    }

    public String helloStudent(Student student) {
        students.put(students.size() + 1, student);
        return "Hello " + student.getName();
    }

    public String helloTeacher(Teacher teacher) {
        teachers.add(teacher);
        return "Hello " + teacher.getName();
    }

    public Map<Integer, Student> getStudents() {
        return students;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }
}
