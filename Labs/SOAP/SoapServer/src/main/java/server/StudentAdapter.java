package server;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class StudentAdapter extends XmlAdapter<StudentImpl, Student> {
    public StudentImpl marshal(Student student) throws Exception {
        if (student instanceof StudentImpl) {
            return (StudentImpl) student;
        }
        StudentImpl s = new StudentImpl();
        s.setName(student.getName());
        return s;
    }
 
    public Student unmarshal(StudentImpl student) throws Exception {
        return student;
    }
}