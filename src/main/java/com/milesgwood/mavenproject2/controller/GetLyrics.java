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
public class GetLyrics extends ListParentAjax{
    
    private String message = "Hey I'm no singer! Go find the lyrics yourself.";
    private String responseMessage = listOpen + message + listClose;
    
    public GetLyrics() {
        super();
        this.setResponseMessage(responseMessage);
    }
}
