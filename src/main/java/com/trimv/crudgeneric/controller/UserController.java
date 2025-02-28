package com.trimv.crudgeneric.controller;

import com.trimv.crudgeneric.domain.Users;
import com.trimv.crudgeneric.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/user/")
public class UserController extends CrudController<Users,Long>{

    protected UserController(UserService service) {
        super(service);
    }
}
