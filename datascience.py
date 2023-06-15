import statistics
import pandas as pd
import numpy as np
numbers = np.array([2, 3, 5, 7, 11])
print(type(numbers))
print(numbers)
integers = np.array([[1, 2, 3], [4, 5, 6]]) 
print(integers[1][2])
#attributi array
print(integers.shape)
print(np.zeros((2,5)))
print(np.ones((2,5)))
print(np.full((2,5),13))
print(np.arange(10,0,-2))
for i in integers.flat:
    print(str(i) + " ")

int2 = integers.copy() #deep copy
int3 = integers.view() #shallow copy
print("Adesso vediamo panda!")
grades = pd.Series([87, 100, 94])
print(grades.describe())
