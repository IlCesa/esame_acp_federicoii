import pandas as pd
from scipy import stats
import numpy as np


if(__name__ == "__main__"):
    dict = {"Giova" : [7,7,7], "Ile": [1,3,5]}
    print(dict)
    df = pd.DataFrame(dict)
    print(df)
    print(df.describe())
    df = pd.read_csv('dataset.csv', skiprows=4)
    linregress = stats.linregress(df)
    prediction = linregress.slope * 2000 + linregress.intercept
    print(prediction)