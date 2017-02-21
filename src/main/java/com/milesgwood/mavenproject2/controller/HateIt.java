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
public class HateIt extends ListParentAjax{
    
    private String message = "Sorry you didn't like that one. There are over 10,000 recordings to sort through. One is bound to be bad eventually.";
    private String responseMessage = listOpen + message + listClose;
    
    public HateIt() {
        super();
        this.setResponseMessage(responseMessage);
    }
}
