import router from '../router'
import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

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
function TransactionDto(price, id, commission, tax, date) {
    this.price = price;
    this.id = id;
    this.commissionApplied = commission;
    this.taxApplied = tax;
    this.date = date;
}

var isArtworkViewed = false

export default {

    data() {
        return {
            artworks: [],
            transactions: [],
            errorArtwork: "",
            errorUpdateAccount: "",
            errorTransaction: "",
            cArtwork: "",
            Username: "",
            Name: "",
            Password: "",
            Address: "",
            selectedArtStyle: "",
        }
    },
    created: function () {
            /** not to be changed */
         this.username = this.$store.getters.username
         const t1 = new TransactionDto("1", "1", "1", "1", "1")
         const t2 = new TransactionDto("2", "2", "2", "2", "2")

    },
    methods: {
        logout: function () {
            this.$store.commit('change', '')
            router.push({
                name: "Home"
            });
        },
        viewCart: function () {
            router.push({
                name: "CartPage"
            })
        },
        /** test function */
        addToCart: function (artworkName) {
            AXIOS.post("/addToCart", { params: { username: this.Username, artName: artworkName } })
                .then(response => {
                    // JSON responses are automatically parsed.
                    //no response is really needed
                })
                .catch(e => {
                    var errorMsg = e
                    console.log(errorMsg)
                })
        },

        viewTransactions: function () {
            this.transactions = []
            AXIOS.get("/AllTransactions/")
                .then(response => {
                    // JSON responses are automatically parsed.
                    //empty and fill array again
                    this.transactions = response.data
                    // this.transactions = [t1 ,t2]
                })
                .catch(e => {
                    var errorMsg = e
                    console.log(errorMsg)
                })
        },

        updateCustomer: function () {
            var user = {
                name: this.Name,
                username: this.Username,
                password: this.Password,
            };
            AXIOS.post("/updateUser", { data: user }, {})
                .then(response => {
                    // JSON responses are automatically parsed.
                    this.Name =response.data.name
                    this.Username =response.data.username
                    this.Password = response.data.password
                })
                .catch(e => {
                    var errorMsg = e
                    console.log(errorMsg)
                })
        },

        viewArtworksByStyle: function (selectedArtStyle) {
            this.artworks = []
            if (isArtworkViewed == false){
              AXIOS.get(backendUrl + '/AllArtworks/'.concat(selectedArtStyle)).then(response => {
                this.artworks = response.data
                isArtworkViewed = true
              })
                .catch(e => {
                  var errorMsg = e.message
                  console.log(errorMsg)
                });
              }else {
                this.artworks = []
                isArtworkViewed = false
              }
          },
    }
}
