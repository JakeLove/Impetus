import warnings

import numpy as np
import tensorflow as tf

import scipy
from scipy import stats

from . import preprocessor


tf.keras.backend.set_floatx('float64')

def train(dataset):

    print("\nPreprocessing data for sequential NN training:")
    inputs, outputs = getio(dataset)

    n = len(inputs[0])

    # Randomly picked model for now, will look at hyper-parameter/architecture optimisation later
    model = tf.keras.Sequential([
        tf.keras.layers.Dense(n, activation='linear'),
        tf.keras.layers.Dense(50, activation='relu'),
        tf.keras.layers.Dense(50, activation='relu'),
        tf.keras.layers.Dense(50, activation='relu'),
        tf.keras.layers.Dense(10, activation='softmax')
    ])

    model.compile(optimizer='adam',
                  loss='sparse_categorical_crossentropy',
                  metrics=['accuracy'])

    model.fit(inputs, outputs, epochs=5)

    return model


'''
    Test a model against a dataset and output / return the loss and accuracy
'''
def test(model, dataset):

    print("\nPreprocessing data for sequential NN testing:")
    inputs, outputs = getio(dataset)

    test_loss, test_acc = model.evaluate(inputs,  outputs, verbose=2)
    print('\nModel accuracy: %.2f' % (test_acc * 100) + '%')
    print('Model loss:', test_loss)

    return test_loss, test_acc


'''
    Process the dataset with the preprocessor then extract the input and ouput vectors into 
    seperate arrays.
'''
def getio(dataset):

    dataset = preprocessor.process(dataset)

    inputs = []
    outputs = []

    for item in dataset:
        features = [item for sublist in [extractfeatures(s) for s in item['processed_log']] for item in sublist]

        inputs.append(features)
        outputs.append(int(item['entry']))

    return inputs, outputs


'''
    Take a numpy array and extract features from it. Current feautures extracted are:
        - value of first element
        - value of final element
        - standard deviation
        - smallest element
        - largest element
        - linear regression slope
        - linear regression intercept
        - linear regression r

    There is a lot of oppurunity for picking more features as many should exist. In future I will look at which
    these features are relevant to classification and which are not.
'''
def extractfeatures(s):

    '''
        Sometimes the linregress fucntion numerically errors (when s = [0, 0, 0 ... 0]).
        If this happends default these features to 0
    '''
    slope, intercept, r_value, p_value, std_err = 0, 0, 0, 0, 0

    with warnings.catch_warnings():

        warnings.filterwarnings('error')

        try:
            slope, intercept, r_value, p_value, std_err = stats.linregress(s, np.linspace(0, 1, len(s)))

        except Warning: 
            pass

    return [

        s[0], 
        s[-1],
        np.std(s),
        np.amin(s),
        np.amax(s),
        np.nan_to_num(slope),
        np.nan_to_num(intercept),
        np.nan_to_num(r_value)

    ]

