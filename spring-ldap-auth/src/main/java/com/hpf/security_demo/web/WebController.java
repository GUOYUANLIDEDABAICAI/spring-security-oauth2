package com.hpf.security_demo.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.security.RolesAllowed;

@Controller
public class WebController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/hello")
   // @PreAuthorize("hasRole('ROLE_DEVELOPERS')")
    //@RolesAllowed({"ROLE_DEVELOPERS"})
    //@PreAuthorize("hasAnyAuthority({'ROLE_DEVELOPERS'})")
    public String hello() {
        return "hello";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    @ResponseBody
   // @PreAuthorize("hasRole('ROLE_MANAGERS')")
   // @RolesAllowed({"ROLE_MANAGERS"})
    //@PreAuthorize("hasAnyAuthority({'ROLE_MANAGERS'})")
    public String test(){
        return "test success";
    }
}
