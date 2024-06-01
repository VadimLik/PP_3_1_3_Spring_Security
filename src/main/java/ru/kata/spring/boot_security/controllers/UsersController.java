package ru.kata.spring.boot_security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.models.Role;
import ru.kata.spring.boot_security.models.User;
import ru.kata.spring.boot_security.service.RoleService;
import ru.kata.spring.boot_security.service.UsersService;
import ru.kata.spring.boot_security.validators.UserValidator;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;
import java.util.List;



@Controller
@RequestMapping("/")
public class UsersController {

    private final UsersService usersService;

    private final RoleService roleService;

    private final UserValidator userValidator;


    @Autowired
    public UsersController(UsersService userService, RoleService roleService, UserValidator userValidator) {

        this.usersService = userService;

        this.roleService = roleService;

        this.userValidator = userValidator;

    }

    @GetMapping("/")
    public String toHomePage(){

        return "index";
    }

    @GetMapping("/user")
    public String showUserInfo(Model model, Principal principal){

        User user = usersService.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("Невозможно найти пользователя по username: " + principal.getName()));

        model.addAttribute("user", user);

        return "user";
    }

    @GetMapping("/admin")
    public String getUsers( Model model) {

        model.addAttribute("userlist", usersService.getAllUsers());

        return "users";
    }

    @GetMapping("/admin/new")
    public String newUser(Model model) {

        model.addAttribute("user", new User());

        return "new";
    }

    @GetMapping("/admin/user{id}")
    public String update(@RequestParam("id") long id, Model model) {

        model.addAttribute("user", usersService.getUser(id));

        return "edit";
    }

    @PostMapping("/admin/new")
    public String createUser(@ModelAttribute("user") @Valid  User user, BindingResult bindingResult) {

        userValidator.validate(user, bindingResult);

        if(bindingResult.hasErrors()){

            return "new";
        }

        usersService.saveUser(user);

        return "redirect:/admin";

    }

    @PostMapping("/admin/{id}")
    public String deleteUser(@RequestParam("id") long id) {

        usersService.deleteUser(id);

        return "redirect:/admin";
    }

    @PostMapping("/admin/user{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @RequestParam("id") long id) {

        userValidator.validate(user, bindingResult);

        if(bindingResult.hasErrors()){

            return "edit";
        }

        usersService.updateUser(user, id);

        return "redirect:/admin";
    }

    @ModelAttribute("roles")
    public Collection<Role> getCollectionRole(Model model) {

        Collection<Role> roles = roleService.getAllRoles();

        return roles;
    }
}
