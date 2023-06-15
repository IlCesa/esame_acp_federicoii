class Coda(object):
    def __init__(self, size):
        self.size = size
        self.coda = []
        self.elems = 0


    def getSize(self):
        return self.size
    
    def isFull(self):
        return self.size == self.elems
    
    def isEmpty(self):
        return self.elems == 0
    
    
    def inserisci(self, valore):
        self.elems = self.elems +1
        self.coda.append(valore)

    def preleva(self):
        self.elems = self.elems - 1
        return self.coda.pop()

        
    
    