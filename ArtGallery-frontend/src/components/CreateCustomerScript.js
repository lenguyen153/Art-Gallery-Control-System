import router from '../router'
import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {

  data() {
    return {
      Name: "",
      Username: "",
      Password: "",
      Address: "",
      error: "",
      createdMsg: ""
    }
  },

  methods: {
    returnToHome: function () {
      router.push({
        name: "Home"
      });
    },

    createCustomer: function (nameEntered, usernameEntered, passwordEntered, addressEntered) {
      if (!usernameEntered) {
        error = "Username cannot be empty!"
        return false
      } else if (!passwordEntered) {
        error = "Password cannot be empty!"
        return false
      } else if (!nameEntered) {
        error = "Name cannot be empty!"
        return false
      } else if (!addressEntered) {
        error = "Name cannot be empty!"
        return false
      }
      AXIOS.post('/createCustomer/', {
        name: nameEntered,
        username: usernameEntered,
        password: passwordEntered,
        address: addressEntered

      }, {})
        .then(response => {
          //JSON responses are automatically parsed.
          error = ""
          createdMsg = "Successfully created account!"
        })
        .catch(e => {
          var errorMsg = e
          console.log(errorMsg)
          error = "Username taken!"
          createdMsg = ""
        })
    }
  }
}