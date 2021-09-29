package com.example.Mimimimetr.Controllers;

import com.example.Mimimimetr.domain.Cat;
import com.example.Mimimimetr.repository.CatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.Mimimimetr.Service.CatService;

import java.util.*;

@ComponentScan
@Controller
public class MainController {

    @Autowired
    private CatRepo catRepo;
    @Autowired
    private CatService catService;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(value = "name", required = false, defaultValue = "Ай-Новус") String name, Model model) {
        model.addAttribute("name", name);
        catService.initialCreatingCats();
        catService.cleanUncheckedCombinationsOfName();
        List<String[]> combinations = catService.createListOfCombinations();
        for (String[] pairOfNames : combinations) {
            catService.fillUncheckedCombinationsofName(pairOfNames[0].intern(), pairOfNames[1].intern());
        }
        return "greeting";
    }

    @GetMapping("/play")
    public String play(Model model) {
        List<String[]> combinations = catService.createListOfCombinations();
        model.addAttribute("steps",catService.getUncheckedCombinationsOfName().size());
        List<String[]> copyOfCombinations = new ArrayList<>(catService.getUncheckedCombinationsOfName());
        if (catService.getUncheckedCombinationsOfName().isEmpty()) {
            return "redirect:/results";
        }
        for (String[] str : copyOfCombinations) {
            if (catService.getUncheckedCombinationsOfName().size() != 0) {
                for (String[] pairOfNames : combinations) {
                    if (((str[0].intern() == pairOfNames[0].intern()) && (str[1].intern() == pairOfNames[1].intern())) || ((str[0].intern() == pairOfNames[1].intern()) && (str[1].intern() == pairOfNames[0].intern()))) {
                        model.addAttribute("cat1", catRepo.findByName(pairOfNames[0]));
                        model.addAttribute("cat2", catRepo.findByName(pairOfNames[1]));
                        catService.updateUncheckedCombinationsOfName(pairOfNames[0].intern(), pairOfNames[1].intern());
                    }
                }

            }
            break;
        }
        return "play";
    }

    @PostMapping(value = "play/leftCatLiked")
    public String leftCatLiked(@ModelAttribute("cat1") Cat cat) {
        catService.updateCat(cat);
        return "redirect:/play";
    }

    @PostMapping("play/rightCatLiked")
    public String rightCatLiked(@ModelAttribute("cat2") Cat cat) {
        catService.updateCat(cat);
        return "redirect:/play";
    }

    @PostMapping("/reset")
    public String reset(){
        catRepo.deleteAll();
        catService.initialCreatingCats();
        return "redirect:/results";
    }

    @GetMapping("/results")
    public String results(Model model) {
        Iterable<Cat> catSequence = catRepo.findAll();
        List<Cat> catList = new ArrayList<>();
        catList.addAll((Collection<? extends Cat>) catSequence);
        List<Cat> sortedCatList = catList.stream().sorted(((Cat o1, Cat o2) -> o2.getLikeAmount() - o1.getLikeAmount())).toList();
        ArrayList<Cat> sortedCatArrayList = new ArrayList<>();
        sortedCatArrayList.addAll(sortedCatList);
        model.addAttribute("catList", sortedCatArrayList);
        return "results";
    }
}
