/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monthly.organizer;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author camen
 * Class for storing client information, stores static array for behavior comparing
 * stores its own behavior array for each value and then stores the array into
 * the correct weekYear number so that it can be called from properly.
 */
public class Client {
    //static variable for storing list of behaviors clients may have
    public static String[] behaviors = {"attending", "fineMotor", "grossMotor", "motorImitation", "routine", "visualPerformance", 
        "intraverbals", "label", "receptiveLanguage", "request", "socialInteractions", "syntaxGrammar", 	
        "vocalImitation", "chores", "dressing", "eating", "grooming", "playLeisure", "toileting", "vocational", 	
        "socialSkills", "group", "pragmaticLanguage", "math", "reading", "spelling", 
        "writing", "academics", "cognitiveFunctioning", "dailyLiving", "language", "safety", 
        "social", "readiness"};
    //name of the client
    public final String name;
    //an array for storing all the weekYears for the past 4 years we use this to store the information for each behavior each week
    public ArrayList<WeekYear> weekValues = new ArrayList<>();
    //constructor for setting client name and initializing the behaviorValues
    public Client(String name){
        this.name = name;
        for(int i = 1; i <= 53; i++){
            for(int j = 3; j >= 0; j--){
                weekValues.add(new WeekYear(i, LocalDate.now().getYear() - j));
            }
            
        }

    }
    //returns the name of the client
    public String getName(){
        return this.name;
    }
    //gets array of current week, fills the value and then saves it back into the correct week
    public void addValue(int weekNum, double value, int beh){
        weekValues.get(weekNum).setValue(value, beh);        
    }
    //gets the value of the behavior i at the week number
    public double getValue(int weekNum, int i){
        return weekValues.get(weekNum).getValue(i);
    }
    
}
