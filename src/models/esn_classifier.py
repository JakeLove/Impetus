import numpy as np
import tensorflow as tf

from easyesn import OneHotEncoder
from easyesn import ClassificationESN

from . import preprocessor


'''
    Train a dataset against a classification ESN with 1000 node and default hyperparameters
'''
def train(dataset):

    print("\nPreprocessing data for ESN classifier training:")

    inputs, outputs = getio(dataset)

    print("Training ESN:")

    esn = ClassificationESN(12, 1000, 10)
    esn.fit(inputs, outputs, verbose=1)

    return esn


'''
    Test a model against a dataset and output / return the accuracy
'''
def test(esn, dataset):

    print("\nPreprocessing data for ESN testing:")

    inputs, outputs = getio(dataset)

    print("Testing ESN:")

    res = esn.predict(inputs, verbose=1)

    correct = 0

    for i, r in enumerate(res):

        if np.array_equal(outputs[i], res[i]):
            correct += 1


    accuracy = 100 * correct / len(outputs)
    print("\nModel accuracy: %.2f" % accuracy + "%")

    return accuracy


'''
    Process the dataset with the preprocessor then extract the input and ouput vectors into 
    seperate arrays.
'''
def getio(dataset):

    dataset = preprocessor.process(dataset)

    inputs = np.array([np.transpose(np.array(item['processed_log'])) for item in dataset])

    outputs = np.array([item['entry'] for item in dataset])

    ohe = OneHotEncoder(classes=range(10))
    outputs = ohe.fit_transform(outputs)

    return inputs, outputs