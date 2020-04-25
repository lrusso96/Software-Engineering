package server;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("courses")
@Produces("text/xml")
public class CourseRepository {
    private Map<Integer, Course> courses = new HashMap<>();

    CourseRepository() {
        Student student1 = new Student();
        Student student2 = new Student();
        student1.setId(1);
        student1.setName("John");
        student2.setId(2);
        student2.setName("Paul");

        List<Student> course1Students = new ArrayList<>();
        course1Students.add(student1);
        course1Students.add(student2);

        Course course1 = new Course();
        Course course2 = new Course();
        course1.setId(1);
        course1.setName("Algorithm Design");
        course1.setStudents(course1Students);
        course2.setId(2);
        course2.setName("Software Engineering");

        this.courses.put(1, course1);
        this.courses.put(2, course2);
    }

    @GET
    @Path("{courseId}")
    public Course getCourse(@PathParam("courseId") int courseId) {
        return findById(courseId);
    }

    @PUT
    @Path("{courseId}")
    public Response updateCourse(@PathParam("courseId") int courseId, Course course) {
        Course existingCourse = findById(courseId);
        if (existingCourse == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (existingCourse.equals(course)) {
            return Response.notModified().build();
        }
        courses.put(courseId, course);
        return Response.ok().build();
    }

    @Path("{courseId}/students")
    public Course pathToStudent(@PathParam("courseId") int courseId) {
        return findById(courseId);
    }

    private Course findById(int id) {
        for (Map.Entry<Integer, Course> course : courses.entrySet()) {
            if (course.getKey() == id) {
                return course.getValue();
            }
        }
        return null;
    }
}