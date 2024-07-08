package com.aclc.InventoryManagementSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReactAppController {
    @GetMapping(value = {"/{path:[^\\.]*}", "/"})
    public String forward() {
        return "forward:/index.html";
    }
}