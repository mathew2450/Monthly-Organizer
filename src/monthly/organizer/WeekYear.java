/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monthly.organizer;

/**
 *
 * @author camen
 */
public class WeekYear {
    
    private final int week;
    private final int year;
    public double[] value = new double[Client.behaviors.length];
    
    
    public WeekYear(int x, int y, double[] val){
        this.week = x;
        this.year = y;  
        this.value = val;
    }

    public int getWeek() {
        return this.week;
    }

    public int getYear() {
        return this.year;
    }

    public void setValue(int[] val) {
        this.value = val;
    }
    
}
