import React, { useRef } from 'react';
import './Modal.css';
import '../noa.css';
import { addChat } from '../serverCalls/chat';
import { AddChatPreview } from '../chat/chat.js';




function Modal({ setOpenModal, token, func }) {



    const ClickEnter = async (event) => {
        if (event.key === 'Enter') {
            setOpenModal(false);
            var txt = modaltxt.current.value;
            var [returnVal, status] = await addChat({ "token": token, "username": txt });
            AddChatPreview(returnVal, func, status);
        }
    }

    const modaltxt = useRef();
    return (
        <div className="modalBackground">
            <div className="modalContainer">
                <div className="titleCloseBtn">
                    <button
                        onClick={() => {
                            setOpenModal(false);
                        }}
                    >
                        X
                    </button>
                </div>
                <div className="title">
                    <h1>Add new contact</h1>
                </div>
                <div className="m-body">

                    <span className="label-text">Contact's name: </span>
                    <input onKeyUp={ClickEnter} ref={modaltxt} type="Modal-txt" id="identifier" name="identifier"></input>

                </div>
                <div className="footer our-btn">
                    <button
                        onClick={async () => {
                            setOpenModal(false);
                            var txt = modaltxt.current.value;
                            var [returnVal, status] = await addChat({ "token": token, "username": txt });
                            AddChatPreview(returnVal, func, status);
                        }}
                        id="Modal-btn"
                    >
                        Add
                    </button>
                </div>
            </div>
        </div>
    );
}

export default Modal;