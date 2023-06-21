const mongoose = require('mongoose');

const Schema = mongoose.Schema;

const connectedPhoneUsers = new Schema({
    username: {
        type: String,
        require: true
    },
    token: {
        type: String,
        require: true
    }
});

module.exports = mongoose.model('connectedPhoneUsers', connectedPhoneUsers);