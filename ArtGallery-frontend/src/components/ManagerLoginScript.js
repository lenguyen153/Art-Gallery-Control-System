import router from '../router'
import axios from 'axios'
import ManagerPageScript from './ManagerPageScript'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
// var backendUrl = 'http://' + config.build.backendHost + ':' + config.build.backendPort
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  // headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
  data() {
    return {
      Username: "",
      Password: "",
      error: ""
    }
  },

  methods: {
    returnToHome: function () {
      router.push({
        name: "Home"
      });
    },
    
    login: function(Username, Password) {
      AXIOS.get('/user/'.concat(Username), {
        params: {
          password: Password
        }
      })
      .then(response => {
        this.error = ''
        this.$store.commit('change', Username)
        router.push({
          name: "ManagerPage"
        })
      })
      .catch(e => {
        var errorMsg = e.response.data
        console.log(errorMsg)
        this.error = "Incorrect username or password!"
      })
    }
  }
  }