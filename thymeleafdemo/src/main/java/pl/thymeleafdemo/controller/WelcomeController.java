package pl.thymeleafdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.thymeleafdemo.Client;

import java.util.List;

@Controller
@RequestMapping("/welcome")
public class WelcomeController {

    @GetMapping
    public String welcome(Model model, @RequestParam(value = "display",required = true) boolean display) {    //podajemy Model, by w html wyszedł 'message' a nie 'Hello World'
        model.addAttribute("message", "Hello from thymeleaf!");   //message = nazwa modelu - taka, jak w pliku html
        model.addAttribute("display", display);    //wyświetlić gdy imię NIE JEST PUSTE /jesli false-wyświetli się tylko paragraf "my home page"
        List<Client> clients=List.of(new Client("Jan", "Kowalski"),new Client("Kris", "Nowak"));
        model.addAttribute("clients",clients);  //sprawdz!!!!
        return "welcome";
    }
}
