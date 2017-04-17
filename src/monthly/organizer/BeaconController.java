/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monthly.organizer;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 *
 * @author camen
 * controller for showing values pulled from excel sheets
 */
public class BeaconController {
    //list of textfields for possible replacements
    @FXML
    TextField attending, fineMotor, grossMotor, motorImitation, routine, visualPerformance, 
        intraverbals, label, receptiveLanguage, request, socialInteractions, syntaxGrammer, 	
        vocalImitation, chores, dressing, eating, grooming, playLeisure, toileting, vocational, 	
        socialSkills, group, pragmaticLanguage, math, reading, spelling, 
        writing, academics, cognitiveFunctioning, dailyLiving, language, safety, 
        social, readiness;
    //next and previous client selections
    @FXML
    JFXButton next, previous;
    //variable to keep track of which client we are on
    int currentClient = 0;
    //label for current client name
    @FXML
    JFXTextField clientName;
    //array to make filling textfields easier
    private ArrayList<TextField> textFields = new ArrayList<>();
    //int variable for keeping track of relative weekNum
    int weekNum = 0;
    //datepicker for picking week
    @FXML
    DatePicker calendar;
    //arraylist for storing clients
    private ArrayList<Client> clients = new ArrayList<>();
    //basic contructor
    public BeaconController(){
        
    }
    //sets all the textfields to their appropriate values from the clients class array
    @FXML
    public void setFields(boolean init){
        if( init ){
        System.out.println("INITIALIZING...");
        textFields.add(attending);
        textFields.add(fineMotor);
        textFields.add(grossMotor);
        textFields.add(motorImitation);
        textFields.add(routine);
        textFields.add(visualPerformance);
        textFields.add(intraverbals);
        textFields.add(label);
        textFields.add(receptiveLanguage);
        textFields.add(request);
        textFields.add(socialInteractions);
        textFields.add(syntaxGrammer);
        textFields.add(vocalImitation);
        textFields.add(chores);
        textFields.add(dressing);
        textFields.add(eating);
        textFields.add(grooming);
        textFields.add(playLeisure);
        textFields.add(toileting);
        textFields.add(vocational);
        textFields.add(socialSkills);
        textFields.add(group);
        textFields.add(pragmaticLanguage);
        textFields.add(math);
        textFields.add(reading);
        textFields.add(spelling);
        textFields.add(writing);
        textFields.add(academics);
        textFields.add(cognitiveFunctioning);
        textFields.add(dailyLiving);
        textFields.add(language);
        textFields.add(safety);
        textFields.add(social);
        textFields.add(readiness);
        calendar.setValue(LocalDate.now());
        }
        weekNum = (calendar.getValue().getYear() - 2014)*53 + calendar.getValue().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        //System.out.println(((calendar.getValue().getYear() - 2014) * 53) + "weekNums to add");
        //System.out.println(calendar.getValue().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) + "current weekNum");
        //System.out.println("Updating Values for WeekNum: " + weekNum);
        clientName.setText(clients.get(currentClient).getName());
        for(int i = 0; i < textFields.size(); i ++){
            //System.out.println("Setting value: " + textFields.get(i).toString() + " as: " + clients.get(currentClient).getValue(weekNum, i) + ""); //+ clients.get(currentClient).getValue(((calendar.getValue().getYear() - 2014) * 53) + calendar.getValue().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR), i) + "");
            textFields.get(i).setText(clients.get(currentClient).getValue(weekNum+1, i) + "");
            }
        previous.setDisable(true);
        calendar.setOnAction((ActionEvent arg0) -> {
            setFields(false);
        });
    }
    //adds a client to this controllers array list from outside class
    public void addClients(ArrayList<Client> clients){
        System.out.println("All clients added");
        this.clients.addAll(clients);
        clients.forEach((names)->{
            System.out.println(names.getName());
        });
    }
    //next button action controller, gets next clients information and fills text fields
    @FXML
	void nextClient(ActionEvent event) throws IOException{
            System.out.println("Loading values for next Client");
            currentClient ++;
            for(int i = 0; i < textFields.size(); i ++){
                textFields.get(i).setText(clients.get(currentClient).getValue(weekNum, i) + "");
            }
            clientName.setText(clients.get(currentClient).getName());
            if(currentClient == clients.size()-1)
                next.setDisable(true);
            else
                next.setDisable(false);
            previous.setDisable(false);
        } 
    //previous button action controller, gets previous clients information and fills text fields   
    @FXML
        void prevClient(ActionEvent event) throws IOException{
            System.out.println("Loading values for previous Client");
                currentClient --;
                for(int i = 0; i < textFields.size(); i ++){
                    textFields.get(i).setText(clients.get(currentClient).getValue(weekNum, i) + "");
                }
                clientName.setText(clients.get(currentClient).getName());
            if(currentClient == 0)
                previous.setDisable(true);
            else
                previous.setDisable(false);
            next.setDisable(false);
        }
        
        public ArrayList<Client> getClients(){
         return   clients;
        }
        
}
