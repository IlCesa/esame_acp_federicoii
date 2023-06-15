package client;

public class ServerImpl extends ServerSkeleton{

    public ServerImpl(){
        super();
    }

    @Override
    public void sendResponse(int v) {
        System.out.println(v);
    }
    
}
