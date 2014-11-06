/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package logicPackage;

/**
 *
 * @author Nacho
 */
public class SubController {
    private int speed;
    SubmarineServer submarine;
    
    public SubController(SubmarineServer submarine, int speed){
        this.submarine = submarine;
        this.speed = speed;
    }
}
