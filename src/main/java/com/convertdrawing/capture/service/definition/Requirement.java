/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.convertdrawing.capture.service.definition;

import java.io.InputStream;
import java.io.FileInputStream;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.Span;

/**
 *
 * @author aswin.vijayakumar
 */
public class Requirement
{
    protected String text;
    protected boolean valid;
    
    public Requirement()
    {
        
    }
    
    public void setText(String text)
    {
        this.text = text;
    }
    
    public String getText()
    {
        return this.text;
    }
    
    public boolean isValid()
    {
        return this.valid;
    }
    
    public String[] validateText() throws Exception
    {
        try {
            InputStream in = new FileInputStream("META-INF/bin/en-sent.bin");
            SentenceModel model = new SentenceModel(in);
            
            SentenceDetectorME sentenceDetector = new SentenceDetectorME(model);
            
            Span positions[] = sentenceDetector.sentPosDetect(text);
            String sentences[] = sentenceDetector.sentDetect(text);
            
            if (positions.length == sentences.length) {
                return sentences;
            }
        } catch(Exception exception) {
            throw exception;
        }
        return null;
    }
}
