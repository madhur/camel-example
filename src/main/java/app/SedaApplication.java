package app;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.seda.SedaEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@Slf4j
@ComponentScan()
public class SedaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SedaApplication.class, args);
    }

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private CamelContext camelContext;

    @RequestMapping("/send/{message}")
    public String sendMessage(@PathVariable String message) throws Exception {
        producerTemplate.sendBody("seda:start?size=10&blockWhenFull=false", message);
        System.out.println("Producing message: " + message);
        SedaEndpoint sedaEndpoint = (SedaEndpoint) camelContext.getEndpoint("seda:start");
        System.out.println("Remaining capacity : " + sedaEndpoint.getQueue().remainingCapacity());
        return "";
    }
}