import socket

class IService():
    '''Interfaccia informale che rappresenta la procedura remota'''
    def servizio():
        pass

class ClientProxy(IService):
    def __init__(self):
        pass

    def servizio(self):
        s = socket.socket(family=socket.AF_INET, type=socket.SOCK_STREAM)
        s.connect(("localhost",8080))
        s.send("msg".encode("utf-8"))
        data = s.recv(1024)


if(__name__ == "__main__"):
    p = ClientProxy()
    p.servizio()




