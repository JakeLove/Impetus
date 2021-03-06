#!/usr/bin/env python3

import os
import time
import json

from flask import Flask
from flask import request

import config

app = Flask(__name__)


@app.route('/')
def index():

    return 'Hello, World!'


@app.route('/sensorfeed', methods=['POST'])
def sensorfeed():

    fp = config.SENSOR_FEED_DIR + str(time.time()) + ".json"

    with open(fp, "w") as file:
        file.write(request.data.decode("utf-8"))
        print("saved to: ", fp)

    return 'Hello, World!'


def main():

    if not os.path.exists(config.SENSOR_FEED_DIR):
        os.makedirs(config.SENSOR_FEED_DIR)

    app.run(host='0.0.0.0', port=1066)


if __name__ == '__main__':
    main()
