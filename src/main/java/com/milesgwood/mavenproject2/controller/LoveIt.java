/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milesgwood.mavenproject2.controller;

/**
 *
 * @author vice
 */
public class LoveIt extends ListParentAjax{
    
    private String message = "Awesome! I love that recording too. What do you like about it? If you don't want to chat about that song how about you tell me your favorite moe. story. You have any moe. stories?";
    private String responseMessage = listOpen + message + listClose;
    
    public LoveIt() {
        super();
        this.setResponseMessage(responseMessage);
    }
}
