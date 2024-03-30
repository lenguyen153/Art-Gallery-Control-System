import router from '../router'
import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
// var backendUrl = 'http://' + config.build.backendHost + ':' + config.build.backendPort
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

function ArtworkDto(artist, name, description, artstyle, nbCopy, price) {
  this.artist = artist;
  this.name = name;
  this.description = description;
  this.artstyle = artstyle;
  this.nbCopy = nbCopy;
  this.price = price;
}
function ArtistDto(name, username, password, id, artworks) {
  this.name = name;
  this.username = username;
  this.password = password;
  this.id = id;
  this.artworks = artworks;
}
function CustomerDto(name, username, password, address, id) {
  this.name = name;
  this.username = username;
  this.password = password;
  this.address = address;
  this.id = id;
}

function ManagerDto(name, username, password, id) {
  this.name = name;
  this.username = username;
  this.password = password;
  this.id = id;
}
function TransactionDto(price, id, commission, tax, date) {
  this.price = price;
  this.id = id;
  this.commissionApplied = commission;
  this.taxApplied = tax;
  this.date = date;
}

var isArtistViewed = false
var isCustomerViewed = false
var isArtworkViewed = false

export default {
  name: 'ManagerPage',
  data() {
    return {
      managers: [],
      artworks: [],
      customers: [],
      artists: [],
      artworks: [],
      errorArtwork: '',
      errorUpdateAccount: '',
      errorCustomer: '',
      errorArtist: '',
      errorManager: '',
      username: '',
      isArtworkViewed: false,
      isArtistViewed: false,
      isCustomerViewed: false
    }
  },
  created: function () {
    const a1 = new ArtistDto('name1', 'username1', 'password1', 'id1', 'null')
    const a2 = new ArtworkDto(a1, 'name1', 'description1', 'artstyle1', '1', '1')
    const a3 = new ArtworkDto(a1, 'name2', 'description2', 'artstyle2', '2', '2')
    this.artworks = [a2, a3]
    /** not to be changed */
    // this.username = this.$store.getters.username

    // if (this.username == "") {
    //   this.$router.push({ name: "login" })
    // }

    AXIOS.get('/AllArtworks')
      .then(response => {
        this.artworks = response.data
      })
      .catch(e => {
        this.errorArtwork = e
      })

    AXIOS.get('/AllCustomers')
      .then(response => {
        this.customers = response.data
      })
      .catch(e => {
        this.errorCustomer = e
      })

    AXIOS.get('/AllArtists')
      .then(response => {
        this.artists = response.data
      })
      .catch(e => {
        this.errorArtist = e
      })
    var req = AXIOS.get('/AllManagers')
      .then(response => {
        // JSON responses are automatically parsed.
        this.managers = response.data
        // arr.push(response.data)
        // req.then(x => console.log("Done!"));
        // console.log(arr)
      })
      .catch(e => {
        this.errorManager = e;
      })
  },

  methods: {

    logout: function () {
      this.$store.commit('change', '')
      router.push({
        name: "Home"
      });
    },
    viewArtists: function () {
      this.artists = []
      if (isArtistViewed == false) {
        AXIOS.get('/AllArtists').then(response => {
          this.artists = response.data
          isArtistViewed = true
        })
          .catch(e => {
            var errorMsg = e.message
            console.log(errorMsg)
          });
      } else {
        this.artists = []
        isArtistViewed = false
      }
    },

    viewArtworks: function () {
      this.artworks = []
      if (isArtworkViewed == false){
        AXIOS.get('/AllArtworks', {}, {}).then(response => {
          this.artworks = response.data
          isArtworkViewed = true
        })
          .catch(e => {
            var errorMsg = e.message
            console.log(errorMsg)
          });
        } else {
          this.artworks = []
          isArtworkViewed = false
        }
    },

    // viewArtworks: function () {
    //   this.isArtworkViewed = !this.isArtworkViewed
    // },

    viewCustomers: function () {
      this.customers = []
      if (isCustomerViewed == false) {
        AXIOS.get('/AllCustomers').then(response => {
          this.customers = response.data
          isCustomerViewed = true
        })
          .catch(e => {
            var errorMsg = e.message
            console.log(errorMsg)
          });
      } else {
        this.customers = []
        isCustomerViewed = false
      }
    },

    deleteArtwork: function (artworkname) {
      AXIOS.post('/deleteArtwork/'.concat(artworkname)).then(response => {
        for (var i = 0; i < this.artworks.length; i++) {
          if (this.artworks[i].name == artworkname) {
            this.artworks.splice(i);
          }
        }
        this.artists = response.data
        this.errorArtwork = ''
      })
        .catch(e => {
          var errorMsg = e.message
          console.log(errorMsg)
        });
    },

    updateManager: function (name, username, password) {
      // var index = this.managers.map(x => x.name).indexOf(username)
      // var manager = this.managers[index]
      AXIOS.post(backendUrl + '/updateManager/'.concat(username), {}, {
        params: {
          name: name, password: password
        }
      })
        .then(response => {
          this.managers.push(response.data)
          this.errorUpdateAccount = ''
        })
        .catch(e => {
          var errorMsg = e.message
          console.log(errorMsg)
        });
    },

    changeCommision: function (username, commission) {
      AXIOS.post(backendUrl + '/updateCommissionByManager', {}, {
        params: {
          username: username, specificCommissionApplied: commission
        }
      })
        .then(response => {
          this.artists.push(response.data)
        })
        .catch(e => {
          var errorMsg = e.message
          console.log(errorMsg)
        });
    },

    ban: function (username) {
      this.customers = []
      // var indexCustomer = this.events.map(x => x.username).indexOf(username)
      // var bancustomer = this.customers[indexCustomer]
      AXIOS.post('/banCustomer/'.concat(username)).then(response => {
        // this.customers[indexCustomer] = response.data
        this.customers.push(response.data)
        this.errorCustomer = ''
      })
        .catch(e => {
          var errorMsg = e.message
          console.log(errorMsg)
        });
    },

    unban: function (username) {
      this.customers = []
      // var indexCustomer = this.events.map(x => x.username).indexOf(username)
      AXIOS.post('/unbanCustomer/'.concat(username)).then(response => {
        // this.customers[indexCustomer] = response.data
        this.customers.push(response.data)
        this.errorCustomer = ''
      })
        .catch(e => {
          var errorMsg = e.message
          console.log(errorMsg)
        });
    }
  }
}