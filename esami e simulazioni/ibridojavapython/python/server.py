import threading, stomp, time
from coda import Coda
from serverproxy import ServerProxy

def serverThread(msg, coda):
    serverProxy = ServerProxy()
    vals = msg.split('#')
    val = vals[1]
    op = vals[0]
    
    if(op == "deposita"):
        coda.inserisci(val)
    else:
        print("sono qui")
        cVal = coda.preleva()
        print("invio..")
        serverProxy.sendResponse(cVal)


class MyListener(stomp.ConnectionListener):
    def __init__(self):
        self.coda = Coda(10)
    def on_message(self, frame):
        print(frame.body)
        t = threading.Thread(target=serverThread, args=(frame.body,self.coda))
        t.start()
        #apro un thread, nel trhead vedo che tipo di richiesta è,
        #prelevo o immetto un valore, 


if(__name__ == "__main__"):

    conn = stomp.Connection([("localhost",61613)], auto_content_length=False)
    mylistener = MyListener()
    conn.set_listener('',listener=mylistener)
    conn.connect(wait= True)
    conn.subscribe(destination='/queue/req', ack='auto', id=1)
    while(True):
        pass
