const express = require('express');
const router = express.Router();
const getTokenFB = require('../controllers/tokenFB.js')

//api/TokenFB
router.route('/').post(getTokenFB.getTokenFB);

module.exports = router;