const connectedPhoneUsers = require('../models/connectPhoneUsers.js');

const insertConnectedPhone = async (username, token) => {
    console.log("insert connected phone");
    try {
      // addd to momgo db
      const data = new connectedPhoneUsers({ username: username, token: token });
      await data.save();
    } catch (error) {
  
      console.error(error);
    }
  };
  module.exports = {
    insertConnectedPhone
  }
  