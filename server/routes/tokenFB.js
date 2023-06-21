const express = require('express');
const router = express.Router();
const tokenFB = require('../controllers/tokenFB.js')

//api/TokenFB
router.route('/').post(tokenFB.getTokenFB);
//api/TokenFB
router.route('/logout').post(tokenFB.deleteFB);

module.exports = router;