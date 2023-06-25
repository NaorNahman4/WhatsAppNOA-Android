import React, { useState, useRef } from 'react';
import '../noa.css';
import Login from '../login/Login';
import { root } from '../index.js'
import logo from "../pictures/LOGO.png"
import { userList } from '../database/Database';
import { sendSwal } from '../chat/chat';
import { registerServer } from '../serverCalls/register.js';

function Register() {
  const ClickEnter = (event) => {
    if (event.key === 'Enter') {
      ClickRegister();
    }
  };
  // function to check if the user choose a picture, if yes show it, otherwise, show nothing.
  function showImage() {
    if (image) {
      return <img src={image} className="prof-pic" alt="Profile"></img>;
    }
  }


  const ClickRegister = async () => {
    var username = usernamev.current.value;
    var password = passwordv.current.value;
    var name = displaynamev.current.value;
    var img;

    if (username === '') {
      sendSwal("Please insert username", "warning");
    }
    else if (password === '') {
      sendSwal("Please insert password", "warning");
    }
    else if (name === '') {
      sendSwal("Please insert display name", "warning");
    }
    else {
      if (!image) {
        img = defaultUserPic;
      }
      else {
        img = image;
      }
      const data = { "username": username, "password": password, "displayName": name, "profilePic": img };
      const statusNum = await registerServer(data);
      const chatList = [];
      if (statusNum === 200) {
        const user = { id: String(userList.length + 1), username, password, name, img, chatList };
        userList.push(user);
        root.render(<Login />);
      }
      else if (statusNum === 409) {
        sendSwal("This user is already exist!", "warning");
      }
      else {
        sendSwal("Incorect status number!", "warning");
      }
    }

  };



  const usernamev = useRef(null);
  const passwordv = useRef(null);
  const displaynamev = useRef(null);


  const ClickLogin = () => {
    root.render(<Login />);
  }


  // the image state
  const [image, setImage] = useState(null);

  //Convert the image to base64 and make it a string
  function convertToBase64(e) {
    var reader = new FileReader();
    if (e.target.files[0]) {
      reader.readAsDataURL(e.target.files[0]);
      reader.onload = () => {
        setImage(reader.result);
      }
      reader.onerror = (error) => {
      }
    }
  }

  function convertToBase64Pic(url) {
    return fetch(url)
    .then(response => response.blob())
    .then(blob => {
      return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.onloadend = () => resolve(reader.result);
        reader.onerror = reject;
        reader.readAsDataURL(blob);
      });
    });
  }
  
  
  const defaultUserPic =`data:text/html;base64,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
  `;

  return (
    <>
      <div className="upper-bg">
        <img src={logo} className="logo" alt="Logo"></img>
      </div>
      <div className="background d-flex justify-content-center align-items-center">
        <div className="form-container p-4 rounded in-register">
          <header className="reg-head">Create an account</header><br></br>
          <label htmlFor="exampleFormControlInput1" className="form-label">Username</label><br></br>
          <input onKeyDown={ClickEnter} ref={usernamev} type="Username" className="form-control" ></input> <br></br>
          <label htmlFor="inputPassword5" className="form-label">Password</label><br></br>
          <input onKeyDown={ClickEnter} ref={passwordv} type="password" className="form-control" aria-labelledby="passwordHelpBlock"></input><br></br>
          <label htmlFor="exampleFormControlInput1" className="form-label">Display Name</label><br></br>
          <input onKeyDown={ClickEnter} ref={displaynamev} type="Display Name" className="form-control" id="exampleFormControlInput1"></input><br></br>
          <label htmlFor="formFile" className="form-label">Picture</label><br></br>
          <input onChange={convertToBase64} className="form-control" accept={".jpg, .jpeg, .png"}type="file"></input><br></br>
          {showImage()}
          <button onClick={ClickRegister} type="submit" className="btn btn-primary our-btn">Register</button>
          <button id="have-acc" onClick={ClickLogin} type="submit" className="btn btn-primary our-btn">I already have an account</button>
        </div>
      </div>
    </>
  );
}
export default Register;