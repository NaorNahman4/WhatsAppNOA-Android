//Making a server
const express = require('express');
var app = express();

const userRouts = require('./routes/login');



// Middleware for parsing JSON bodies
app.use(express.json());

//Using bodyParser midware to use "body" in POST calls
const bodyParser = require('body-parser');
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));



// app.use('/', homepageFunc);
//Using cors midware, I dont know why
const cors = require('cors');
app.use(cors());



const mongoose = require('mongoose');
app.use(express.static('../public'));
app.use('/', userRouts);







// naor changes !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

//this is the key for all the tokens right now -should be changed to a more secure key!!
const key = "Some super secret key shhhhhhhhhhhhhhhhh!!!!!"


// the userDB is the users array in the models/user.js file ** TEMPORARY **
const { users } = require('./models/user.js');
// For tokens
// const crypto = require('crypto');
const jwt = require('jsonwebtoken');

// Function to generate a secure secret key - to be used with JWT - return a random string of 64 characters = token
const generateSecretKey = () => {
    return crypto.randomBytes(64).toString('hex');
};

// Chats  routes

// POST route for /api/Chats
// POST route for /api/Chats
app.post('/api/Chats', function (req, res) {
    const { token, username } = req.body; // Retrieve the token and username from the request body
  
    // Find the user in the users array based on the token


    const user = users.find(u => u.token === token);

  
    if (user) {
      // Check if the username exists
      const chatUser = users.find(u => u.username === username);
  
      if (chatUser) {
        // temp as 2, should be changed to chat arr.length + 1 ( or something like that)
        const chatId =2;
  
        // Create a new chat object with the necessary details
        const newChat = {
          id: chatId,
          user: {
            username: chatUser.username,
            displayName: chatUser.displayName,
            profilePic: chatUser.profilePic
          },
          lastMessage: null // You can set this to the last message if available
        };
  
        // Add the chat to the user's chatList
        user.ChatList.push(chatId);
  
        // Return the chat details as the response
        res.json(newChat);
      } else {
        // User not found
        res.status(404).json({ error: 'User not found' });
      }
    } else {
      // Invalid token
      res.status(401).json({ error: 'Invalid token' });
    }
  });
  
// GET route for /api/Chats
// use the token that the user send and return the chats array
app.get('/api/Chats', function (req, res) {
    // If the request has an authorization header
    if (req.headers.authorization) {
        // Extract the token from that header
        const token = req.headers.authorization.split(" ")[1];
        try {
            // IDK HOW TO DO IT - its on the targilon7 file
            // Verify the token is valid
            // print the token and the key  
           // const data = jwt.verify(token, key);
            // Token validation was successful, return the chats array
            //res.send(data.ChatList);
            // temp....................
            res.send(users[0].ChatList);
        } catch (err) {
            return res.status(401).send("Invalid Token");
        }
    }
    else
        return res.status(403).send('Token required');
});

// GET route for /api/Chats/:id
app.get('/api/Chats/:id', function (req, res) {
    const { token } = req.headers; // Retrieve the token from the request headers
    const { id } = req.params; // Retrieve the chat ID from the request parameters
    // Find the user in the users array based on the token
    const user = users.find(u => u.token === token);
  
    if (user) {
      // Find the chat in the user's chatList based on the ID
      const chatId = user.ChatList.find(cid => cid === id);
  
      if (chatId) {
        // Find the other user in the chat
        const otherUser = users.find(u => u.ChatList.includes(chatId) && u.token !== token);
        if (otherUser) {
          // Construct the chat object with the desired structure
          const chat = {
            id: chatId,
            users: [
              {
                username: user.username,
                displayName: user.displayName,
                profilePic: user.profilePic
              },
              {
                username: otherUser.username,
                displayName: otherUser.displayName,
                profilePic: otherUser.profilePic
              }
            ],
            messages: [] // 
          };
  
          // Return the chat details as the response
          res.json(chat);
        } else {
          // Other user not found in the chat
          res.status(404).json({ error: 'Other user not found in the chat' });
        }
      } else {
        // Chat ID not found in user's chatList
        res.status(404).json({ error: 'Chat ID not found' });
      }
    } else {
      // Invalid token
      res.status(401).json({ error: 'Invalid token' });
    }
  });
  
  

// DELETE route for /api/Chats/:id
app.delete('/api/Chats/:id', function (req, res) {
 // need to adjust && didnt see it client side :O


    res.end();
});
//  POST route for /api/Chats/:id/Messages
app.post('/api/Chats/:id/Messages', function (req, res) {
 // need to adjust 

    res.end();
});
//  GET route for /api/Chats/:id/Messages
app.get('/api/Chats/:id/Messages', function (req, res) {
     // need to adjust 

 
    res.end();
});

// Tokens routes

// POST route for /api/Tokens
// ** If the user press login button check if the username and password are correct base on the usersDB and return the token
app.post('/api/Tokens', function (req, res) {
    const { username, password } = req.body; 

    // Find the user in the usersDB array, if didnt find -> user=null
    const user = users.find(u => u.username === username && u.password === password);
    if (user) {
        // User credentials are correct, generate and return a token
        //const secretKey = generateSecretKey();
        const token = jwt.sign({ username: user.username }, key); 
        res.json({ token: token });
    } else {
        // User credentials are incorrect
        res.status(401).json({ error: 'Invalid username or password' });
    }
});

// Users routes

// POST route for /api/Users
app.post('/api/Users', function (req, res) {
 // need to adjust 

    res.end();
});

// GET route for /api/Users/:username
app.get('/api/Users/:username', function (req, res) {
    // get the username from the request URL and  Returning the user: username, displayname and img
    const username = req.params.username;
    const user = users.find(u => u.username === username);
    if (user) {
        res.json({ username: user.username, displayName: user.displayName, img: user.img });
    }
    else {
        res.status(404).json({ error: 'User not found' });
    }
});
app.listen(8080);




