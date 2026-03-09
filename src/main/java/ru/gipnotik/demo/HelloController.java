package ru.gipnotik.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller 
public class HelloController {

    @GetMapping("/")
    public String sayHello(Model model) {
        // Добавляем атрибуты, которые будут доступны в HTML шаблоне
        model.addAttribute("message", "Привет! Твой Spring Boot 4.0 проект работает 🚀");
        model.addAttribute("name", "Андрей");
        model.addAttribute("year", 2026);
        
        // Возвращаем имя HTML файла (без расширения)
        return "index";
    }
}