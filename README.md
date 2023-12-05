# ACME-RUN
You are working for ACME (A Company that Makes Everything), as part of their IT department. You have been contacted by a client named HammerCorp Inc. to design and develop a new game they want to launch in Fall 2024 on the North American market. 

## Description
Game Center Service
When the user sends a request and receives the request, he will first ask for the user's username. After it knows your username, it will try to send a request to the center. This request means that it can start in the workflow. It can be understood that our thing is the upper panel of the gym treadmill. It first asks the user to log in to the account. After logging in, you press start and your trial starts. It will first create a workout, which represents the training today, and then will next request a track. 

Heartrate Monitor Service
After starting training, the heartrate monitor will continuously record the user's real-time heart rate. It does not store data locally, it is continuously sent to game center. The message sent includes user name, workout ID, and heart rate.

Trail Provider Service
It will assign a track to the user and send the message to the user. For example, if there is a track in Hamilton, it will automatically calculate where the nearest track you should be assigned to based on the longitude and latitude, and then these would be assigned.

Point Service
As the user is working out, the heart rate and some other information of workout will be sent to point service via asynchronous messenger such as RabbiMQ. There is a listener to receive this message and then update the point in the point database accordingly conforming to a given point management rule.

Attack Generator Service
As the user is working out, the attack service will generate some attacks to users and provide several options for users to respond to the attack. Messages of the attack will be sent with RabbitMQ to heart rate monitor.

## Deployment


