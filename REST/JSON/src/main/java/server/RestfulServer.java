package server;

import com.fasterxml.jackson.jaxrs.json.*;
import java.util.*;
import org.apache.cxf.binding.BindingFactoryManager;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.*;
import org.apache.cxf.jaxrs.lifecycle.*;

public class RestfulServer {

    public static void main(String[] args) {
        JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();
        factoryBean.setResourceClasses(FlightsRepository.class);
        FlightsRepository repository = new FlightsRepository();
        //args[0] contains the path to the database
        repository.setConnection(args[0]);
        factoryBean.setResourceProvider(new SingletonResourceProvider(repository));
        int port = 8080;
        factoryBean.setAddress("http://localhost:"+port+"/");

        List<Object> providers = new ArrayList<Object>();
        providers.add(new JacksonJaxbJsonProvider());

        factoryBean.setProviders(providers);

        BindingFactoryManager manager = factoryBean.getBus().getExtension(BindingFactoryManager.class);
        JAXRSBindingFactory restFactory = new JAXRSBindingFactory();
        restFactory.setBus(factoryBean.getBus());
        manager.registerBindingFactory(JAXRSBindingFactory.JAXRS_BINDING_ID, restFactory);

        Server server = factoryBean.create();
        server.start();
        System.out.println("Server is running on port " + port);

        while (true) {
        }
    }
}
