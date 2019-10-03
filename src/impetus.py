#!/usr/bin/env python3

import os
import json

import models.esn_classifier
import models.feature_classifier

import config

os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3' 

def banner(text):
    rows, columns = os.popen('stty size', 'r').read().split()

    print('\n' + '=' * int(columns))
    print('  ' + text)
    print('=' * int(columns))


def main():

    dataset = []

    for fn in sorted(os.listdir(config.SENSOR_FEED_DIR)):

        with open(os.path.join(config.SENSOR_FEED_DIR, fn)) as item:

            dataset.append(json.load(item))

    trainset = dataset[:600]
    testset  = dataset[600:]


    banner('Model: Categorical classification with an echo state network')
    esn = models.esn_classifier.train(trainset)
    res = models.esn_classifier.test(esn, testset)


    banner('Model: Categorical classification with a sequential neural network')
    model = models.feature_classifier.train(trainset)
    res = models.feature_classifier.test(model, testset)



if __name__ == '__main__':
    main()
