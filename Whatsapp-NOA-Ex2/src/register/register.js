import React, { useState, useRef } from 'react';
import '../noa.css';
import Login from '../login/Login';
import { root } from '../index.js'
import logo from "../pictures/LOGO.png"
import { userList } from '../database/Database';
import defaultUserPic from '../pictures/user-profile.png';
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
        await convertToBase64Pic('../pictures/user-profile.png').then(result => {
          img = result;
          console.log(img);
        });
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