/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milesgwood.mavenproject2.controller;

import com.opensymphony.xwork2.ActionSupport;
import java.io.InputStream;

/**
 *
 * @author vicetad
 */
public class NetbeansTomcatTest extends ActionSupport{
    
    protected InputStream inputStream;
    
    @Override
    public String execute()
    {
        return SUCCESS;
    }
}
