/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milesgwood.mavenproject2.controller;

import static com.opensymphony.xwork2.Action.SUCCESS;
import com.milesgwood.mavenproject2.hibernate.*;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is being updated so that it follows RESTFUL standards.
 * @author vice
 */
public class StartBot extends ListParentAjax{
    private static boolean firstExecution = true;
    
    private String botMessage = "Chat with me to sift through the 28 years of live moe. recordings in search of forgotten gems. If you're busy working you can just listen, and I'll keep the music flowing.";
    private String botMessage2 = "What do you want to do?";
    private String responseMessage = listOpen + botMessage + listClose + listOpen + botMessage2 + listClose;
    
    public StartBot() {
        super();
        this.setResponseMessage(responseMessage);
    }

    /**
     * Creates the database resources and sends out the initial message.
     * @return SUCCESS
     */
    public String execute() throws Exception {
        if(firstExecution) {
            Hibernate hibernate = new Hibernate();
            firstExecution = false;
        }
        
        try {
            inputStream = new ByteArrayInputStream(responseMessage.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(StartBot.class.getName()).log(Level.SEVERE, null, ex);
        }
        return SUCCESS;
    }
}
