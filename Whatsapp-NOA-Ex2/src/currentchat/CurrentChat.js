function CurrentChat(props) {
    return (
        <div id="current-chat-info">
            <img src={props.groupPic} className="rounded-circle profile-pic-in-div" />
             <span id="chat-name">{props.groupName}</span>
        </div>
    );
}
export default CurrentChat; 