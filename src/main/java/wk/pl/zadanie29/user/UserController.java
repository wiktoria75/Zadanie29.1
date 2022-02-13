package wk.pl.zadanie29.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/secure")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String secure(Model model) {
        model.addAttribute("admin", userService.checkIfCurrentUserIsAnAdmin());
        model.addAttribute("user", userService.findCurrentUser());
        return "secure";
    }

    @PostMapping("")
    public String secure(@RequestParam (required = false) String change, @RequestParam String text) {
        userService.updateUser(change, text);
        return "redirect:/secure";
    }
    
}
