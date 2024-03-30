import router from '../router'
import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl
  //headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {

  data() {
    return {
      Name: "",
      Username: "",
      Password: "",
      selectedOption: "",
      error: ""
    }
  },

  methods: {
    returnToHome: function () {
      router.push({
        name: "Home"
      });
    },
  
    createArtist: function (nameEntered, usernameEntered, passwordEntered, optionEntered) {
      if (!usernameEntered) {
        error = "Username cannot be empty!"
        return false
      } else if (!passwordEntered) {
        error = "Password cannot be empty!"
        return false
      } else if (!nameEntered) {
        error = "Name cannot be empty!"
        return false
      } else if (!optionEntered) {
        error = "Shipping option must be selected!"
        return false
      }
      AXIOS.post('/createArtist',{
        name: nameEntered,
        username: usernameEntered,
        password: passwordEntered,
        shipToGallery: optionEntered
      },{})
        .then(response => {
          // JSON responses are automatically parsed.
          //this.persons.push(response.data)
          createdMsg = "Successfully created account!"
          error = ""
        })
        .catch(e => {
          var errorMsg = e.response.data.message
          console.log(errorMsg)
          error = "Username taken!"
          createdMsg = ""
        })
    }
  }
}