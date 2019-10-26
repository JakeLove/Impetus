# Impetus

Android and IOS applications are run in [sanboxed environments](https://en.wikipedia.org/wiki/Sandbox_(computer_security)) that tightly control application resources. By default, apps can't interact with each other and have limited access to the OS. A positive consequence of this is that malicious applications cannot see what you are doing in you benign ones. In an ideal world, if you were to enter a secure PIN into your mobile banking app, a malicious application could not retrieve this PIN unless they could escape their sandbox. To do this ([according to Android](https://source.android.com/security/app-sandbox)) one must first compromise the Linux kernel. Impetus is a proof of concept exploit that shows the feasibility of using a side-channel attack to access user input data from outside of the sandbox.

Modern smartphones contain multiple sensors that output a large variety of data. The [Android Sensor API](https://developer.android.com/guide/topics/sensors/index.html) exposes over 13 different sensors. None of them requires any explicit permissions from the user and can log data in background services whilst the app is closed. 

Imagine you are logging into your mobile banking app with a PIN code. As you enter the digits through the touch screen your phone will move. This is due to either the force of your finger touching the screen, or you moving the screen closer to your finger. This motion data can be logged from outside of the bank apps sandbox and then used to figure out which digit you entered without having any access to bank apps memory or processes.

Impetus aims to demonstrate this by capturing temporal data from the orientation sensor, gyroscope, accelerometer and linear accelerometer. It then uses a variety of methods to work out what digits have been entered.

# Limitations

- The dataset I am using was trained and tested on people who hold and enter their PIN with a single hand. 
- The model requires to know when the device was touched (data from the sensors begins being collected on when the screen is touched). However, I believe that given a continuous signal of motion sensor data touch events could be recognised using traditional signal processing methods.
- The dataset was trained from a single mobile phone, different sizes and weights of phones almost certainly effect how a user holds one.

# Classification Approach

Sensor data was collected from 4 people and shuffled into the training and test sets.

My initial approach was to look at the problem as a classification problem. Each PIN entry was a discrete value out of 10 possible categories. For each PIN digit entry, I had a set of temporal data, 12 time-series (4 sensors with 3 dimensions each. To classify this kind of data I looked into two options. Echo state networks for signal classification, then sequential neural networks using various signal features and statistics for the input layer.

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

No hyperparameters were tuned at this stage, the model parameters have been picked pretty much at random. If the dataset was larger I would claim that the ESN performs a little better than the NN, but it isn't. The accuracy displayed is the accuracy for a single digit of the PIN, so an 80% accuracy to classify a single-digit results in approx a 40% accuracy to classify the entire PIN. Not mind-blowing, but significantly better than 0.01% by random guess.

# Improvements

- Train and test on a much larger range of people
- Look at treating the problem as a regression problem (how far left/right was the press, how far up/down) then decode these values to PIN locations.
- Look at classifying the relationship between sequential inputs. Is it easier to guess a 4 sequential inputs than 4 randomly selected ones?
