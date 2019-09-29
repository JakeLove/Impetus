import os

from flask import Flask
from flask import request

DATA_DIR = 'sensor_data'

app = Flask(__name__)


@app.route('/')
def index():
    return 'Hello, World!'


@app.route('/sensorfeed', methods=['POST'])
def sensorfeed():
    print(request.json)
    return 'Hello, World!'


def main():

    if not os.path.exists(DATA_DIR):
        os.makedirs(DATA_DIR)

    app.run(host='0.0.0.0', port=1066)


if __name__ == '__main__':
    main()
