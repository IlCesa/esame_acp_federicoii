import stomp, time
import threading

def threadTask(body):
    print(body)

class MyListener(stomp.ConnectionListener):
    def __init__(self):
        pass
    def on_message(self, frame):
        #print(frame.body)
        t = threading.Thread(target=threadTask, args=(frame.body,))
        t.start()

if(__name__ == "__main__"):
    conn = stomp.Connection([("localhost",61613)], auto_content_length=False)
    conn.connect(wait=True)
    mylistener = MyListener()
    conn.subscribe(destination="/queue/messages", ack='auto',id=1)
    conn.set_listener('', listener=mylistener)

    while(True):
        pass
    
    conn.disconnet()