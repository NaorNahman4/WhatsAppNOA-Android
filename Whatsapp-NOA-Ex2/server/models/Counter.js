const mongoose = require('mongoose');
const Schema = mongoose.Schema;
const Counter = new Schema({
    name : {
        type: String,
        require : true
    },
    count: {
        type: Number,
        default: 0
    }
})


module.exports = mongoose.model('Counter', Counter);