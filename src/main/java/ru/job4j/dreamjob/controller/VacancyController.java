package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.dreamjob.dto.FileDto;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.model.Vacancy;
import ru.job4j.dreamjob.repository.MemoryVacancyRepository;
import ru.job4j.dreamjob.service.SimpleVacancyService;
import ru.job4j.dreamjob.service.VacancyService;
import ru.job4j.dreamjob.service.CityService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@ThreadSafe
@Controller
@RequestMapping("/vacancies") /* Rabotat' s kandidatami budem po URI /vacancies/** */
public class VacancyController {

    private final VacancyService vacancyService;

    private final CityService cityService;

    public VacancyController(VacancyService vacancyService, CityService cityService) {
        this.vacancyService = vacancyService;
        this.cityService = cityService;
    }

//    private final VacancyService vacancyService = SimpleVacancyService.getInstance();

    @GetMapping
    public String getAll(Model model, HttpSession session) {
        var user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Gost");
        }
        model.addAttribute("user", user);
        model.addAttribute("vacancies", vacancyService.findAll());
        return "vacancies/list";
    }

    @GetMapping("/create")
    public String getCreationPage(Model model, HttpSession session) {
        var user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Gost");
        }
        model.addAttribute("user", user);
        model.addAttribute("cities", cityService.findAll());
        return "vacancies/create";
    }

//    @PostMapping("/create")
//    public String create(HttpServletRequest request) {
//        var title = request.getParameter("title");
//        var description = request.getParameter("description");
//        vacancyRepository.save(new Vacancy(0, title, description, LocalDateTime.now()));
//        return "redirect:/vacancies";
//    }

    @PostMapping("/create")
    public String create(@ModelAttribute Vacancy vacancy, @RequestParam MultipartFile file, Model model) {
        try {
            vacancyService.save(vacancy, new FileDto(file.getOriginalFilename(), file.getBytes()));
            return "redirect:/vacancies";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id, HttpSession session) {
        var user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Gost");
        }
        model.addAttribute("user", user);
        var vacancyOptional = vacancyService.findById(id);
        if (vacancyOptional.isEmpty()) {
            model.addAttribute("message", "Vakansija s ukazannym identifikatorom ne najdena");
            return "errors/404";
        }
        model.addAttribute("cities", cityService.findAll());
        model.addAttribute("vacancy", vacancyOptional.get());
        return "vacancies/one";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Vacancy vacancy, @RequestParam MultipartFile file, Model model) {
        try {
            var isUpdated = vacancyService.update(vacancy, new FileDto(file.getOriginalFilename(), file.getBytes()));
            if (!isUpdated) {
                model.addAttribute("message", "Vakansija s ukazannym identifikatorom ne najdena");
                return "errors/404";
            }
            return "redirect:/vacancies";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id, HttpSession session) {
        var user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Gost");
        }
        model.addAttribute("user", user);
        var isDeleted = vacancyService.deleteById(id);
        if (!isDeleted) {
            model.addAttribute("message", "Vakansija s ukazannym identifikatorom ne najdena");
            return "errors/404";
        }
        return "redirect:/vacancies";
    }
}