
const loginServer = async (data) => {
    const res = await fetch('http://localhost:8080/api/Tokens', {
      'method' : 'post',
      "headers" : {
        'Content-Type': 'application/json',
      },
      'body': JSON.stringify(data)
    });
    let token = await res.json();
    let statusNum = res.status;
    //returning the status number and ID!!
    return [statusNum, token];
  }

  module.exports = {
    loginServer
  }