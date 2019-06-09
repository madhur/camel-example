package app;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TestRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("seda:start?size=10").process(exchange -> {
            System.out.println("Consumed message: " + exchange.getIn().getBody());
        }).end();
    }
}