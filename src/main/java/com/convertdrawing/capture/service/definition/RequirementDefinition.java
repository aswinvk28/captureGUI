/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.convertdrawing.capture.service.definition;

import java.util.HashMap;

/**
 *
 * @author aswin.vijayakumar
 */
public class RequirementDefinition
{
    public String subjectPrior;
    public String subjectPost;
    public String subjectCategory;
    
    public String predicatePrior;
    public String predicatePost;
    public String predicateCategory;
    
    public String sCat;
    public String pCat;
    
    public RequirementDefinition(String sCat, String pCat)
    {
        this.sCat = sCat;
        this.pCat = pCat;
    }
    
    public boolean validate(RequirementDefinition[] definitions)
    {
        if(definitions.length > 0) {
            HashMap<String[], String[]> map = new HashMap<String[], String[]>();
            for (RequirementDefinition def : definitions) {
                String[] key = new String[]{def.sCat, def.subjectCategory};
                String[] value = new String[]{def.pCat, def.predicateCategory};
                map.put(key, value);
            }
            String[] currentKey = new String[]{this.sCat, this.subjectCategory};
            String[] currentValue = new String[]{this.pCat, this.predicateCategory};
            if(map.containsKey(currentKey) && map.containsValue(currentValue)) {
                return false;
            }
            return true;
        }
        return true;
    }
}
