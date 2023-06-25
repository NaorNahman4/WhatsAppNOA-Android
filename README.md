# WhatsAppNOA-Crossed platform communication application

This is a Full-Stack project of 3 students from Bar-Ilan University. 
In this project we implemented communication application in mobile and web.

## Available Scripts

In the project directory, you can run the android client app via android studio.
In addition, you can access to Whatsapp-NOA-Ex2 directory with cd ./Whatsapp-NOA-Ex2/ and then:

### `npm start`
To run the web client on localhost on port 3000


Or, you can access the server with cd ./server/ to the server directory and then:
### `npm start`
To run the server on localhost on port 8080, also the server will connect to mongodb://localhost:27017/DB

## React uses
We implemented number of components where we render into index.html to the root component. Each component is styled
with css file named noa.css

In this project we used useRef on some HTML tags to get its info (like textboxes),
also, we used useState on the chat component, userList, some buttons and states to change state of the code.

Added some functionalities like delete a chat (that needs to be rendered), send messages (that goes into the db also)
and add chats that goes into the db.

At registery, you can register with image or without and then to get a default image.

## Mobile
We implemented the mobile client side in java. The application has various features:
- The ability to swap between themes, Light and Dark. you can access it throu the settings button at the Login
page or at the Contacts page.
- The ability to change to Server's ip whom we try to acess via the Settings page (The port stays 8080).
- You can communicate with users who use Web or Mobile application even without knowing what device they are on!
- Contact list in sorted by the date of the last message and when changed the latest one goes up of the list.
- We created new Toast message from warnings.
- You can create 1 chat with each user and can delete chats as you wish
- When registered without an image you get the same default picture like the React web application.


## Express uses
We implemented a whole server by the proffesor's (Hemi) api with every needed function.
The web client "communicates" with the server with a folder named ServerCalls in src where the client calls the api functions.
The mobile client "communicates" with the server with couple api folder Repository like.
Also, the server's connects between active users (whether in web or mobile) via sockets and firebase api.

## MongoDB uses
We uses mongodb, to precise, mongoos. we have 4 colections that implements 4 schema's, User, Chat, Message and ActiveUser.
We save in the data base every new user, chat, message and the conected users.

## Socket.io and Firebase
We used those api's to determine the active users and to deliver and render new messages or new chats in real time.
We created 2 Databases "connectedphoneusers" and "connectedusers" which save the online users and then when someone sends 
a message to them, it will render in real time.
So 1 user cannot login in from 2 devices because he is in the online users Databases.

To save images in our database, we converted the image to a string of bytes and convert it at our will, it 
saves in the db using mongoose as a 64bit HEX string.

## Special instalations
First, to run the server or web client you need to have Node.js on your computers.
Second, to run the database you need to have mongoDB on your computer.
In addition, to start the android client app you need android studio on your computer.
And lasttely, you need to install some external modules to your node_modules.
So at the root directory type: `npm i express body-parser cors custom-env fs jsonwebtoken mongoose socket.io socket.io-client firebase-admin`.


## How to start
First, you need to install mongoDB on your computer, then it works as service.
Second, open this directory, enter to Whatsapp-NOA-Ex2 directory and there at server directory  run "npm start", then your server is up.
lasttely, open Whatsapp-NOA-Ex2 directory and run "npm start", then Web client application is up.
Also, you can open the main directory at Android Studio and start the Mobile client application.

Now you can enjoy creating users with cool names and images, add your friends (with username) and open new chat and chating with them in real time! 
Ofcourse all of your chats and messages will be saved.

Have fun looking at our project, Whatsapp-NOA(Naor Or Adar).
