const chatService = require('../servies/chat.js');
const userService = require('../servies/user.js')
const jwt = require('jsonwebtoken');

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
        console.log(msg);
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