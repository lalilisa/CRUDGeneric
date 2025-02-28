package com.trimv.crudgeneric.service;

import com.trimv.crudgeneric.domain.Users;
import com.trimv.crudgeneric.repository.UserRepository;
import com.trimv.crudgeneric.service.impl.CRUDServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService extends CRUDServiceImpl<Users,Long> {


    public UserService(UserRepository userRepository) {
        super(userRepository);
    }
}
