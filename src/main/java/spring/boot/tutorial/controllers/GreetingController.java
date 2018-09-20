package spring.boot.tutorial.controllers;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import spring.boot.tutorial.beans.GreetingBean;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(path="/greeting", method=RequestMethod.GET)
    public GreetingBean greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new GreetingBean(counter.incrementAndGet(),
                            String.format(template, name));
    }
}
