package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    MessageRepository messageRepository;

    @RequestMapping("/")
    public String messageForm (Model model){
        model.addAttribute("messages", messageRepository.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String listNow (Model model){
        model.addAttribute("message", new Message());
        return "listform";
    }
    @PostMapping("/process")
    public String processList (@Valid @ModelAttribute("message") Message message, BindingResult result){
        if(result.hasErrors()){
            return "listform";
        }
        messageRepository.save(message);
        return "redirect:/";
    }

    @RequestMapping("/edit/{id}")
    public String updateList (@PathVariable ("id") long id, Model model){
        model.addAttribute("message", messageRepository.findById(id).get());
        return "listform";
    }
    @RequestMapping("/details/{id}")
    public String showList (@PathVariable ("id") long id, Model model){
        model.addAttribute("message", messageRepository.findById(id).get());
        return "show";
    }
    @RequestMapping("/delete/{id}")
    public String delList (@PathVariable ("id") long id){
        messageRepository.deleteById(id);
        return "redirect:/";
    }
}