 export const userList = ([]);
 

  // Returning a user (variable) by the id
  export const getUserById = (id) => {
    return userList.find((user) => user.id === id);
  };

