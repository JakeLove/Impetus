#!/usr/bin/env python3

import os
import json

import classifier.feature
import config


def main():

    dataset = []

    for fn in os.listdir(config.SENSOR_FEED_DIR):

        with open(os.path.join(config.SENSOR_FEED_DIR, fn)) as sensorevent:

            dataset.append(json.load(sensorevent))

    classifier.feature.train(dataset)


if __name__ == '__main__':
    main()
