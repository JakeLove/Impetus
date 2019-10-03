# Impetus

Android and IOS applications are run in [sanboxed environments](https://en.wikipedia.org/wiki/Sandbox_(computer_security)) that tightly controls application resources. By default, apps can't interact with each other and have limited access to the OS. A positive consequence of this is that malicious applications cannot see what you are doing in you benign ones. In an ideal world, if you were to enter secure PIN into your mobile banking app, a malicious application could not retrieve this PIN unless they could escape their sandbox. In order to do this ([according to Android](https://source.android.com/security/app-sandbox)) one must first compromise the Linux kernel). Impetus is a proof of concept exploit that shows the feasibility of using a side channel attack to access user input data from outside of the sandbox.

Modern smartphones contain multiple sensors that output a large variety of data. The [Android Sensor API](https://developer.android.com/guide/topics/sensors/index.html) exposes over 13 different sensors. None of them require any explicit permissions from the user and can log data in background services whilst the app is closed. 

# Results

Initial Classification:

==========================================================================================================
  Model: Categorical classification with an echo state network
==========================================================================================================

Preprocessing data for ESN classifier training:
100% (600 of 600) |################################################| Elapsed Time: 0:00:00 Time:  0:00:00
Training ESN:
100% (600 of 600) |################################################| Elapsed Time: 0:00:18 Time:  0:00:18

Preprocessing data for ESN testing:
100% (89 of 89) |##################################################| Elapsed Time: 0:00:00 Time:  0:00:00
Testing ESN:
100% (89 of 89) |##################################################| Elapsed Time: 0:00:02 Time:  0:00:02

Model accuracy: 80.90%

==========================================================================================================
  Model: Categorical classification with a sequential neural network
==========================================================================================================

Preprocessing data for sequential NN training:
100% (600 of 600) |################################################| Elapsed Time: 0:00:00 Time:  0:00:00
Train on 600 samples
Epoch 1/5
600/600 [==============================] - 0s 663us/sample - loss: 2.1704 - accuracy: 0.3583
Epoch 2/5
600/600 [==============================] - 0s 55us/sample - loss: 1.1874 - accuracy: 0.6650
Epoch 3/5
600/600 [==============================] - 0s 51us/sample - loss: 0.8240 - accuracy: 0.7500
Epoch 4/5
600/600 [==============================] - 0s 51us/sample - loss: 0.6421 - accuracy: 0.7967
Epoch 5/5
600/600 [==============================] - 0s 52us/sample - loss: 0.4136 - accuracy: 0.8533

Preprocessing data for sequential NN testing:
100% (89 of 89) |##################################################| Elapsed Time: 0:00:00 Time:  0:00:00
89/1 - 0s - loss: 0.5986 - accuracy: 0.7640

Model accuracy: 76.40%
Model loss: 0.58180064445501


