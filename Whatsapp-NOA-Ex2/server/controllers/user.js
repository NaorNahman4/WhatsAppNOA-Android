const userService = require('../servies/user.js');
const jwt = require('jsonwebtoken');
const connectedUsers =require('../models/connectedUsers.js');


const createUser = async (req, res) => {
  const username = req.body.username;
  const password = req.body.password;
  const displayName = req.body.displayName;
  const profilePic = req.body.profilePic;

  const user = await userService.findUserByUsername(username);
  if (!user) {
    res.status(200).json(await userService.createUser(username, password, displayName, profilePic));
  }else{
    res.status(409).json(user);
  }
};



const login = async (req, res) => {
    const u = req.body.username;
    const p = req.body.password;
    const user = await userService.getUser(u, p);
    if (user) {
      // check if the user is already logged in, if yes , don't allow login and return error
      const userAlreadyLoggedIn =  await connectedUsers.findOne({username: u});
      if(userAlreadyLoggedIn){
        res.status(402).json({ error: "User already logged in" });
        return;
      }
      const token = jwt.sign({ username: u }, "key");
      res.json(token);
    } else {
      res.status(401).json({ error: "Invalid username or password" });
    }
  };
  
const getUserPersonel = async (req,res) => {
    const u = req.params.username;
    const user = await userService.findUserByUsername(u);
    if(user){
        res.status(200).json({username : user.username, displayName : user.displayName, profilePic:user.profilePic});
    }else{
        res.status(401);
    }
}

module.exports = {
    createUser, login, getUserPersonel
};