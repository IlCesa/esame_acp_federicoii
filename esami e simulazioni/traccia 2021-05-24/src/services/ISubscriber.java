package services;

public interface ISubscriber {
    public abstract void notifyAlert(int criticality);
}
