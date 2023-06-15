const UserPassName = require('../models/user.js');

const createUser = async (username, password, displayName, profilePic) => {
  const user = new  UserPassName({ username: username, password: password, displayName: displayName });
  //If there is profile pic
  if (profilePic) {
    user.profilePic = profilePic;
  }
  return await user.save();
}

const getUser = async (username, password) => {
  try {
    return await UserPassName.findOne({ username, password }).exec();
  } catch (error) {

    console.error(error);
  }
};

const findUserByUsername = async (username) => {
  try {
    const  user =  await UserPassName.findOne({username}).exec();
    return user;
  } catch (error) {

    console.error(error);
    return null;
  }
};


module.exports = {
  createUser, getUser, findUserByUsername
}