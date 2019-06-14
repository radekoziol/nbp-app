package com.app.model.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String username;

    private String name;
    private String input;
    private String output;

    public UserRequest(String username, String name, String input, String output) {
        this.username = username;
        this.name = name;
        this.input = input;
        this.output = output;
    }

    public UserRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }


    @Override
    public String toString() {
        return "UserRequest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", input='" + input + '\'' +
                ", output='" + output + '\'' +
                '}';
    }
}
