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
public class GetChat extends ListParentAjax{
    
    private String message = "If I had a moe. story to tell you here is where I would tell it. Unfortunatley I'm only a day old so I have no stories for you.";
    private String responseMessage = listOpen + message + listClose;
    
    public GetChat() {
        super();
        this.setResponseMessage(responseMessage);
    }
}
