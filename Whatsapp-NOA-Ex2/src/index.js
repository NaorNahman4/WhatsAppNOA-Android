import React from 'react';
import Login from './login/Login';
import { createRoot } from 'react-dom/client';


const container = document.getElementById('root');
const root = createRoot(container);
root.render(<Login />);
// if the buttonLogin in the login page is pressed, show the Chat component in chat.js

export { root };
