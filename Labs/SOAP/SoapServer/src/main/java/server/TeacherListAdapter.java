package server;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.List;

public class TeacherListAdapter extends XmlAdapter<TeacherList, List<Teacher>> {

    public TeacherList marshal(List<Teacher> teachers) throws Exception {
        TeacherList teacherList = new TeacherList();
        for(Teacher teacher : teachers){
            teacherList.getTeachers().add(teacher);
        }
        return teacherList;
    }

    public List<Teacher> unmarshal(TeacherList teacherList) throws Exception {
        return teacherList.getTeachers();
    }
}
