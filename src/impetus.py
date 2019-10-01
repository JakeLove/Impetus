#!/usr/bin/env python3

import os
import json

import preprocessor
import config


def main():

    data_set = []

    for fn in os.listdir(config.SENSOR_FEED_DIR):

        with open(os.path.join(config.SENSOR_FEED_DIR, fn)) as sensorevent:

            preprocessor.process(json.load(sensorevent))

    
if __name__ == '__main__':
    main()
