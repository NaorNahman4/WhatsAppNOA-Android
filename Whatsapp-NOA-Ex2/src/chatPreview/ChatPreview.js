//Returning the HTML code of a Chat Preview
function ChatPreview(props) {
  props.socket.emit('renderAddChat',props.username);
  if (props.lastMessage) {
    const formatDate = (dateString) => {
      const date = new Date(dateString);
      
      const formattedDate = date.toLocaleDateString('en-GB', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric'
      });
      
      const formattedTime = date.toLocaleTimeString('en-GB', {
        hour: '2-digit',
        minute: '2-digit'
      });
      
      return `${formattedDate} ${formattedTime}`;
    };
    const formattedDate = formatDate(props.created);

    return (
<>
        <div>
          <img src={props.img} className="rounded-circle chat-profile-pic" />
        </div>
        <div>
          <span className="chat-tag-label-name">{props.name}</span>
        </div>
        <div>
          <span className="chat-tag-label-last">{props.lastMessage}</span>
        </div>
        <div className='pre-chat-con'>
          <span className="chat-tag-label-date" style={{ textAlign: "center" }}>{formattedDate}</span>
        </div>

      </>
    );
  }

  else {
    return (
      <>
        <div>
          <img src={props.img} className="rounded-circle chat-profile-pic" />
        </div>
        <div>
          <span className="chat-tag-label-name">{props.name}</span>
        </div>
        <div>
          <span className="chat-tag-label"></span>
        </div>
      </>
    );
  }
}

export default ChatPreview;


