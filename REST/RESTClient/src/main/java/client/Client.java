package client;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.xml.bind.JAXB;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class Client {
    private static final String BASE_URL = "http://localhost:8080/courses";
    private static CloseableHttpClient client;

    public static void main(String[] args) throws IOException {
        client = HttpClients.createDefault();

        //GET requests
        System.out.println("GET course with id 1...");
        Course c1 = getCourse(1);
        System.out.println(c1);
        for (int i = 0; i < c1.getStudents().size(); i++)
            System.out.println(c1.getStudents().get(i));

        //POST request
        System.out.println("GET course with id 2...");
        Course c2 = getCourse(2);
        System.out.println(c2);

        createValidStudent(2);

        c2 = getCourse(2);
        System.out.println(c2);

        if (c2.getStudents() != null) {
            for (int i = 0; i < c2.getStudents().size(); i++)
                System.out.println(c2.getStudents().get(i));
        }

        client.close();
    }

    private static Course getCourse(int courseID) throws IOException {
        System.out.println(String.format("GET course with id %d...", courseID));
        URL url = new URL(String.format("%s/%d", BASE_URL, courseID));
        InputStream input = url.openStream();
        return JAXB.unmarshal(new InputStreamReader(input), Course.class);
    }

    private static Student getStudent(int courseID, int studentID) throws IOException {
        System.out.println(String.format("GET student %d for course %d...", studentID, courseID));
        URL url = new URL(String.format("%s/%d/students/%d", BASE_URL, courseID, studentID));
        InputStream input = url.openStream();
        return JAXB.unmarshal(new InputStreamReader(input), Student.class);
    }

    private static void createValidStudent(int courseId) throws IOException {
        System.out.println(String.format("POST student for course %d...", courseId));
        final HttpPost httpPost = new HttpPost(String.format("%s/%d/students", BASE_URL, courseId));
        final InputStream resourceStream = Client.class.getClassLoader().getResourceAsStream("student.xml");

        httpPost.setEntity(new InputStreamEntity(resourceStream));
        httpPost.setHeader("Content-Type", "text/xml");

        final HttpResponse response = client.execute(httpPost);
        System.out.println(response.toString());
    }

}