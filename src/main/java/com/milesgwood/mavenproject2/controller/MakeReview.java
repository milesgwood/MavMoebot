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
public class MakeReview extends ListParentAjax{
    
    private String message = "Everyone either loves that song, hates it... or thinks it's just O.K.";
    private String responseMessage = listOpen + message + listClose;
    
    public MakeReview() {
        super();
        this.setResponseMessage(responseMessage);
    }
}
