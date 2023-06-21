const express = require('express');
const app = express();
const http = require('http');
const server = http.createServer(app);
const socketIO  = require("socket.io");
const io = socketIO(server); 
const path = require('path');
const connectedUsers =require('./models/connectedUsers.js');
const connectedPhoneUsers = require('./models/connectPhoneUsers.js');
const admin = require('firebase-admin');
// Initialize Firebase Admin SDK


var serviceAccount = require("./config/firebase-admin.json");
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)

});


// when starting the serer,delete all the connected users
const deleteAllConnectedUsers = async () => {
  //delete all the connected users
await connectedUsers.deleteMany({}).exec();
}
deleteAllConnectedUsers();



// when starting the serer,delete all the connected users phones
const deleteAllConnectedUsersPhone = async () => {
  //delete all the connected users
await connectedPhoneUsers.deleteMany({}).exec();
}
deleteAllConnectedUsersPhone();


// Import the 'cors' package
const cors = require('cors');

// Enable CORS for all routes
app.use(cors());

app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, '../public/index.html'));

  });

io.on('connection', async (socket) => {
  // add user to connected users
  socket.on('userConnected', async(username) => {
    const temp = new connectedUsers({ username: username, socketId: socket.id });
      await temp.save();
  });
  socket.on('newMessage', async( senderUsername, receiverUsername,msg) => {
   // check if receiver is android or web
   var ifReceiverConnected = await connectedUsers.findOne({username: receiverUsername}); 
   // receiver is web and connected
   if(ifReceiverConnected){
    const socketId = await connectedUsers.findOne({username: receiverUsername});
    // send message to receiver
    io.to(socketId.socketId).emit('render');
   }
  else {
      ifReceiverConnected = await connectedPhoneUsers.findOne({username: receiverUsername});
      // receiver is android and connected
      if(ifReceiverConnected){
        const otherUserTokenFB = ifReceiverConnected.token;
        if(otherUserTokenFB){
            // send the message to the other user with firebse base on the tokenFB
            const message = {
                data: {
                  content: msg,
                  senderUsername: senderUsername
                },
                token: otherUserTokenFB
              };
            const response = await admin.messaging().send(message);
        }
      }
      else{
        // receiver is not connected
      }
   }
  });

  
  socket.on('renderAddChat', async(username) => {
    const ifReceiverConnected = await connectedUsers.findOne({username: username}); 
    if(ifReceiverConnected){
     // find receiver socket id
     // send message to receiver
     io.to(ifReceiverConnected.socketId).emit('renderAddChat');
    }
  
  });

    socket.on('renderDeleteChat', async(username) => {
    const ifReceiverConnected = await connectedUsers.findOne({username: username}); 
    if(ifReceiverConnected){
     // send message to receiver
     io.to(ifReceiverConnected.socketId).emit('renderDeleteChat');
    }
  });


  socket.on('logout', async() => {
    // remove user from connected users
    await connectedUsers.deleteOne({ socketId: socket.id });
  });

  socket.on('disconnect',async () => {
    // remove user from connected users
    await connectedUsers.deleteOne({ socketId: socket.id });
  });

  socket.on('close', async () => {
    // Disconnect users and clean up resources here


});
});

const userRoutes = require('./routes/user.js');
const chatRoutes = require('./routes/chat.js');
const tokenRoutes = require('./routes/token.js');
const tokenFBRoutes = require('./routes/tokenFB.js');


// Middleware for parsing JSON bodies
app.use(express.json({ limit: '1mb' }));
app.use(express.urlencoded({ limit: '1mb', extended: true }));

// Using cors middleware to enable cross-origin requests
app.use(cors());

// Connecting to MongoDB
const mongoose = require('mongoose');
const { copyFileSync } = require('fs');
mongoose.connect("mongodb://127.0.0.1:27017/DB" , {
    useNewUrlParser: true,
    useUnifiedTopology: true,
  })
  .then(() => console.log('Connected to MongoDB'))
  .catch((e) => console.log(e));

app.use(express.static('../public'));


app.use('/api/Users', userRoutes);
app.use('/api/Tokens', tokenRoutes);
app.use('/api/Chats', chatRoutes);
app.use('/api/TokensFB', tokenFBRoutes);

app.listen(process.env.PORT);

server.listen(8080);


module.exports = {
  io
};
