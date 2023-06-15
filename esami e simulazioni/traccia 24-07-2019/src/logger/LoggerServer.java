package logger;

public class LoggerServer {
    public static void main(String[] args) {
        LoggerImpl lImpl = new LoggerImpl();
        lImpl.runSkeleton();
    }
}
