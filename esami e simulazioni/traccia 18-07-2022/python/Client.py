import stomp
import threading
import socket

def requestThread(param):
    if(param == "vendi"):
        pass

class Service:
    def richiesta(self):
        pass

class Skeleton(Service):
    def runSkeleton(self):
        print("Connecting Server:")
        ss = socket.socket(family=socket.AF_INET, type=socket.SOCK_STREAM)
        ss.bind(("localhost", 8080))
        ss.listen(1)
        conn,addr = ss.accept()
        data = conn.recv(1024)
        threading.Thread(target=requestThread, args=(data,))
        #conn.close()
        #ss.close()
        #self.richiesta()


class ServerImpl(Skeleton):
    def richiesta(self):
        print("Implementazione richiesta")


def main():
    conn = stomp.Connection([("localhost",61613)], auto_content_length=False)
    conn.connect()
    conn.send("/topic/provaaaa", "message")
    conn.disconnect()

    s = ServerImpl()
    s.runSkeleton()




if(__name__ == "__main__"):
    main()