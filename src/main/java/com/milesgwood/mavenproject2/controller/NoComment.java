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
public class NoComment extends ListParentAjax{
    
    private String message = "That's fine. We don't need to talk now. Just enjoy the rest of this song.";
    private String responseMessage = listOpen + message + listClose;
    
    public NoComment() {
        super();
        this.setResponseMessage(responseMessage);
    }
}
