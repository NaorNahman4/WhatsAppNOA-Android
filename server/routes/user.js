const express = require('express');
const router = express.Router();
const userController = require('../controllers/user')

//Register
router.route('/').post(userController.createUser);

//get User pesonel by username
router.route('/:username').get(userController.getUserPersonel);


module.exports = router;