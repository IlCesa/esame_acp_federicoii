import stomp, multiprocessing

class MyListener(stomp.ConnectionListener):
    def on_message(self, frame):
        print(frame.body)
        conn = stomp.Connection([("localhost", 61613)], auto_content_length=False)
        conn.connect(wait=True)
        conn.send("/queue/res", "a predictin chiavatell ngul")



if(__name__ == "__main__"):
    conn = stomp.Connection([("localhost", 61613)], auto_content_length=False)
    mylistener = MyListener()
    conn.set_listener('', listener = mylistener)
    conn.connect(wait=True)
    conn.subscribe(destination="/queue/req", ack='auto', id=1)
    

    #p = multiprocessing.Process(target=, arg)
    #p.start()

