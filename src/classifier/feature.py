from . import preprocessor

import numpy as np
import tensorflow as tf


def train(dataset):

    dataset = preprocessor.process(dataset)

    inputs = []
    labels = []

    for item in dataset:
        features = [item for sublist in [extractfeatures(s) for s in item['processed_log']] for item in sublist]

        inputs.append(features)
        labels.append(int(item['entry']))

    n = len(inputs[0])

    model = tf.keras.Sequential([
        tf.keras.layers.Dense(n, activation='linear'),
        tf.keras.layers.Dense(10 + int(n * 2 / 3), activation='relu'),
        tf.keras.layers.Dense(10, activation='softmax')
    ])

    model.compile(optimizer='adam',
                  loss='sparse_categorical_crossentropy',
                  metrics=['accuracy'])

    model.fit(inputs[:500], labels[:500], epochs=10)


    test_loss, test_acc = model.evaluate(inputs[500:],  labels[500:], verbose=2)
    print('\nTest accuracy:', test_acc)


def extractfeatures(s):
    return [
        s[0], 
        s[-1],
        np.mean(s),
        np.std(s),
        np.amin(s),
        np.amax(s)
    ]

