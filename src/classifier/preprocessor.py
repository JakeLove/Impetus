import operator

import progressbar

import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

from scipy import interpolate



def process(dataset):

	# filter out empty sensor logs
	dataset = [s for s in dataset if len(s['sensor_log']['android.sensor.orientation']['values']) > 0]

	bar = progressbar.ProgressBar(max_value=len(dataset), redirect_stdout=True, poll_interval=0.0001)
	bar.update(0)

	for n, item in enumerate(dataset):

		item['processed_log'] = processlog(item['sensor_log'])
		bar.update(n)

	bar.finish()

	return dataset


def processlog(sensorlog):

	processed = []

	for sensorname, timeseries in sorted(sensorlog.items(), key=operator.itemgetter(0)):

		transposed = [list(s) for s in zip(*timeseries['values'])]

		domain_old = timeseries['domain']
		domain_new = np.linspace(domain_old[0], domain_old[-1], 100)

		for s in transposed:

			f = interpolate.interp1d(domain_old, s, kind='linear')
			s_ = f(domain_new)
			
			processed.append(np.subtract(s_, np.mean(s_)))

	return processed 
