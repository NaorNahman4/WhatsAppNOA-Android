const mongoose = require('mongoose');
// const defaultPic = require('../../src/pictures');

const Schema = mongoose.Schema;

const Message = new Schema({
    id: {
        type: Number,
        require: true
    },
    created: {
        type: Date,
        require: true
    },
    sender: {
        username: {
            type: String,
            require: true
        },
        displayName: {
            type: String,
            require: true
        },
        profilePic: {
            type: String
        }
    },
    content: {
        type: String,
        require: true
    }
})

module.exports = mongoose.model('Message', Message);