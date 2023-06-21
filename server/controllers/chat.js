const chatService = require('../servies/chat.js');
const userService = require('../servies/user.js')
const jwt = require('jsonwebtoken');
const connectPhoneUsers = require('../models/connectPhoneUsers.js');
const { connect } = require('../routes/user.js');
const connectedUsers = require('../models/connectedUsers.js');
const usingSocket = require('../app.js');
const admin = require('firebase-admin');
// Initialize Firebase Admin SDK




const CreateChat = async (req, res) => {
    try {
        const me = decode(req.headers.authorization);
        const username = req.body.username;
        const firstUser = await userService.findUserByUsername(me);
        const secondUser = await userService.findUserByUsername(username);
        if (secondUser === null) {
            //there isnt a user
            res.status(404).json(null);
        }
        //Checking I try to add myself
        else if (firstUser.username === secondUser.username) {
            //Invalid access to server
            res.status(501).json({});
        }
        //Checking if a chat between those 2 already exist
        else if (await checkIfThereIsChat(firstUser.username, secondUser.username)) {
            //Invalid access to server
            res.status(500).json({});
        }
        else {
            res.json(await chatService.CreateChat(firstUser, secondUser));
        }
    }
    catch (error) {
        console.error(error);
        //invalid request from server
        res.status(500);
    }
};

const getAllChats = async (req, res) => {
    try {
        const username = decode(req.headers.authorization);
        res.status(200).json(await chatService.getAllChats(username));
    } catch (error) {
        console.error(error);
        //Not found any chat list
        res.status(404);
    }
}

const getChatById = async (req, res) => {

    try {
        const username = decode(req.headers.authorization);
        const id = req.params.id;
        const i = await chatService.getChatById(username, id);
        res.status(200).json(i);
    } catch (error) {
        console.error(error);
        res.status(404);
    }
}

async function getChatByIdWithoutAutho(username, id) {
    try {
        return await chatService.getChatById(username, id);
    } catch (error) {
        console.error(error);
        return null;
    }
}

const deleteChat = async (req, res) => {
    try {
        const username = decode(req.headers.authorization);
        const id = req.params.id;
        //Check if chat exist and a chat of user
        var tmpChat = await getChatByIdWithoutAutho(username, id);
        if (tmpChat) {
            await chatService.deleteChat(username, id)

            res.status(200).send();
            return;
        } else {
            //404 tells that not found this chat.
            res.status(404);
        }
    } catch (error) {
        console.error(error);
        res.status(400);
    }
}

const sendMessage = async (req, res) => {
    try {
        const username = decode(req.headers.authorization);
        const id = req.params.id;
        const msg = req.body.msg;
        const activeChat = await chatService.getChatById(username, id);
        const receiverUsername = activeChat.users[0].username === username ? activeChat.users[1].username : activeChat.users[0].username;
        const senderUser = await connectPhoneUsers.findOne({ username: username }); 
        const receiverUserAndroid = await connectPhoneUsers.findOne({ username: receiverUsername });
        const receiverUserWeb = await connectedUsers.findOne({ username: receiverUsername });

        // first check if the sender is android, else web,the message will handle in app.js listen to the event "newMessage"
        if(senderUser){
            console.log("send is android");
            // if the sender is android, check if the receiver is connect android if yes use firebase
            if(receiverUserAndroid){
                console.log("send is android, receiver is android");
            const otherUserTokenFB = receiverUserAndroid.token;
                if(otherUserTokenFB){
                // send the message to the other user with firebse base on the tokenFB
                const message = {
                    data: {
                      content: msg,
                      senderUsername: username
                    },
                    token: otherUserTokenFB
                  };
                const response = await admin.messaging().send(message);
                }
        }
        else if(receiverUserWeb){
            console.log("send is android, receiver is web");
            // sender is android, receiver is web so we use socket.io
            const otherUserSocketId = receiverUserWeb.socketId;
            if(otherUserSocketId){
                // send the message to the other user with socket.io
                usingSocket.io.to(otherUserSocketId).emit('render');
            }
        }

    }
        //Check if chat exist and a chat of user
        if (chatService.getChatById(username, id)) {
            res.status(200).json(await chatService.sendMessage(username, id, msg));
        } else {
            //404 tells that not found this chat.
            res.status(404);
        }
    }
    catch (error) {
        console.error(error);
        res.status(400);
    }
}
const getMessageArray = async (req, res) => {
    try {
        const username = decode(req.headers.authorization);
        const id = req.params;
        const i = await chatService.getChatById(username, id);
        return res.status(200).json(i.messages);
    }
    catch (error) {
        console.error(error);
        res.status(404);
    }
}

module.exports = {
    CreateChat, getAllChats, getChatById, deleteChat, sendMessage, getMessageArray
};

function decode(token) {
    return jwt.verify(token, "key").username;
}

async function checkIfThereIsChat(firstUsername, secondUsername) {
    const chatList = await chatService.getAllChats(firstUsername);

    let hasChat = false;
    chatList.some(item => {
        if (item.users[0].username === secondUsername || item.users[1].username === secondUsername) {
            hasChat = true;
            return true; // exit the loop early
        }
    });

    return hasChat;
}