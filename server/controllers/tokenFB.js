const userService = require('../servies/tokenFB.js');
const jwt = require('jsonwebtoken');
const tokenFBRoutes =require('../servies/tokenFB.js');

const getTokenFB = async (req,res) => {
    
    console.log("get token fb");
    const u = req.body.username;
    const tokenFB=req.body.token;
    console.log("get token fb");
    console.log(u);
    console.log(tokenFB);
    await tokenFBRoutes.insertConnectedPhone(u,tokenFB);
    res.status(200).json({});
}
const deleteFB = async (req,res) => {
    console.log("Delete controller");
    const u = req.body.username;
    const tokenFB=req.body.token;
    console.log("get token fb");
    console.log(u);
    console.log(tokenFB);
    await tokenFBRoutes.logOutUser(u,tokenFB);
    res.status(200).json({});
}
module.exports = {
    getTokenFB,deleteFB
};
