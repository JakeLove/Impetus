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

		# resample data to a linear domain so it regression statistics are more accurate
		# remaspling to a constant length is also necessary for the ESN
		domain_old = timeseries['domain']
		domain_new = np.linspace(domain_old[0], domain_old[-1], 100)

		for s in transposed:

			f = interpolate.interp1d(domain_old, s, kind='linear')
			s_ = f(domain_new)
			
			# Center the data onto mean.
			# The data is NOT fully standardised here, if we want to scale by the std I think we
			# should scale by the std of all values for this kind of sensor so as not to loose information
			processed.append(np.subtract(s_, np.mean(s_)))

	return processed 
