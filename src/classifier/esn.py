import numpy as np
import tensorflow as tf

from easyesn import OneHotEncoder
from easyesn import ClassificationESN

from . import preprocessor


def train(dataset):

    print("Preprocessing data for ESN Classifier")

    dataset = preprocessor.process(dataset)


    # inputs = np.array([item['processed_log'][0] for item in dataset])
    inputs = np.array([np.transpose(np.array(item['processed_log'])) for item in dataset])

    print(np.shape(inputs))

    outputs = np.array([item['entry'] for item in dataset])

    ohe = OneHotEncoder(classes=range(10))
    outputs = ohe.fit_transform(outputs)

    cutoff = 500

    inputDataTraining = inputs[:cutoff]
    outputDataTraining = outputs[:cutoff]
    inputDataValidation = inputs[cutoff:]
    outputDataValidation = outputs[cutoff:]


    print("Training ESN")

    esn = ClassificationESN(12, 700, 10, leakingRate=10.0, reservoirDensity=0.1, spectralRadius=10.0)
    esn.fit(inputDataTraining, outputDataTraining, verbose=1)


    print("Testing ESN")

    res = esn.predict(inputDataValidation, verbose=1)
    MSE = np.mean(np.abs((res - outputDataValidation)))

    print("\nAccuracy (MSE): " + str(MSE * 100) + "%")


