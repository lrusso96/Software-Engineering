package server;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "Course")
public class Course {
    private int id;
    private String name;
    private List<Student> students = new ArrayList<>();

    @GET
    @Path("{studentId}")
    public Student getStudent(@PathParam("studentId") int studentId) {
        return findById(studentId);
    }

    @POST
    @Path("")
    public Response createStudent(Student student) {
        for (Student element : students) {
            if (element.getId() == student.getId()) {
                return Response.status(Response.Status.CONFLICT).build();
            }
        }
        students.add(student);
        return Response.ok(student).build();
    }

    @DELETE
    @Path("{studentId}")
    public Response deleteStudent(@PathParam("studentId") int studentId) {
        Student student = findById(studentId);
        if (student == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        students.remove(student);
        return Response.ok().build();
    }

    private Student findById(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return this.students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}