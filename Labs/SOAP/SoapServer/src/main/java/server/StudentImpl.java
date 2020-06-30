package server;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "Student")
public class StudentImpl implements Student {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}