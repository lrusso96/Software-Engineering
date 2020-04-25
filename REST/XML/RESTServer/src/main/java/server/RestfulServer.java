package server;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;

public class RestfulServer {

    public static void main(String[] args) {
        JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();
        factoryBean.setResourceClasses(CourseRepository.class);

        factoryBean.setResourceProvider(new SingletonResourceProvider(new CourseRepository()));

        factoryBean.setAddress("http://localhost:8080/");

        Server server = factoryBean.create();

        server.start();

        while (true) {
        }
    }
}
