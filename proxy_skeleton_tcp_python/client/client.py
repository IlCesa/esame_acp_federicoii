import socket

class IService():
    '''Interfaccia informale che rappresenta la procedura remota'''
    def servizio(self):
        pass

class ClientProxy(IService):
    def __init__(self):
        pass

    def servizio(self):
        s = socket.socket(family=socket.AF_INET, type=socket.SOCK_STREAM)
        s.connect(("localhost",8080))
        msg = "msgClient"
        print(f"[CLIENT] sending: {msg}")
        s.send(msg.encode("utf-8"))
        print(f"[CLIENT] waiting response...")
        data = s.recv(1024)
        print(f"[CLIENT] received: {data}")
        s.close()


if(__name__ == "__main__"):
    p = ClientProxy()
    p.servizio()




