package example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class StudentController {

    @RequestMapping(value = "/hi")
    public String hi() {
        return "Hi Student";
    }

    @GetMapping(value = "/action")
    public String action() {
        return "Student Action";
    }
}