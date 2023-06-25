const userService = require('../servies/tokenFB.js');
const jwt = require('jsonwebtoken');
const tokenFBRoutes =require('../servies/tokenFB.js');

const getTokenFB = async (req,res) => {
    
    const u = req.body.username;
    const tokenFB=req.body.token;

    await tokenFBRoutes.insertConnectedPhone(u,tokenFB);
    res.status(200).json({});
}
const deleteFB = async (req,res) => {
    const u = req.body.username;
    const tokenFB=req.body.token;
    await tokenFBRoutes.logOutUser(u,tokenFB);
    res.status(200).json({});
}
module.exports = {
    getTokenFB,deleteFB
};
