import threading
from coda import Coda
import random
#import logging

NUM_THREAD = 10


def produttore(codaCircolare, posti_disponibili, elem_cons, cv_lock):
    #cv_lock.acquire()
    with posti_disponibili:
        while(codaCircolare.isFull()):
            posti_disponibili.wait()

        #inizio sezione critica
        val = random.randint(1,100)
        print(f"Prodotto:{val}")
        codaCircolare.inserisci(val)
        elem_cons.notify()

    #fine sezione critica

    #cv_lock.release()



def consumatore(codaCircolare, posti_disponibili, elem_cons, cv_lock):
    with elem_cons:
        while(codaCircolare.isEmpty()):
            elem_cons.wait()
        
        val = codaCircolare.preleva()
        posti_disponibili.notify()
        print(f"Consumato:{val}")


    #cv_lock.release()


def main():
    codaCircolare = Coda(2)
    cv_lock = threading.RLock()
    posti_disponibili = threading.Condition(lock=cv_lock)
    elem_cons = threading.Condition(lock=cv_lock)

    threadList = []

    for i in range(NUM_THREAD):
        func = produttore if (i<5) else consumatore
        thread = threading.Thread(target=func, args=(codaCircolare, posti_disponibili, elem_cons, cv_lock))
        thread.start()
        threadList.append(thread)
    
    for i in range(NUM_THREAD):
        thread = threadList[i]
        #print("Waiting")
        thread.join()

    




    


if(__name__ == '__main__'):
    main()