

const Chat = require('../models/chat.js');
const Counter = require('../models/Counter.js');
const Message = require('../models/message.js');

const CreateChat = async (me, username) => {
    const firstUser = { username: me.username, displayName: me.displayName, profilePic: me.profilePic };
    const secondUser = { username: username.username, displayName: username.displayName, profilePic: username.profilePic };

    const newID = await getChatID();
    const chat = new Chat({ id: newID, users: [firstUser, secondUser], messages: [] });
    return await chat.save();
};

const getAllChats = async (username) => {
    return await Chat.find({
        users: {
            $elemMatch: {
                username: username
            }
        }
    });
}

const getChatById = async (username, id) => {
    const chatList = await getAllChats(username);
    var chat = null
    chatList.forEach(item => {
        if (item.id == id) {
            chat = item;
        }
    });
    return chat;
}

const deleteChat = async (username, id) => {
    try {
        await Chat.deleteOne({
            id: id
        })
    }
    catch (error) {

    }
}
const sendMessage = async (username, id, msg) => {
    //Creating a new message into Message DB
    const chat = await getChatById(username, id);
    const sender = getUserInfo(chat, username);
    let generateID = await getMessageID();
    const message = new Message({ id: generateID, created: new Date().toISOString(), sender: sender, content: msg });
    const tmp = { id: generateID, created: new Date().toISOString(), sender: sender, content: msg };
    await message.save();
    // Sending the message to the other user
    try {
       await Chat.findOneAndUpdate(
            { id: id },
            { $push: { messages: tmp } },
            { new: true }
        ).exec();
        await message.save();
        return tmp;
      } catch(error){

        console.error(error);
    }
}

module.exports = {
    CreateChat, getAllChats, getChatById, deleteChat, sendMessage
};

function getUserInfo(chat, me) {
    if (chat.users[0].username === me) {
        return chat.users[0];
    }
    return chat.users[1];
}
function getOtherUserInfo(chat, me) {
    if (chat.users[0].username === me) {
        return chat.users[1];
    }
    return chat.users[0];
}

async function getChatID() {
    let chatCounter = await Counter.findOneAndUpdate(
        { name: "chat" },
        { $inc: { count: 1 } },
        { new: true, upsert: true }
    );
    return await chatCounter.count;
}
async function getMessageID() {
    let messageCounter = await Counter.findOneAndUpdate(
        { name: "chat" },
        { $inc: { count: 1 } },
        { new: true, upsert: true }
    );
    return await messageCounter.count;;
}