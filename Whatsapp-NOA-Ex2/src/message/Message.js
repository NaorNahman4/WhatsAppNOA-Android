

  

  //Returning the HTML of a message.
  function Message({ id, me, senderUsername, senderProfilePic, senderDisplayName, messageText, img, time }) {
    var datetimeString = time;
    var dateObj = new Date(datetimeString);
    var hour = dateObj.getHours();
    var minutes = dateObj.getMinutes();
    //Checking who is the sender of this message
    if (senderUsername === me) {
        return (
            <li  key={id}className="clearfix">
                <img src={img} className="rounded-circle profile-pic-in-div" alt="Profile"/>
                <div className="my-mess card text-white bg-primary mb-3 " style={{ maxWidth: '18rem', left: '10%' }}>
                    <div className="card-body">
                        <p className="card-text">{messageText}</p>
                        <small>{hour}:{minutes}</small>
                    </div>
                </div>
            </li>
        );
    }
    else {
        return (
            <li key={id}>
                <img src={senderProfilePic} className="rounded-circle sender-pic-in-div" alt="Profile"/>
                <div className="friend-mes card text-white bg-secondary mb-3  " style={{ maxWidth: '18rem', float: 'right', right: '10%' }}>
                    <div className="card-body">
                        <h5 className="card-title">{senderDisplayName}</h5>
                        <p className="card-text">{messageText}</p>
                        <small>{hour}:{minutes}</small>
                    </div>
                </div>
            </li>
        )
    }
}
export default Message;