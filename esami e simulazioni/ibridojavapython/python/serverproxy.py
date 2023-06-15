import socket

class ServerInterface():
    '''interfaccia informale per la definizione dei metodi remoti'''
    def sendResponse(self):
        pass

class ServerProxy(ServerInterface):
    def __init__(self):
        self.s = socket.socket(family=socket.AF_INET, type=socket.SOCK_DGRAM)


    def sendResponse(self, message):
        self.s.sendto((str(message) + "\n").encode("utf-8"), ("localhost", 8080))



