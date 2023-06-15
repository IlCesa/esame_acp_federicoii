package manager;

public class Subscriber {

    private int componentID;
    private int port;

    public Subscriber(int componentID, int port) {
        this.componentID = componentID;
        this.port = port;
    }

    public int getComponentID() {
        return componentID;
    }

    public int getPort() {
        return port;
    }

}
