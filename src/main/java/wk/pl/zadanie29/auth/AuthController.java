package wk.pl.zadanie29.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @GetMapping("/logowanie")
    public String logingForm(@RequestParam(required = false) String error,
                             @RequestParam(required = false, name = "po-rejestracji") String afterReg,
                             Model model) {
        boolean showErrorMessage = error != null;
        boolean afterRegistration = afterReg != null;
        model.addAttribute("showErrorMessage", showErrorMessage);
        model.addAttribute("afterRegistration", afterRegistration);
        return "login";
    }
}
