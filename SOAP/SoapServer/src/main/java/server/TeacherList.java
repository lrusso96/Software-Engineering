package server;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlType(name = "TeacherList")
public class TeacherList {
    private List<Teacher> teachers = new ArrayList<Teacher>();

    @XmlElement(name = "entry")
    public List<Teacher> getTeachers() {
        return this.teachers;
    }
}
