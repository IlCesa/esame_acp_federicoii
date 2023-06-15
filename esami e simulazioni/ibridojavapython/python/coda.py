import threading

class Coda():
    def __init__(self, size):
        self.coda = []
        self.size = size
        self.tail = self.head = self.elem = 0
        self.lock = threading.RLock()
        self.cv_prod = threading.Condition(lock=self.lock)
        self.cv_cons = threading.Condition(lock=self.lock)

    def getSize(self):
        return self.size
    
    def isFull(self):
        return self.elem == self.size
    
    def isEmpty(self):
        return self.elem == 0

    def inserisci(self, val):
        self.lock.acquire()

        while(self.isFull()):
            self.cv_prod.wait()
        
        
        self.coda.append(val)
        self.elem+=1
        self.cv_cons.notify()
        self.lock.release()

    def preleva(self):
        self.lock.acquire()

        while(self.isEmpty()):
            self.cv_cons.wait()
        
        val = self.coda.pop()
        self.elem-=1
        self.cv_prod.notify()
        self.lock.release()

        return val



