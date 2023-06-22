import './noa.css';
import addimg from 'pictures/add-chat.png'



function CurrentUser(props) {
    return (
        <header id="contacts-header" className="user-in-chat">
            <div id="user-in-chat-left">
                <img src={props.img} className="rounded-circle profile-pic-in-div" />
            </div>
            <span style={{ position: 'absolute', left: '20%', top: '35%', width: '250px', height: '20px', fontWeight: 'bold' }}>{props.name}</span>
            <div id="user-in-chat-right">
                <img type="button" src={addimg} alt="image" id="add-btn" className="rounded-circle" data-bs-toggle="modal" data-bs-target="#imageModal" />
                <div className="modal fade" id="imageModal" tabIndex={-1} aria-labelledby="imageModalLabel" aria-hidden="true">
                    <div className="modal-dialog">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h5 className="modal-title" id="imageModalLabel">Add new contact</h5>
                                <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close" />
                            </div>
                            <div className="modal-body">
                                <label htmlFor="identifier">Contact's identifier:</label>
                                <input type="text" id="identifier" name="identifier" />
                            </div>
                            <div className="modal-footer">
                                <button type="button" className="btn btn-outline-secondary our-btn">Add</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div></header>
    );
}

export default CurrentUser;