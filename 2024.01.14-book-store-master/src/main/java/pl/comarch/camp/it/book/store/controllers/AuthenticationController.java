package pl.comarch.camp.it.book.store.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.comarch.camp.it.book.store.exceptions.UserAlreadyExistException;
import pl.comarch.camp.it.book.store.exceptions.UserValidationException;
import pl.comarch.camp.it.book.store.model.dto.RegisterUserDTO;
import pl.comarch.camp.it.book.store.services.IAuthenticationService;
import pl.comarch.camp.it.book.store.validators.UserValidator;

@Controller
public class AuthenticationController {
    private final IAuthenticationService authenticationService;

    public AuthenticationController(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String login, @RequestParam String password,
                        HttpSession httpSession) {
        try {
            UserValidator.validateLogin(login);
            UserValidator.validatePassword(password);
        } catch (UserValidationException e) {
            return "redirect:/login";
        }
        this.authenticationService.login(login, password);
        if(httpSession.getAttribute("user") != null) {
            return "redirect:/main";
        }
        return "redirect:/login";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout() {
        this.authenticationService.logout();
        return "redirect:/main";
    }

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("userModel", new RegisterUserDTO());
        return "register";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(@ModelAttribute RegisterUserDTO userDTO) {
        try {
            UserValidator.validateUserDto(userDTO);
            this.authenticationService.register(userDTO);
        } catch(UserValidationException | UserAlreadyExistException e) {
            return "redirect:/register";
        }
        return "redirect:/main";
    }
}
