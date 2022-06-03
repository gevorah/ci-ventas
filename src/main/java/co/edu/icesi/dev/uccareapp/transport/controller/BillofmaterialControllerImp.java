package co.edu.icesi.dev.uccareapp.transport.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.icesi.dev.uccareapp.transport.model.prod.Billofmaterial;
import co.edu.icesi.dev.uccareapp.transport.service.BillofmaterialService;

@Controller
public class BillofmaterialControllerImp implements BillofmaterialController {
    
    @Autowired
    BillofmaterialService billofmaterialservice;

    @GetMapping("/billofmaterials")
    public String index(Model model) {
        model.addAttribute("billofmaterials", billofmaterialservice.findAll());
        return "billofmaterials/index";
    }

    @GetMapping("/billofmaterials/add")
    public String showSaveForm(Model model) {
        model.addAttribute("billofmaterial", new Billofmaterial());
        return "billofmaterials/add-billofmaterial";
    }

    @PostMapping("/billofmaterials/add") 
    public String saveBillofmaterial(@Validated @ModelAttribute Billofmaterial billofmaterial, BindingResult bindingResult, 
            Model model, @RequestParam(value = "action", required = true) String action) {
        if (!action.equals("Cancel")) {
            if (bindingResult.hasErrors()) {
                return "billofmaterials/add-billofmaterial";
            }
            
            billofmaterialservice.saveBillofmaterial(billofmaterial);
        }
        return "redirect:/billofmaterials/";
    }

    @GetMapping("/billofmaterials/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<Billofmaterial> billofmaterialop = billofmaterialservice.findById(id);
        if (billofmaterialop == null)
            throw new IllegalArgumentException("Invalid billofmaterial Id:" + id);
        model.addAttribute("billofmaterial", billofmaterialop.get());
        return "billofmaterials/update-billofmaterial";
    }

    @PostMapping("/billofmaterials/edit/{id}")
    public String updateBillofmaterial(@PathVariable("id") Integer id, @RequestParam(value = "action", required = true) String action, 
            @Validated @ModelAttribute Billofmaterial billofmaterial, BindingResult bindingResult, Model model ) {
        if (!action.equals("Cancel")) {
            if (bindingResult.hasErrors()) {
                return "billofmaterials/update-billofmaterial";
            }
            billofmaterialservice.editBillofmaterial(billofmaterial);
        }
        return "redirect:/billofmaterials/";
    }

    @GetMapping("/billofmaterials/del/{id}")
    public String deleteShipmethod(@PathVariable("id") Integer id, Model model) {
        Billofmaterial billofmaterial = billofmaterialservice.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid billofmaterial Id:" + id));
        billofmaterialservice.delete(billofmaterial);
        return "redirect:/billofmaterials/";
    }
}
