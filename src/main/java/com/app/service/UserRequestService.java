package com.app.service;

import com.app.model.user.UserRequest;
import com.app.repository.UserRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRequestService {

    private final UserRequestRepository userRequestRepository;

    public UserRequestService(UserRequestRepository userRequestRepository) {
        this.userRequestRepository = userRequestRepository;
    }

    public void addRequestToUser(String username, String methodName, List<String> parameters, String output) {
        UserRequest userRequest = new UserRequest(username, methodName, parameters.toString(), output);
        userRequestRepository.save(userRequest);
    }


}
