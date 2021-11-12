package Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PersonController {
    //@Autowired
    //private PersonRepository repository;

    //private PeopleService service = new PeopleService(repository);
    @Autowired
    public PersonController(){

    }

    @GetMapping("/{name}")
    public String getStr(@PathVariable("name") String name,Model model){
        String res;
        if(name == null){
            res = "Hello";
        } else{
            res = "Hello, " + name;
        }
        model.addAttribute("result",res);
        return "people/index";
    }
   /*
    @PostMapping
    public String save(@RequestParam File file){
     service.add(file);
     return "index";
    }
    @DeleteMapping
    public String delete(@RequestParam Long id){
        service.deleteById(id);
        return "index";
    }
*/

}
