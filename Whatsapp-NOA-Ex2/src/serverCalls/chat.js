//Function that returns a user Personal data.
const getUserPersonel = async ({username, token}) => {
    try {
        const response = await fetch('http://localhost:8080/api/Users/' + username, {
            method: 'GET',
            headers: {
                "authorization": token,
                'Content-Type': 'application/json',
            },
        });
        //Returning the user: username, displayname and img
        return await response.json();
    } catch (error) {
        console.error(error);
    }
};

//Function that returns a user's chat list.
const getUserChats = async ({token }) => {
    try {
        const response = await fetch('http://localhost:8080/api/Chats/', {
            method: 'GET',
            headers: {
                "authorization": token,
                'Content-Type': 'application/json',
            },
        });
        //Returning the user's chat list
        const i = await (response.json());
        return i;
    } catch (error) {
        console.error(error);
    }

}

//Function that adds a chat to a user's chat
const addChat = async ({token, username}) => {
    try {
        const response = await fetch('http://localhost:8080/api/Chats/', {
            method: 'POST',
            headers: {
                "authorization": token,
                'Content-Type': 'application/json',
            },
            'body': JSON.stringify({"username": username})
        });
        //Returning the user's chat list
        const r = await response.json();

        return [r, response.status];
    } catch (error) {
        console.error(error);
    }
}

//Function that returns a chat by its id
const getChat = async ({token, id}) => {
    try {
        const response = await fetch('http://localhost:8080/api/Chats/' + id, {
            method: 'GET',
            headers: {
                "authorization": token,
                'Content-Type': 'application/json',
            },
        });
        //Returning the chat with this id
        var r = await response.json();

        return r;
    } catch (error) {
        console.error(error);
    }
}

//delete a chat from the user chat preview
const deleteChat = async (token, id) => {

    try {
        const r = await fetch('http://localhost:8080/api/Chats/' + id, {
            method: 'DELETE',
            headers: {
                "authorization": token,
                'Content-Type': 'application/json',
            },
        });

        var k = await r.json();
        return k;
    } catch (error) {

        console.error(error);
    }
    
}






module.exports = {
    getUserPersonel, getUserChats,addChat, getChat, deleteChat
}