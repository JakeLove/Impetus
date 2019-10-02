import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

from scipy import interpolate
from tsfresh import extract_features

def processfeatures(dataset):

	labels = [item['entry'] for item in dataset]

	sensordata = extractsensordata(dataset)

	df = makedataframe(sensordata)

	print(df.head())

def makedataframe(sensordata):

	head = sensordata[0]

	columncount  = len(head)
	columnlength = len(head[0])
	itemcount    = len(sensordata)

	columnheaders = ['sensor' + str(n) for n in range(columncount)]

	datamatrix = np.vstack([np.transpose([np.repeat(i, len(sensor[0]))] + sensor) for i, sensor in enumerate(sensordata)])

	return pd.DataFrame(datamatrix)


def extractsensordata(dataset):
	return standardise(list(map(resample, dataset)))


def standardise(dataset):

	head = dataset[0]

	sumstds = np.zeros(len(head))

	for sensorlog in dataset:
		sumstds += np.array([np.std(s) for s in sensorlog])

	meanstds = np.divide(sumstds, len(dataset))

	return [[center(np.divide(s, meanstds[i])) for i, s in enumerate(sensorlog)] for sensorlog in dataset]


def resample(item):

	resampledlogs = []
	log = item['sensor_log']

	for sensorname, timeseries in log.items():

		transposed = [list(s) for s in zip(*timeseries['values'])]

		domain_old = timeseries['domain']
		domain_new = np.linspace(domain_old[0], domain_old[-1], 50)

		for s in transposed:

			f = interpolate.interp1d(domain_old, s, kind='cubic')
			resampledlogs.append(f(domain_new))

	return resampledlogs


def center(X):

    return np.subtract(X, np.mean(X))


