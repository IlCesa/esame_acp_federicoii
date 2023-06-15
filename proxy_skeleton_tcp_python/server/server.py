import socket

class IService():
    '''Interfaccia informale che rappresenta la procedura remota'''
    def servizio(self):
        pass

class ClientSkeleton(IService):
    def __init__(self):
        pass

    def runSkeleton(self):
        s = socket.socket(family=socket.AF_INET, type=socket.SOCK_STREAM)
        s.bind(("localhost",8080))
        s.listen(1)
        while(True):
            conn, addr = s.accept()
            data = conn.recv(1024)
            print(f"[SERVER] recv: {data}")
            #simulo ricezione comando servizio
            print(f"[SERVER] called SERVIZIO")
            self.servizio()
            stringa = (str(data) + "added")
            print(f"[SERVER] sending {stringa}")
            conn.send(stringa.encode("utf-8"))
        #accetto connessioni e avvio thread




class ClientImpl(ClientSkeleton):
    def __init__(self):
        pass

    def servizio(self):
        print("bell")
        

    





if(__name__ == "__main__"):
    p = ClientImpl()
    p.runSkeleton()




