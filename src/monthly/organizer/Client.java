/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monthly.organizer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author camen
 */
public class Client {
    
    public static String[] behaviors = {"Attending", "fineMotor", "grossMotor", "motorImitation", "routine", "visualPerformance", 
        "intraverbals", "label", "receptiveLanguage", "request", "socialInteraction", "syntaxGrammar", 	
        "vocalImitation", "chores", "dressing", "eating", "grooming", "playLeisure", "toileting", "vocational", 	
        "socialSkills", "groupInstruction", "pragmaticLanguage", "socialInteractions", "math", "reading", "spelling", 
        "writing", "academics", "cognitiveFunctioning", "dailyLiving", "language", "readiness", "safety", 
        "social"};
    
    public final String name;
    
    public double[] behaviorValues = new double[behaviors.length];
    
    public WeekYear[] weekValues = new WeekYear[53*4];
    
    public Client(String name){
        for(int i = 0; i < behaviors.length; i ++)
            behaviorValues[i] = 0;
        this.name = name;
        int x = 0;
        for(int i = 0; i < 52; i++){
            for(int j = 3; j >= 0; j--){
                weekValues[x] = new WeekYear(i+1, LocalDate.now().getYear() - j, behaviorValues);
                x++;
            }
            
        }

    }
    
    public String getName(){
        return this.name;
    }
    
    public void addValue(int weekNum, double value, int beh){
        weekValues[weekNum - 1].setValue(value);
        this.behaviorValues[beh] = value;
    }
    
    public String getValue(int weekNum, int beh){
        return behaviorValues.get(beh)[weekNum] + "";
    }
    
}
