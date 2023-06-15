package services;

import java.io.Serializable;

public class AlertNotification implements Serializable{
    
    private int componentID;
    private int criticality;

    public AlertNotification(int componentID, int criticality) {
        this.componentID = componentID;
        this.criticality = criticality;
    }

    public int getComponentID() {
        return componentID;
    }

    public int getCriticality() {
        return criticality;
    }


    @Override
    public String toString() {
        return "AlertNotification [componentID=" + componentID + ", criticality=" + criticality + "]";
    }
   
}
