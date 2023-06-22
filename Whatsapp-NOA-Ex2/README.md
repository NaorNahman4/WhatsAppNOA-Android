# Whatsapp-NOA React App

This is a react project of 3 students from Bar-Ilan University. 
In the project we implemented WhatsappWeb-like application, only sending messages, without recieving.

## Available Scripts

In the project directory, you can run:

### `npm start`
To run the client side on localhost on port 3000


Or, you can access the server with cd ./server/ to the server directory and then:
### `npm start`
To run the server side on localhost on port 8080, also the server will connect to mongodb://localhost:27017/DB

## React uses
We implemented number of components where we render into index.html to the root component. Each component is styled
with css file named noa.css

In this project we used useRef on some HTML tags to get its info (like textboxes),
also, we used useState on the chat component, userList, some buttons and states to change state of the code.

Added some functionalities like delete a chat (that needs to be rendered), send messages (that goes into the db also)
and add chats that goes into the db.

At registery, you can register with image or without and then to get a default image.

## Express uses
We implemented a whole server by the proffesor's (Hemi) api with every needed function.
The client "communicates" with the server with a folder named ServerCalls in src where the client calls the api functions.
Also, the server's connects between active users and uses sockets.

## MongoDB uses
We uses mongodb, to precise, mongoos. we have 4 colections that implements 4 schema's, User, Chat, Message and ActiveUser.
We save in the data base every new user, chat, message and the conected users.

## Socket.io 
We used socket.io to determine the active users and to deliver and render new messages or new chats in real time.

To save images in our database, we converted the image to a string of bytes and convert it at our will, it 
saves in the db using mongoose as a 64bit HEX string.

## How to start
First, you need to install mongoDB on your computer, then it works as service.
Second, open this directory at server directory and run "npm start", then your server is up.
lasttely, open the project directory and run "npm start", then client side is available.

Now you can enjoy creating users with cool names and images, add your friends (with username) and open new chat and chating with them in real time! 
Ofcourse all of your chats and messages will be saved.

Have fun looking at our project, Whatsapp-NOA(Naor Or Adar).
