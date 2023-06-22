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
  const logOutUser = async (username, token) => {
    console.log("log out user");
    try {
      // remove from mongo db
      await connectedPhoneUsers.deleteOne({ username: username});
    } catch (error) {
      console.error(error);
    }
  };
  module.exports = {
    insertConnectedPhone,logOutUser
  }
  