package com.hpf.security_demo.web;

import com.hpf.security_demo.dao.PersonDao;
import com.hpf.security_demo.dao.UserDao;
import com.hpf.security_demo.entity.Person;
import com.hpf.security_demo.entity.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class WebController {

    @Autowired
    PersonDao personDao;

    @Autowired
    UserDao userDao;

    @RequestMapping("/")
    public String index() {
       /* UserBean user = new UserBean();
        user.setId("cn");
        user.setName("sn");
        user.setStaticPwd("aaa");
        userDao.addUser(user);*/
        return "index";
    }

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    @ResponseBody
    public String test(){
       /* Person person = new Person();
        person.setCn("luci");
        person.setSn("Lucifa");
        person.setUserPassword("11111");
        personDao.createOnePerson(person);*/
        return "test success";
    }
}
