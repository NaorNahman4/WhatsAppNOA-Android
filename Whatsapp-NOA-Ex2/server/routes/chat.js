const express = require('express');
const router = express.Router();
const chatController = require('../controllers/chat.js')

//Creat new Chat
router.route('/').post(chatController.CreateChat);
//Returns all chat of a user
router.route('/').get(chatController.getAllChats);

//Returns a chat by its id
router.route('/:id').get(chatController.getChatById);
//Deletes a chat by its id
router.route('/:id').delete(chatController.deleteChat);


//send a message to the id chat
router.route('/:id/messages').post(chatController.sendMessage);
//Returns array of messages of chat
router.route('/:id/messages').get(chatController.getMessageArray);


module.exports = router;


