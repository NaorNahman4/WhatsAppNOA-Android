const usernameAndToken = require('../models/connectPhoneUsers.js');

const insertConnectedPhone = async (username, token) => {
    try {
      return await usernameAndToken.insertOne({ username, password }).exec();
    } catch (error) {
  
      console.error(error);
    }
  };
  module.exports = {
    insertConnectedPhone
  }
  