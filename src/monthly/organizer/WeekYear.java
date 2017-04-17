/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monthly.organizer;

/**
 *
 * @author camen
 * class to keep track of the week year and the values for each behavior possible
 */
public class WeekYear {
    //week number
    private final int week;
    //year
    private final int year;
    //the array of behavior values, the index represents the behavior from the static array in Client
    public double[] value = new double[Client.behaviors.length];
    
    //initializing constructor to set all the values, the array is initially all 0's
    public WeekYear(int x, int y){
        this.week = x;
        this.year = y;  
        for(int i = 0; i < value.length; i ++)
            value[i] = 0;
    }
    //returns the week number
    public int getWeek() {
        return this.week;
    }
    //returns the year of this weekYear
    public int getYear() {
        return this.year;
    }
    //sets the value of the behaviors array
    public void setValue(double val, int beh) {
        this.value[beh] = val;
    }
    //returns the value for the index provided of the behaviors array
    public double getValue(int i){
        return this.value[i];
    }
    //ruturns the whole behavior array
    public double[] getWeekValues(){
        return this.value;
    }
    
}
