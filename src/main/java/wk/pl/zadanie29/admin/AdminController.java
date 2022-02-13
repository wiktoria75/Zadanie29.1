package wk.pl.zadanie29.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wk.pl.zadanie29.user.UserService;
import wk.pl.zadanie29.userrole.UserRoleService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final UserRoleService userRoleService;

    public AdminController(UserService userService, UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @GetMapping("")
    public String adminPanel(@RequestParam(required = false) boolean lastAdmin, Model model) {
        model.addAttribute("lastAdmin", lastAdmin);
        model.addAttribute("currentUser", userService.findCurrentUser());
        model.addAttribute("users", userService.getAllUsersButCurrentAndChangeToDto());
        return "admin_panel";
    }

    @GetMapping("/add/{id}")
    public String addAdmin(@PathVariable Long id) {
        userRoleService.addAdmin(id);
        return "redirect:/admin";
    }

    @GetMapping("/remove/{id}")
    public String removeAdminRole(@PathVariable Long id) {
        boolean adminRoleRemoved = userRoleService.removeAdminRole(id);
        if (adminRoleRemoved && id.equals(userService.findCurrentUser().getId())) {
            return "redirect:/logout";
        }
        return "redirect:/admin?lastAdmin=" + !adminRoleRemoved;
    }
}
