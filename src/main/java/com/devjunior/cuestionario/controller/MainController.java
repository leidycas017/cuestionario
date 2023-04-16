package com.devjunior.cuestionario.controller;

import com.devjunior.cuestionario.model.QuestionForm;
import com.devjunior.cuestionario.model.Result;
import com.devjunior.cuestionario.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    Result result;
    @Autowired
    QuizService qService;

    Boolean submitted = false;

    @ModelAttribute("result")
    public Result getResult() {
        return result;
    }

    @GetMapping("/")
    public String home() {
        return "index.html";
    }

    @PostMapping("/quiz")
    public String quiz(@RequestParam String username, Model m, RedirectAttributes ra) {
        if(username != null && username.equals("")) {
            ra.addFlashAttribute("warning", "Debe ingresar su nombre para continuar");
            return "redirect:/";
        }

        submitted = false;
        result.setUsername(username);

        QuestionForm qForm = qService.getQuestions();
        m.addAttribute("qForm", qForm);

        return "quiz.html";
    }

    @PostMapping("/submit")
    public String submit(@ModelAttribute QuestionForm qForm, Model m, RedirectAttributes ra) {

        if(Boolean.TRUE.equals(!submitted)) {
            result.setPerfil(qService.getResult(qForm));
            if(result.getPerfil() != null && result.getPerfil().equals("NA")){
                ra.addFlashAttribute("warning", "Hay preguntas sin responder, inténtalo nuevamente");
                return "redirect:/";
            }else{
                qService.saveScore(result);
                submitted = true;
            }
        }
        return "result.html";

    }

    @GetMapping("/score")
    public String score(Model m) {
        List<Result> sList = qService.getTopScore();
        m.addAttribute("sList", sList);

        return "scoreboard.html";
    }

}
