/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monthly.organizer;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 *
 * @author camen
 */
public class BeaconController {
    
    @FXML
    TextField Attending, fineMotor, grossMotor, motorImitation, routine, visualPerformance, 
        intraverbals, label, receptiveLanguage, request, socialInteraction, syntaxGrammar, 	
        vocalImitation, chores, dressing, eating, grooming, playLeisure, toileting, vocational, 	
        socialSkills, groupInstruction, pragmaticLanguage, socialInteractions, math, reading, spelling, 
        writing, academics, cognitiveFunctioning, dailyLiving, language, readiness, safety, 
        social;
    
    @FXML
    JFXButton next, previous;
    
    int currentClient = 0;
    
    @FXML
    JFXTextField clientName;
    
    TextField[] textFields = {Attending, fineMotor, grossMotor, motorImitation, routine, visualPerformance, 
        intraverbals, label, receptiveLanguage, request, socialInteraction, syntaxGrammar, 	
        vocalImitation, chores, dressing, eating, grooming, playLeisure, toileting, vocational, 	
        socialSkills, groupInstruction, pragmaticLanguage, socialInteractions, math, reading, spelling, 
        writing, academics, cognitiveFunctioning, dailyLiving, language, readiness, safety, 
        social};
    
    @FXML
    DatePicker calendar;
    
    private ArrayList<Client> clients = new ArrayList<>();
    
    public BeaconController(){
        
    }
    
    public void setFields(){
        for(int i = 0; i < textFields.length; i ++){
                textFields[i].setText(clients.get(i).getValue(calendar.getValue().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR), i));
            }
    }
    
    public void addClient(Client client){
        this.clients.add(client);
    }
    
    @FXML
	void nextClient(ActionEvent event) throws IOException{
            currentClient ++;
            for(int i = 0; i < textFields.length; i ++){
                textFields[i].setText(clients.get(clients.indexOf(clientName.getText())));
            }
        } 
        
    @FXML
        void prevClient(ActionEvent event) throws IOException{
            
        }
}
