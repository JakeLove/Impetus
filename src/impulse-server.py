import os
import time
import json

from flask import Flask
from flask import request

DATA_DIR = os.path.join(os.path.dirname(__file__), '../../data/sensor-feed/')

app = Flask(__name__)


@app.route('/')
def index():

    return 'Hello, World!'


@app.route('/sensorfeed', methods=['POST'])
def sensorfeed():

    print(DATA_DIR + str(time.time()) + ".txt")

    with open(DATA_DIR + str(time.time()) + ".txt", "w") as file:
        file.write(request.data.decode("utf-8"))

    return 'Hello, World!'


def main():

    if not os.path.exists(DATA_DIR):
        os.makedirs(DATA_DIR)

    app.run(host='0.0.0.0', port=1066)


if __name__ == '__main__':
    main()
