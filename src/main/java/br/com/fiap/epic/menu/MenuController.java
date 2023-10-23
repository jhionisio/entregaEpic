package br.com.fiap.epic.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("menus", menuService.findAll());
        return "menu/index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        if (menuService.delete(id)) {
            redirect.addFlashAttribute("success",
                    messageSource.getMessage("menu.delete.success", null, LocaleContextHolder.getLocale()));
        } else {
            redirect.addFlashAttribute("error", "Menu não encontrado");
        }
        return "redirect:/menu";
    }

    @GetMapping("/new")
    public String form(Menu menu) {
        return "menu/form";
    }

    @PostMapping
    public String create(@Valid Menu menu, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return "menu/form";
        }

        menuService.save(menu);
        redirect.addFlashAttribute("success", "Item cadastrado com sucesso");
        return "redirect:/menu";
    }
}
