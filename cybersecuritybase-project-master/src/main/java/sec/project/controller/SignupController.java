package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.config.CustomUserDetailsService;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

@Controller
public class SignupController {
    
    @Autowired
    public CustomUserDetailsService users;

    @Autowired
    public SignupRepository signupRepository;

    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm() {
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(@RequestParam String name, @RequestParam String address) {
        signupRepository.save(new Signup(name, address));
        return "done";
    }
    
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteUsers(){
        signupRepository.deleteAll();
        return "redirect:/form";
    }

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String view(Model model) {
        model.addAttribute("showers", signupRepository.findAll());
        model.addAttribute("userDetails", users.getAccountDetails());
        return "users";
    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loadLogin() {
        return "login";
    }
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String submitLogin(Model model, @RequestParam String uname, @RequestParam String psw) {
        model.addAttribute("userInfo", users.loadUserByUsername(uname));
        return "success";
    }
}
