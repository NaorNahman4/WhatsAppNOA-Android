const mongoose = require('mongoose');
// const defaultPic = require('../../src/pictures');

const Schema = mongoose.Schema;

const UserPassName = new Schema({
    username: {
        type: String,
        require: true,
        unique: true
    },
    password: {
        type: String,
        require: true
    },
    displayName: {
        type: String,
        require: true
    },
    profilePic: {
        type: String    ,
        // default: defaultPic.user-profile.png
    },

});

module.exports = mongoose.model('UserPassName', UserPassName);