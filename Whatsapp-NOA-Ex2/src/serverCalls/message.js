//Function that send a message to a specific chat id
const sendMessage = async ({id, token, msg}) => {
    try {
        const response = await fetch('http://localhost:8080/api/Chats/' + id + "/Messages", {
            method: 'POST',
            headers: {
                "authorization": token,
                'Content-Type': 'application/json',
            },
            'body': JSON.stringify({"msg": msg})
        });
        //Returning message
        const i = await response.json();
        return i;
    } catch (error) {
        console.error(error);
    }
};

//Function that returns list of messages for a specific chat id
const getMessages = async ({id, token}) => {
    try {
        const response = await fetch('http://localhost:8080/api/Chats/' + id + "/Messages", {
            method: 'GET',
            headers: {
                "authorization": token,
                'Content-Type': 'application/json',
            },
        });
        //Returning the user: username, displayname and img
        return [await response.json(), response.status];
    } catch (error) {
        console.error(error);
    }
};

module.exports = {sendMessage, getMessages};