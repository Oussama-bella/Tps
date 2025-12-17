package com.ouss.msa.resttemplatefront.controller;

import com.ouss.msa.resttemplatefront.domaine.EmpVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;

@Controller
public class EmpController {

    private final RestTemplate restTemplate;

    @Value("${server.rest.url}")
    private String url;

    public EmpController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    public String showWelcomeFile(Model m) {
        return "index";
    }

    @GetMapping("/empform")
    public String showform(Model m) {
        m.addAttribute("empVo", new EmpVo());
        return "empform";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("empVo") EmpVo emp, RedirectAttributes redirectAttrs) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EmpVo> entity = new HttpEntity<>(emp, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            HttpStatusCode statusCode = response.getStatusCode();
            System.out.println("Response Status Code: " + statusCode);

            if (statusCode.is2xxSuccessful()) {
                redirectAttrs.addFlashAttribute("successMessage", "Employé ajouté avec succès.");
                return "redirect:/viewemp";
            } else {
                String msg = "Backend returned status: " + statusCode;
                redirectAttrs.addFlashAttribute("errorMessage", msg);
                return "redirect:/empform";
            }
        } catch (RestClientException ex) {
            ex.printStackTrace();
            redirectAttrs.addFlashAttribute("errorMessage", "Erreur lors de l'appel au backend: " + ex.getMessage());
            return "redirect:/empform";
        }
    }

    @GetMapping("/viewemp")
    public String viewemp(Model m) {
        EmpVo[] list = new EmpVo[0];
        try {
            ResponseEntity<EmpVo[]> response = restTemplate.getForEntity(url, EmpVo[].class);
            HttpStatusCode statusCode = response.getStatusCode();
            System.out.println("Response Status Code: " + statusCode);
            if (statusCode == HttpStatus.OK && response.getBody() != null) {
                list = response.getBody();
            } else {
                m.addAttribute("errorMessage", "Backend returned: " + statusCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("errorMessage", "Erreur lors de la récupération des employés: " + e.getMessage());
        }
        m.addAttribute("list", Arrays.asList(list));
        return "viewemp";
    }

    @GetMapping("/editemp/{id}")
    public String edit(@PathVariable Long id, Model m) {
        try {
            ResponseEntity<EmpVo> response = restTemplate.getForEntity(url + "/id/" + id, EmpVo.class);
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                m.addAttribute("empVo", response.getBody());
                return "empeditform";
            } else {
                m.addAttribute("errorMessage", "Impossible de récupérer l'employé: " + response.getStatusCode());
                return "redirect:/viewemp";
            }
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("errorMessage", "Erreur: " + e.getMessage());
            return "redirect:/viewemp";
        }
    }

    @PostMapping("/editsave")
    public String editsave(@ModelAttribute("empVo") EmpVo emp, RedirectAttributes redirectAttrs) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EmpVo> entity = new HttpEntity<>(emp, headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                redirectAttrs.addFlashAttribute("successMessage", "Employé modifié.");
                return "redirect:/viewemp";
            } else {
                redirectAttrs.addFlashAttribute("errorMessage", "Backend: " + response.getStatusCode());
                return "redirect:/empform";
            }
        } catch (RestClientException ex) {
            ex.printStackTrace();
            redirectAttrs.addFlashAttribute("errorMessage", "Erreur lors de l'appel au backend: " + ex.getMessage());
            return "redirect:/empform";
        }
    }

    @GetMapping("/deleteemp/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(url + "/" + id, HttpMethod.DELETE, entity, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                redirectAttrs.addFlashAttribute("successMessage", "Employé supprimé.");
            } else {
                redirectAttrs.addFlashAttribute("errorMessage", "Backend: " + response.getStatusCode());
            }
        } catch (RestClientException ex) {
            ex.printStackTrace();
            redirectAttrs.addFlashAttribute("errorMessage", "Erreur lors de l'appel au backend: " + ex.getMessage());
        }
        return "redirect:/viewemp";
    }
}
