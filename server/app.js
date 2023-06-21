const express = require('express');
const app = express();
const http = require('http');
const server = http.createServer(app);
const socketIO  = require("socket.io");
const io = socketIO(server); 
const path = require('path');
const connectedUsers =require('./models/connectedUsers.js');



// when starting the serer,delete all the connected users
const deleteAllConnectedUsers = async () => {
  //delete all the connected users
await connectedUsers.deleteMany({}).exec();
}
deleteAllConnectedUsers();



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
  socket.on('newMessage', async( senderUsername, receiverUsername) => {
   const ifSenderConnected = await connectedUsers.findOne({username: senderUsername});
   const ifReceiverConnected = await connectedUsers.findOne({username: receiverUsername}); 
   if(ifSenderConnected && ifReceiverConnected){
    // find receiver socket id
    const socketId = await connectedUsers.findOne({username: receiverUsername});
    // send message to receiver
    io.to(socketId.socketId).emit('render');
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
