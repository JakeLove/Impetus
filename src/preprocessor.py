import os
import json

import numpy as np


def process(folder):

	files = os.listdir(folder)
	data_set = []

	for file in files:

		data_set.append(parse(file))


def parse(sensor_data_file):

	with open(sensor_data_file) as json_file:

	    data = json.load(json_file)

	    print(data)


def center(X):
	return np.subtract(X, np.mean(X))


def mean_std(P):
	return np.mean(list(map(np.std, P)))
