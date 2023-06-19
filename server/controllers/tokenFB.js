const userService = require('../servies/tokenFB.js');
const jwt = require('jsonwebtoken');
const tokenFBRoutes =require('../servies/tokenFB.js');

const getTokenFB = async (req,res) => {
    

    const u = req.body.username;
    const tokenFB=req.body.token;
    console.log("get token fb");
    console.log(u);
    console.log(tokenFB);
    await tokenFBRoutes.insertConnectedPhone(username,tokenFB);
    res.status(200).json({});
}

module.exports = {
    getTokenFB
};
